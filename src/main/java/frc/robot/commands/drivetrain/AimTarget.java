package frc.robot.commands.drivetrain;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.components.Limelight;
import frc.core.components.Limelight.LedMode;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.Drivetrain;

public class AimTarget extends CommandBase {
  private Drivetrain drivetrain;

  public AimTarget(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    addRequirements(this.drivetrain);
  }

  @Override
  public void initialize() {
    Limelight.setLed(LedMode.ON);
    // Limelight.setPipeline(LimelightConstants.pipeline);
  }

  @Override
  public void execute() {
    double x = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
    System.out.println(x);
    var headingError = -(x);
    double adjust = 0;

    if (x > 1) {
      adjust = LimelightConstants.kP * 
                headingError - 
                LimelightConstants.minCommand;
      
      System.out.println("entrou x>1");
    } else if (x < 1) { 
      adjust = LimelightConstants.kP * 
                headingError + 
                LimelightConstants.minCommand;

      System.out.println("entrou x<1");
    }

    double rightSpeed = 0,
     leftSpeed = 0;

    rightSpeed -= adjust;
    leftSpeed += adjust;

    this.drivetrain.tankDrive(leftSpeed, rightSpeed);

    System.out.println(leftSpeed);
    System.out.println(rightSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    Limelight.setLed(LedMode.OFF);
  }
}
