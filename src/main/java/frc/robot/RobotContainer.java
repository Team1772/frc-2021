package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

import java.util.List;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.BufferConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.intake.Actuator;
import frc.robot.commands.intake.CollectPowerCell;
import frc.robot.commands.intake.ReleasePowerCell;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.CurvatureDrive;
import frc.robot.commands.buffer.Feed;
import frc.robot.commands.buffer.RollBack;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Buffer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  //subsystems
  private final Drivetrain drivetrain;
  private final Intake intake;
  private final Buffer buffer;

  //controller
  private final XboxController driver;
  private final XboxController operator;

  //constructor
  public RobotContainer() {
    this.drivetrain = new Drivetrain();
    this.intake = new Intake();
    this.buffer = new Buffer();

    this.driver = new XboxController(OIConstants.driverControllerPort);
    this.operator = new XboxController(OIConstants.operatorControllerPort);
    
    this.configureButtonBindings();
    this.configureDefaultCommand();
  }

  //configure commands
  private void configureDefaultCommand() {
    var buttonBumperLeft = new JoystickButton(this.driver, Button.kBumperLeft.value);

    buttonBumperLeft.whenHeld(
      new CurvatureDrive(
        this.drivetrain,
        () -> this.driver.getY(Hand.kLeft),
        () -> this.driver.getX(Hand.kRight)
      )
    );

    this.drivetrain.setDefaultCommand(
      new ArcadeDrive(
        drivetrain, 
        () -> this.driver.getY(Hand.kLeft), 
        () -> this.driver.getX(Hand.kRight)
      )
    );
  }

  private void configureButtonBindings() {

  }

  public Command getAutonomousCommand() {
    var simpleMotorFeedforward = new SimpleMotorFeedforward(
      DrivetrainConstants.ksVolts,
      DrivetrainConstants.kvVoltSecondsPerMeter,
      DrivetrainConstants.kaVoltSecondsSquaredPerMeter
    );

    var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
      simpleMotorFeedforward,
      DrivetrainConstants.kDriveKinematics,
      DrivetrainConstants.differentialDriveVoltageConstraintMaxVoltage
    );

    var trajectoryConfig = new TrajectoryConfig(
      AutoConstants.kMaxSpeedMetersPerSecond,
      AutoConstants.kMaxAccelerationMetersPerSecondSquared
    )
    .setKinematics(DrivetrainConstants.kDriveKinematics)
    .addConstraint(autoVoltageConstraint);

    var trajectory = TrajectoryGenerator.generateTrajectory(
      new Pose2d(0, 0, new Rotation2d(0)),
      List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
      new Pose2d(3, 0, new Rotation2d(0)),
      trajectoryConfig
    );

    var pidController = new PIDController(
      DrivetrainConstants.kPDriveVelocity, 
      DrivetrainConstants.kIDriveVelocity, 
      DrivetrainConstants.kDDriveVelocity
    );

    var ramseteCommand = new RamseteCommand(
      trajectory,
      this.drivetrain::getPose,
      new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
      simpleMotorFeedforward,
      DrivetrainConstants.kDriveKinematics, 
      this.drivetrain::getWheelSpeeds, 
      pidController, 
      pidController, 
      this.drivetrain::tankDriveVolts, 
      this.drivetrain
    );

    this.drivetrain.resetOdometry(trajectory.getInitialPose());

    return ramseteCommand.andThen(() -> this.drivetrain.tankDriveVolts(0, 0));
  }
}
