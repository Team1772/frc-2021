package frc.robot.routines;

import static java.util.Objects.isNull;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.core.util.DoubleButton;
import frc.robot.Robot;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.buffer.Feed;
import frc.robot.commands.buffer.SmartFeed;
import frc.robot.commands.drivetrain.AimTarget;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.CurvatureDrive;
import frc.robot.commands.intake.CollectPowerCell;
import frc.robot.commands.intake.ReleasePowerCell;
import frc.robot.commands.shooter.ShootPowerCellAngle;
import frc.robot.commands.shooter.ShootPowerCellDefault;

public class Teleoperated implements IRoutines {
  private static Teleoperated teleoperated;

  private XboxController driver = new XboxController(OIConstants.driverControllerPort);
  private XboxController operator = new XboxController(OIConstants.operatorControllerPort);

  private Teleoperated() { }

  public static Teleoperated getInstance(){
    if (isNull(teleoperated)) teleoperated = new Teleoperated();

    return teleoperated;
  }

  @Override
  public void init() { }

  @Override
  public void periodic() {
    this.configureButtonBindings();
  }

  @Override
  public void cancel() { }

  private void configureButtonBindings() {
    this.commandsIntake();
    this.commandsDrivetrain();
    this.commandsBuffer();
    this.commandsShooter();
  }

  private void commandsIntake() {
    var buttonBumperLeft = new JoystickButton(this.operator, Button.kBumperLeft.value);
    var axisTriggerLeft = new JoystickButton(this.operator, Axis.kLeftTrigger.value);

    buttonBumperLeft
      .whileHeld(new CollectPowerCell(Robot.intake));
    
    axisTriggerLeft
      .whileHeld(new ReleasePowerCell(Robot.intake));
  }

  private void commandsShooter() {
    var buttonBumperRight = new JoystickButton(this.operator, Button.kBumperRight.value);
    
    Trigger isAtSettedVelocity = new Trigger(() -> Robot.shooter.atSettedVelocity());

    buttonBumperRight
      .whileHeld(new ShootPowerCellDefault(Robot.shooter))
      .and(isAtSettedVelocity).whileActiveContinuous(new Feed(Robot.buffer));
    
    var doubleButton = new DoubleButton(
      this.operator,
      Button.kBumperRight.value,
      Button.kA.value
    );

    doubleButton.whileActiveContinuous(new ShootPowerCellAngle(Robot.shooter));
  }

  private void commandsDrivetrain() {
    var buttonBumperLeft = new JoystickButton(this.driver, Button.kBumperLeft.value);

    buttonBumperLeft.whileHeld(
      new CurvatureDrive(
        Robot.drivetrain,
        () -> this.driver.getY(Hand.kLeft),
        () -> this.driver.getX(Hand.kRight)
      )
    );

    Robot.drivetrain.setDefaultCommand(
      new ArcadeDrive(
        Robot.drivetrain, 
        () -> this.driver.getY(Hand.kLeft), 
        () -> this.driver.getX(Hand.kRight)
      )
    );

    var buttonBumperRight = new JoystickButton(this.driver, Button.kBumperRight.value);
    
    buttonBumperRight
      .whileHeld(new AimTarget(Robot.drivetrain));
  }

  private void commandsBuffer(){
    Robot.buffer.setDefaultCommand(
      new SmartFeed(Robot.buffer)
    );
  }
}