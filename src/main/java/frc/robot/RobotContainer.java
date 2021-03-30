package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.intake.CollectPowerCell;
import frc.robot.commands.intake.ReleasePowerCell;
import frc.robot.commands.shooter.ShootPowerCellAngle;
import frc.robot.commands.shooter.ShootPowerCellDefault;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.buffer.Feed;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Buffer;

public class RobotContainer {

  private final Drivetrain drivetrain;
  private final Intake intake;
  private final Buffer buffer;
  private final Shooter shooter;

  private final XboxController driver;
    
  public RobotContainer() {
    this.drivetrain = new Drivetrain();
    this.intake = new Intake();
    this.buffer = new Buffer();
    this.shooter = new Shooter();

    this.driver = new XboxController(OIConstants.driverControllerPort);
    this.configureButtonBindings();
  }

  private void configureButtonBindings() {
    this.configureButtonBindingsIntake();
    this.configureButtonBindingsBuffer();
    this.configureButtonBindingsShooter();
    this.configureButtonBindingsDrivetrain();
  }

  private void configureButtonBindingsIntake() {
    var buttonX = new JoystickButton(this.driver, Button.kBumperLeft.value);
    var buttonB = new JoystickButton(this.driver, Button.kB.value);
    
    buttonX
      .whileHeld(new CollectPowerCell(this.intake));
    
    buttonB
      .whileHeld(new ReleasePowerCell(this.intake));
  }

  private void configureButtonBindingsBuffer() {
    var buttonBumperLeft = new JoystickButton(this.driver, Button.kBumperLeft.value);

    buttonBumperLeft
      .whileHeld(new Feed(this.buffer));
    }

  private void configureButtonBindingsShooter() {
    // var doubleButton = new DoubleButton(
    //   this.driver,
    //   Button.kBumperRight.value,
    //   Button.kA.value
    // );
    
    var buttonBumperRight = new JoystickButton(this.driver, Button.kBumperRight.value);

    buttonBumperRight
      .whileHeld(new ShootPowerCellDefault(this.shooter, 0.5));

    var buttonY = new JoystickButton(this.driver, Button.kY.value);

    buttonY
      .whileHeld(new ShootPowerCellAngle(this.shooter, 0.6));

    var buttonA = new JoystickButton(this.driver, Button.kA.value);

    buttonA
    .whileHeld(new ShootPowerCellAngle(this.shooter, 0.75));


  }

  public void configureButtonBindingsDrivetrain() {
    this.drivetrain.setDefaultCommand(
      new ArcadeDrive(
        drivetrain, 
        () -> this.driver.getY(Hand.kLeft), 
        () -> this.driver.getX(Hand.kRight)
      )
    );

    // var axisLeftTrigger = new JoystickButton(this.driver, Axis.kLeftTrigger.value);

    // axisLeftTrigger
    //   .whileHeld(new AimTarget(this.drivetrain));
  }

  public Command getAutonomousCommand() {
    return null;
  }
  
  public void reset() {
    this.drivetrain.reset();
  }
}
