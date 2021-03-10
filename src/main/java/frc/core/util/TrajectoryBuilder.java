package frc.core.util;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

	private Map<String, Trajectory> trajectories;
	private RamseteCommand ramseteCommand;

	public TrajectoryBuilder(Drivetrain drivetrain, String... filesNames) {
		this.drivetrain = drivetrain;

		this.trajectories = this.mapTrajectories(filesNames);

		this.simpleMotorFeedforward = new SimpleMotorFeedforward(
			DrivetrainConstants.ksVolts,
			DrivetrainConstants.kvVoltSecondsPerMeter,
			DrivetrainConstants.kaVoltSecondsSquaredPerMeter
		);
		this.pidController = new PIDController(
			DrivetrainConstants.PID.kPDriveVelocity, 
			DrivetrainConstants.PID.kIDriveVelocity, 
			DrivetrainConstants.PID.kDDriveVelocity
		);
		this.ramseteController = new RamseteController(
			AutoConstants.kRamseteB, 
			AutoConstants.kRamseteZeta
		);
	}

	public Map<String, Trajectory> mapTrajectories(String... filesNames) {
		return Arrays.stream(filesNames)
			    .collect(Collectors.toMap(
				    fileName -> fileName,
				    fileName -> this.createTrajectory(fileName)
			    ));
	}
	
	public void createRamsete(Trajectory trajectory) {
		if (isNull(trajectory)) {
			DriverStation.reportError(
				"trajectory is null", 
				new Exception().getStackTrace()
			);
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

			this.drivetrain.resetOdometry(trajectory.getInitialPose());
		}
  }

  public Command buildTrajectory(String fileName) {
		var trajectory = this.trajectories.get(fileName);
    this.createRamsete(trajectory);

    return this.getRamsete().andThen(
      () -> this.drivetrain.tankDriveVolts(0, 0)
    );
  }

	private RamseteCommand getRamsete() {
		return this.ramseteCommand;
	}

	private Trajectory createTrajectory(String fileName) {
		String path = String.format("paths/output/%s.wpilib.json", fileName);
		try {
			Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(path);

			return TrajectoryUtil.fromPathweaverJson(trajectoryPath);
		} catch (IOException ex) {
			DriverStation.reportError(
					String.format("Unable to open trajectory: %s", path), 
					ex.getStackTrace()
			);

			return null;
		}
	}
}