package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.components.Limelight;
import frc.core.components.Limelight.LedMode;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.Drivetrain;

public class AimAndRangeTarget extends CommandBase {
  private Drivetrain drivetrain;

  public AimAndRangeTarget(Drivetrain drivetrain) {
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
      y = Limelight.getY(),
      headingError = -(x),
      distanceError = -(y),
      steeringAdjust = 0;

    if (x > 1) {
      steeringAdjust = LimelightConstants.AimAndRangeTarget.kP *
                        headingError -
                        LimelightConstants.AimAndRangeTarget.minCommand;
    } else if (x < 1) {
      steeringAdjust = LimelightConstants.AimAndRangeTarget.kP *
                        headingError +
                        LimelightConstants.AimAndRangeTarget.minCommand;
    }

    var distanceAdjust = LimelightConstants.AimAndRangeTarget.kPDistance * distanceError;

    double leftSpeed = 0,
      rightSpeed = 0;
      
    leftSpeed += steeringAdjust + distanceAdjust;
    rightSpeed -= steeringAdjust + distanceAdjust;

    this.drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    Limelight.setLed(LedMode.OFF);
  }
}
