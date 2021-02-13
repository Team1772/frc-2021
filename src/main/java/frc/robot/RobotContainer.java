package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
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
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {

  //subsystems
  private final Drivetrain drivetrain;
  private final Intake intake;

  //controller
  private TrajectoryBuilder trajectoryBuilder;
  private final XboxController driver;
  private final XboxController operator;

  private Trajectory trajectory;

  //constructor
  public RobotContainer() {
    this.drivetrain = new Drivetrain();
    this.intake = new Intake();

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
    this.configureButtonBindingsIntake();

  }

   private void configureButtonBindingsIntake(){
     var buttonBumperLeft = new JoystickButton(this.operator, Button.kBumperLeft.value);
     var axisTriggerLeft = new JoystickButton(this.operator, Axis.kLeftTrigger.value);
 
     buttonBumperLeft
     .whileHeld(new CollectPowerCell(this.intake));
     
     axisTriggerLeft
     .whileHeld(new ReleasePowerCell(this.intake));
    }

  public Command getAutonomousCommand() {
    this.trajectoryBuilder = new TrajectoryBuilder(this.drivetrain);

    return new GalacticA(this.trajectoryBuilder);
	}
}
