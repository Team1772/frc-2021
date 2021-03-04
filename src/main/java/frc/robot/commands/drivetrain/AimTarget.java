package frc.robot.commands.drivetrain;

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
    Limelight.setPipeline(LimelightConstants.pipeline);
  }

  @Override
  public void execute() {
    double x = Limelight.getX(),
     headingError = -(x),
     adjust = 0;

    if (x > 1) {
      adjust = LimelightConstants.kP * 
                headingError - 
                LimelightConstants.minCommand;
    } else if (x < 1) { 
      adjust = LimelightConstants.kP * 
                headingError + 
                LimelightConstants.minCommand;
    }

    double rightSpeed = 0,
     leftSpeed = 0;

    rightSpeed -= adjust;
    leftSpeed += adjust;

    this.drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    Limelight.setLed(LedMode.OFF);
  }
}
