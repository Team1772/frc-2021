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
  private Drivetrain drivetrain;

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
			DrivetrainConstants.PIDConstants.kPDriveVelocity,
			DrivetrainConstants.PIDConstants.kIDriveVelocity, 
			DrivetrainConstants.PIDConstants.kDDriveVelocity
		);
		this.ramseteController = new RamseteController(
			AutoConstants.kRamseteB, 
			AutoConstants.kRamseteZeta
		);
	}

  public void createRamsete(){
    if (isNull(this.trajectory)) {
      DriverStation.reportError(
          "trajectory is null", 
          new Exception().getStackTrace()
      );
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

			this.drivetrain.resetOdometry(this.trajectory.getInitialPose());
		}
  }
  
  public Command buildTrajectory(String fileName) {
    this.setTrajectory(fileName);
    this.createRamsete();

    return this.getRamsete().andThen(
      () -> this.drivetrain.tankDriveVolts(0, 0)
    );
  }
  
	private RamseteCommand getRamsete() {
		return this.ramseteCommand;
	}

	private void setTrajectory(String fileName) {
		String path = String.format("paths/output/%s.wpilib.json", fileName);
		try {
			Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(path);
			this.trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
		} catch (IOException ex) {
			DriverStation.reportError(
					String.format("Unable to open trajectory: %s", path), 
					ex.getStackTrace()
			);
		}
	}
}