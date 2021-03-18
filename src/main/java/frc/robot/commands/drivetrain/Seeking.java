package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.components.Limelight;
import frc.core.components.Limelight.LedMode;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.Drivetrain;

public class Seeking extends CommandBase {
  private Drivetrain drivetrain;

  public Seeking(Drivetrain drivetrain) {
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
      steeringAdjust = 0;

    if (!Limelight.isTarget()) {
      steeringAdjust = 0.3;
    } else {
      double headingError = x;

      steeringAdjust = LimelightConstants.Seeking.kP * headingError;
    }

    double leftSpeed = 0,
    rightSpeed = 0;

    leftSpeed += steeringAdjust;
    rightSpeed -= steeringAdjust;

    this.drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    Limelight.setLed(LedMode.OFF);
  }
}
