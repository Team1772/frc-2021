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
import frc.robot.commands.autons.PercursoC;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.CurvatureDrive;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {

  private final Drivetrain drivetrain;
  private final Intake intake;

  private final XboxController driver;
  
  private TrajectoryBuilder trajectoryBuilder;
  
  public RobotContainer() {
    this.drivetrain = new Drivetrain();
    this.intake = new Intake();

    this.driver = new XboxController(OIConstants.driverControllerPort);

    this.trajectoryBuilder = new TrajectoryBuilder(
      this.drivetrain, 
      "test",
      "test2",
      "percurso-a",
      "percurso-b",
      "percurso-c"
    );

    this.configureButtonBindings();

    /* CONTROLS:

       Left Stick & Right Stick - Drive the robot
       Left Bumper - Smooth Curve (NEEDS TO BE FIXED)
       Right Bumper - Collect Powercell
       Right Trigger - Release Powercell

    */
  }

  private void configureButtonBindings() {
    this.configureButtonBindingsIntake();
    this.configureButtonBindingsDrivetrain();
  }

   private void configureButtonBindingsIntake() {
     var buttonBumperRight = new JoystickButton(this.driver, Button.kBumperRight.value);
     var axisTriggerRight = new JoystickButton(this.driver, Axis.kRightTrigger.value);

     buttonBumperRight
       .whileHeld(new CollectPowerCell(this.intake));
    
     axisTriggerRight
       .whileHeld(new ReleasePowerCell(this.intake));
   }

  public void configureButtonBindingsDrivetrain() {
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
        this.drivetrain, 
        () -> this.driver.getY(Hand.kLeft), 
        () -> this.driver.getX(Hand.kRight)
      )
    );
  }

  public Command getAutonomousCommand() {
    return new PercursoC(this.trajectoryBuilder);
  }
  
  public void reset() {
    this.drivetrain.reset();
  }
}
