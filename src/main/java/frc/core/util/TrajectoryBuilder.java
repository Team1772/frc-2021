package frc.core.util;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;

public class TrajectoryBuilder {
    //subsystems
    private final Drivetrain drivetrain;

    //attributes
    private final SimpleMotorFeedforward simpleMotorFeedforward;
    private final PIDController pidController;
    private final RamseteController ramseteController;

    private Trajectory trajectory;
    private RamseteCommand ramseteCommand;

    //constructor
    public TrajectoryBuilder(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        this.simpleMotorFeedforward = new SimpleMotorFeedforward(
            DrivetrainConstants.ksVolts,
            DrivetrainConstants.kvVoltSecondsPerMeter,
            DrivetrainConstants.kaVoltSecondsSquaredPerMeter
        );
        this.pidController = new PIDController(
            DrivetrainConstants.kPDriveVelocity, 
            DrivetrainConstants.kIDriveVelocity, 
            DrivetrainConstants.kDDriveVelocity
        );
        this.ramseteController = new RamseteController(
            AutoConstants.kRamseteB, 
            AutoConstants.kRamseteZeta
        );
    }

    //autonomous commands

    /*
     * This method was not updated. For the next commits,
     * we need to handle this.
     */
    public Command getAutonomousCommand() {
        if (isNull(this.ramseteCommand)) {
            this.setRamseteCommand(this.trajectory);
        }
        
        this.drivetrain.resetOdometry(this.trajectory.getInitialPose());
    
        return this.ramseteCommand.andThen(() -> this.drivetrain.tankDriveVolts(0, 0));
    }

    //getters
    public Trajectory getTrajectory(){
        return this.trajectory;
    }

    public RamseteCommand getRamseteCommand() {
        return this.ramseteCommand;
    }

    //setters
    public void setTrajectory(String localPath) {
        String trajectoryJSON = localPath;
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            this.trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError(
                "Unable to open trajectory: " + trajectoryJSON, 
                ex.getStackTrace()
            );
        }
    }

    public void setRamseteCommand(Trajectory trajectory){
        if (isNull(this.trajectory)) {
            System.err.println("trajectory is null, it must be a not null value");
        } else {
            this.ramseteCommand = new RamseteCommand(
                trajectory,
                this.drivetrain::getPose,
                this.ramseteController,
                this.simpleMotorFeedforward,
                DrivetrainConstants.kDriveKinematics, 
                this.drivetrain::getWheelSpeeds, 
                this.pidController, 
                this.pidController, 
                this.drivetrain::tankDriveVolts, 
                this.drivetrain
            );
        }
    }
}