package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.components.Limelight;
import frc.core.components.Limelight.LedMode;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.Drivetrain;

public class AimAndRangeTarget extends CommandBase {
  private Drivetrain drivetrain;
  private Limelight limelight;

  public AimAndRangeTarget(Drivetrain drivetrain, Limelight limelight) {
    this.drivetrain = drivetrain;
    this.limelight = limelight;
    this.limelight.setPipeline(LimelightConstants.pipeline);

    addRequirements(this.drivetrain);
  }

  @Override
  public void initialize() {
    this.limelight.setLed(LedMode.ON);
  }

  @Override
  public void execute() {
    double x = limelight.getX(),
      y = limelight.getY(),
      headingError = -(x),
      distanceError = -(y),
      steeringAdjust = 0,
      leftSpeed = 0,
      rightSpeed = 0;

    if (x > 1) {
      steeringAdjust = LimelightConstants.kpAim *
                        headingError -
                        LimelightConstants.minAimCommand;
    } else if (x < 1) {
      steeringAdjust = LimelightConstants.kpAim *
                        headingError +
                        LimelightConstants.minAimCommand;
    }

    var distanceAdjust = LimelightConstants.kpDistance * distanceError;
    
    leftSpeed += steeringAdjust + distanceAdjust;
    rightSpeed -= steeringAdjust + distanceAdjust;

    this.drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    this.limelight.setLed(LedMode.OFF);
  }
}
