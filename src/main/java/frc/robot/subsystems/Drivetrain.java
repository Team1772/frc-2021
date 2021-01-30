package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.OIConstants;

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

    var isEncoderRightInverted = true;
    this.encoderRight = new Encoder(
      DrivetrainConstants.encoderRightPort[0],
      DrivetrainConstants.encoderRightPort[1],
      isEncoderRightInverted
    );

    this.setEncodersDistancePerPulse();
  }

  public void arcadeDrive(double forward, double rotation) {
    this.drive.arcadeDrive(forward, rotation);
    System.out.println("Forward: " + forward);
  }

  public void resetEncoders() {
    System.out.println("entrou aqui");
    this.encoderLeft.reset();
    this.encoderRight.reset();
  }

  public void setEncodersDistancePerPulse() {
    double wheelCircumferenceCentimeters = (Units.inchesToMeters(DrivetrainConstants.wheelRadius) * 100) * 2 * Math.PI;
    double pulsesRight = 498;
    double pulsesLeft = 494;

    SmartDashboard.putNumber("wheel circumference", wheelCircumferenceCentimeters);
    SmartDashboard.putNumber("wheel radius", Units.inchesToMeters(DrivetrainConstants.wheelRadius));

    this.encoderLeft.setDistancePerPulse(wheelCircumferenceCentimeters / pulsesLeft);
    this.encoderRight.setDistancePerPulse(wheelCircumferenceCentimeters / pulsesRight);
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
