package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartNavX;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  private final SpeedControllerGroup motorsRight, motorsLeft;
  private final DifferentialDrive drive;
  private final Encoder encoderRight, encoderLeft;
  private final SmartNavX navX; 
  private final DifferentialDriveOdometry odometry;

  //constructor
  public Drivetrain() {
    this.motorsRight = new SpeedControllerGroup(
      new VictorSP(DrivetrainConstants.motorRightPort[0]), 
      new VictorSP(DrivetrainConstants.motorRightPort[1])
    );
    this.motorsLeft = new SpeedControllerGroup(
      new VictorSP(DrivetrainConstants.motorLeftPort[0]), 
      new VictorSP(DrivetrainConstants.motorLeftPort[1])
    );

    this.setMotorsInverted(
      DrivetrainConstants.isMotorsInverted[0], 
      DrivetrainConstants.isMotorsInverted[1]
    );

    this.drive = new DifferentialDrive(this.motorsRight, this.motorsLeft);

    this.encoderLeft = new Encoder(
      DrivetrainConstants.encoderLeftPort[0],
      DrivetrainConstants.encoderLeftPort[1],
      DrivetrainConstants.isEcondersInverted[0]
    );
 
    this.encoderRight = new Encoder(
      DrivetrainConstants.encoderRightPort[0],
      DrivetrainConstants.encoderRightPort[1],
      DrivetrainConstants.isEcondersInverted[1]
    );

    this.navX = new SmartNavX(); 

    this.odometry = new DifferentialDriveOdometry(this.getRotation2d());

    this.setEncodersDistancePerPulse();
    this.resetEncoders();
  }

  //helpers
  public void arcadeDrive(double forward, double rotation) {
    this.drive.arcadeDrive(forward, (rotation));
  }

  public void curvatureDrive(double forward, double rotation, boolean isQuickTurn) {
    this.drive.curvatureDrive(forward, -(rotation), isQuickTurn);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    this.drive.tankDrive(leftSpeed, rightSpeed);
  }

  public void resetEncoders() {
    this.encoderLeft.reset();
    this.encoderRight.reset();
  }

  public void resetNavX() {
    this.navX.reset();
  }

  public void reset() {
    this.resetNavX();
    this.resetEncoders();
  }

  public void updateOdometry() {
    this.odometry.update(
      this.getRotation2d(), 
      this.encoderLeft.getDistance(), 
      this.encoderRight.getDistance()
    );
  }

  public void resetOdometry(Pose2d pose) {
    this.resetEncoders();
    this.odometry.resetPosition(pose, this.getRotation2d());
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    this.motorsLeft.setVoltage(leftVolts);
    this.motorsRight.setVoltage(-(rightVolts));
    this.drive.feed();
  }

  //getters
  public double getAverageDistance() {
    var averageDistance = (this.encoderLeft.getDistance() + this.encoderRight.getDistance()) / 2.0;
    
    return averageDistance;
  }

  public double getAngle() {
    return this.navX.getAngle();
  }

  public Pose2d getPose() {
    return this.odometry.getPoseMeters();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    var driveWheelSpeeds = new DifferentialDriveWheelSpeeds(
      this.encoderLeft.getRate(), 
      this.encoderRight.getRate()
    );
    
    return driveWheelSpeeds;
  }

  public Encoder getEncoderLeft() {
    return this.encoderLeft;
  }

  public Encoder getEncoderRight() {
    return this.encoderRight;
  }

  public double getHeading() {
    return this.getRotation2d().getDegrees();
  }

  public Rotation2d getRotation2d() {
    return this.navX.getRotation2d();
  }

  public double getTurnRate() {
    return this.navX.getRate();
  }

  //setters
  public void setMaxOutput(double maxOutput) {
    this.drive.setMaxOutput(maxOutput);
  }

  public void setMotorsInverted(boolean isMotorsLeftInverted, boolean isMotorsRightInverted) {
    this.motorsLeft.setInverted(isMotorsLeftInverted);
    this.motorsRight.setInverted(isMotorsRightInverted);
  }

  public void setEncodersDistancePerPulse() {
    var wheelCircumferenceMeters = Units.inchesToMeters(DrivetrainConstants.wheelRadius) * 2 * Math.PI;

    var distancePerPulseLeft = wheelCircumferenceMeters / (double) DrivetrainConstants.pulsesLeft;
    var distancePerPulseRight = wheelCircumferenceMeters / (double) DrivetrainConstants.pulsesRight;

    this.encoderLeft.setDistancePerPulse(distancePerPulseLeft);
    this.encoderRight.setDistancePerPulse(distancePerPulseRight);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("[DRIVETRAIN] Encoder Left", this.encoderLeft.get());
    SmartDashboard.putNumber("[DRIVETRAIN] Encoder Right", this.encoderRight.get());
    SmartDashboard.putNumber("[DRIVETRAIN] Average Distance", this.getAverageDistance());
    
    this.updateOdometry();
  }
}
