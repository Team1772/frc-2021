package frc.core.util;

import java.util.List;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
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
    private final DifferentialDriveVoltageConstraint autoVoltageConstraint;
    private final TrajectoryConfig trajectoryConfig;
    private final PIDController pidController;
    private final RamseteController ramseteController;

    private Trajectory trajectory;
    private RamseteCommand ramseteCommand;

    //constructor
    public TrajectoryBuilder() {
        this.drivetrain = new Drivetrain();

        this.simpleMotorFeedforward = new SimpleMotorFeedforward(
            DrivetrainConstants.ksVolts,
            DrivetrainConstants.kvVoltSecondsPerMeter,
            DrivetrainConstants.kaVoltSecondsSquaredPerMeter
        );

        this.autoVoltageConstraint =  new DifferentialDriveVoltageConstraint(
            this.simpleMotorFeedforward,
            DrivetrainConstants.kDriveKinematics,
            DrivetrainConstants.differentialDriveVoltageConstraintMaxVoltage
        );

        this.trajectoryConfig = new TrajectoryConfig(
            AutoConstants.kMaxSpeedMetersPerSecond,
            AutoConstants.kMaxAccelerationMetersPerSecondSquared
        )
        .setKinematics(DrivetrainConstants.kDriveKinematics)
        .addConstraint(this.autoVoltageConstraint);

        /* 
         * this is a hard coded trajectory, 
         * it must be instanciated to prevent NullPointerException.
         * 
         * You can use the method setTrajectory() for a custom trajectory
         */
        this.trajectory = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
            new Pose2d(3, 0, new Rotation2d(0)),
            this.trajectoryConfig
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
    public Command getAutonomousCommand() {
        if (this.ramseteCommand == null) {
            this.setDefaultRamseteCommand();
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
    public void setTrajectory(
        Pose2d initialPosition, 
        List<Translation2d> listOfCommands, 
        Pose2d finalPosition 
    )
    {
        this.trajectory = TrajectoryGenerator.generateTrajectory(
            initialPosition,
            listOfCommands,
            finalPosition,
            this.trajectoryConfig
        );
    }

    public void setTrajectoryWithCustomConfig(
        Pose2d initialPosition, 
        List<Translation2d> listOfCommands, 
        Pose2d finalPosition, 
        TrajectoryConfig trajectoryConfig
    ) 
    {
        this.trajectory = TrajectoryGenerator.generateTrajectory(
            initialPosition,
            listOfCommands,
            finalPosition,
            trajectoryConfig
        );
    }

    public void setDefaultRamseteCommand(){
        if (this.trajectory == null) {
            System.err.println("TRAJECTORY IS NULL, YOU MUST SET A TRAJECTORY IN CONSTRUCTOR");
        } else {
            this.ramseteCommand = new RamseteCommand(
                this.trajectory,
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