package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.core.util.TrajectoryBuilder;
import frc.core.util.DoubleButton;
import frc.robot.Constants.BufferConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.intake.CollectPowerCell;
import frc.robot.commands.intake.ReleasePowerCell;
import frc.robot.commands.shooter.ShootPowerCellAngle;
import frc.robot.commands.shooter.ShootPowerCellDefault;
import frc.robot.commands.autons.GalacticA;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.CurvatureDrive;
import frc.robot.commands.buffer.Feed;
import frc.robot.commands.buffer.SmartFeed;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Buffer;

public class RobotContainer {

  private final Drivetrain drivetrain;
  private final Intake intake;
  private final Buffer buffer;
  // private final Shooter shooter;

  private final XboxController driver, operator;
  
  private TrajectoryBuilder trajectoryBuilder;
  
  public RobotContainer() {
    this.drivetrain = new Drivetrain();
    this.intake = new Intake();
    this.buffer = new Buffer();
    // this.shooter = new Shooter();

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
    // this.configureButtonBindingsShooter();
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
      new SmartFeed(this.buffer)
    );
  }

  // private void configureButtonBindingsShooter() {
  //   var buttonBumperRight = new JoystickButton(this.operator, Button.kBumperRight.value);
    
  //   buttonBumperRight
  //   .whileHeld(new ShootPowerCellDefault(shooter));
    
  //   var doubleButton = new DoubleButton(
  //     this.operator,
  //     Button.kBumperRight.value,
  //     Button.kA.value
  //   );

  //   doubleButton.whenActive(new ShootPowerCellAngle(shooter));
  // }

  public Command getAutonomousCommand() {
    return new GalacticA(this.trajectoryBuilder);
  }

  
  
  public void reset() {
    this.drivetrain.reset();
  }
}
