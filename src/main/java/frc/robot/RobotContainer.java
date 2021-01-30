package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

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
    var buttonA = new JoystickButton(this.driver, Button.kA.value);

    buttonA.whenHeld(new RunCommand(() -> this.drivetrain.resetEncoders(), this.drivetrain));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
