package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.subsystems.Intake;
import frc.core.util.TrajectoryBuilder;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.intake.CollectPowerCell;
import frc.robot.commands.intake.ReleasePowerCell;
import frc.robot.commands.autons.GalacticA;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.CurvatureDrive;
import frc.robot.commands.buffer.Feed;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Buffer;

public class RobotContainer {

  private final Drivetrain drivetrain;
  private final Intake intake;
  private final Buffer buffer;

  private final XboxController driver, operator;
  
  private TrajectoryBuilder trajectoryBuilder;
  
  public RobotContainer() {
    this.drivetrain = new Drivetrain();
    this.intake = new Intake();
    this.buffer = new Buffer();

    this.driver = new XboxController(OIConstants.driverControllerPort);
    this.operator = new XboxController(OIConstants.operatorControllerPort);

    this.trajectoryBuilder = new TrajectoryBuilder(this.drivetrain, "autoAwards_0","autoAwards_1", "autoAwards");

    this.configureButtonBindings();
    this.configureDefaultCommand();
  }

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
    this.configureButtonBindingsIntake();
    this.configureButtonBindingsBuffer();
  }

  private void configureButtonBindingsIntake() {
    var buttonBumperLeft = new JoystickButton(this.operator, Button.kBumperLeft.value);
    var axisTriggerLeft = new JoystickButton(this.operator, Axis.kLeftTrigger.value);

    buttonBumperLeft
      .whileHeld(new CollectPowerCell(this.intake));
    
    axisTriggerLeft
      .whileHeld(new ReleasePowerCell(this.intake));
  }

  private void configureButtonBindingsBuffer() {
    this.buffer.setDefaultCommand(
      new Feed(
        buffer,
        () -> this.operator.getY(Hand.kRight)
      )
    );
  }

  public Command getAutonomousCommand() {
    return new GalacticA(this.trajectoryBuilder);
  }

  
  
  public void reset() {
    this.drivetrain.reset();
  }
}
