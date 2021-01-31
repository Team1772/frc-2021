package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  private final Drivetrain drivetrain;

  private final XboxController driver;

  public RobotContainer() {
    this.drivetrain = new Drivetrain();

    this.driver = new XboxController(OIConstants.driverControllerPort);

    this.configureButtonBindings();
    this.configureDefaultCommand();
  }

  private void configureDefaultCommand() {
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
    var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
      new SimpleMotorFeedforward(
        DrivetrainConstants.ksVolts,
        DrivetrainConstants.kvVoltSecondsPerMeter,
        DrivetrainConstants.kaVoltSecondsSquaredPerMeter
      ),
      DrivetrainConstants.kDriveKinematics,
      DrivetrainConstants.differentialDriveVoltageConstraintMaxVoltage
    );

    return null;
  }
}
