package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  private SpeedControllerGroup motorsRight, motorsLeft;
  private DifferentialDrive drive;
  private Encoder encoderRight, encoderLeft;

  public Drivetrain() {
    this.motorsRight = new SpeedControllerGroup(
      new VictorSP(DrivetrainConstants.motorRightPort[0]), 
      new VictorSP(DrivetrainConstants.motorRightPort[1])
    );
    this.motorsLeft = new SpeedControllerGroup(
      new VictorSP(DrivetrainConstants.motorLeftPort[0]), 
      new VictorSP(DrivetrainConstants.motorLeftPort[1])
    );

    this.drive = new DifferentialDrive(this.motorsRight, this.motorsLeft);

    this.encoderLeft = new Encoder(
      DrivetrainConstants.encoderLeftPort[0],
      DrivetrainConstants.encoderLeftPort[1]
    );
 
    this.encoderRight = new Encoder(
      DrivetrainConstants.encoderRightPort[0],
      DrivetrainConstants.encoderRightPort[1],
      DrivetrainConstants.isEncoderRightInverted
    );

    this.setEncodersDistancePerPulse();
    this.resetEncoders();
  }

  public void arcadeDrive(double forward, double rotation) {
    this.drive.arcadeDrive(forward, rotation);
  }

  public void resetEncoders() {
    this.encoderLeft.reset();
    this.encoderRight.reset();
  }

  public void setEncodersDistancePerPulse() {
    var wheelCircumferenceCentimeters = (Units.inchesToMeters(DrivetrainConstants.wheelRadius) * 100) * 2 * Math.PI;

    var distancePerPulseLeft = wheelCircumferenceCentimeters / (double) DrivetrainConstants.pulsesLeft;
    var distancePerPulseRight = wheelCircumferenceCentimeters / (double) DrivetrainConstants.pulsesRight;

    this.encoderLeft.setDistancePerPulse(distancePerPulseLeft);
    this.encoderRight.setDistancePerPulse(distancePerPulseRight);
  }

  public double getAverageDistance() {
    var averageDistance = (this.encoderLeft.getDistance() + this.encoderRight.getDistance()) / 2.0;
    
    return averageDistance;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Encoder left", encoderLeft.get());
    SmartDashboard.putNumber("Encoder right", encoderRight.get());
    SmartDashboard.putNumber("Average distance", this.getAverageDistance());
  }
}
