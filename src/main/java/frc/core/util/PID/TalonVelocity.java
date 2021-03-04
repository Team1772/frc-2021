package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants.PIDTalonConstants;

public class TalonVelocity extends PIDTalon {

  public TalonVelocity(
    TalonSRX master, 
    boolean isMasterInverted,
    boolean isFollowerInverted,
    boolean isSensorPhase,
    double nominalOutputForwardValue, 
    double nominalOutputReverseValue, 
    double peakOutputForwardValue,
    double peakOutputReverseValue, 
    Gains gains,
    TalonSRX... followers
  ) {
    super(
      master, 
      isMasterInverted, 
      isFollowerInverted,
      isSensorPhase,
      nominalOutputForwardValue,
      nominalOutputReverseValue, 
      peakOutputForwardValue, 
      peakOutputReverseValue, 
      gains,
      followers
    );
  }

  public TalonVelocity(
    TalonSRX master,
    boolean isMasterInverted,
    boolean isFollowerInverted,
    Gains gains,
    TalonSRX... followers
  ) {
    this(
      master,
      isMasterInverted,
      isFollowerInverted,
      PIDTalonConstants.isSensorPhase,
      PIDTalonConstants.nominalOutputForwardValue,
      PIDTalonConstants.nominalOutputReverseValue,
      PIDTalonConstants.peakOutputForwardValue,
      PIDTalonConstants.peakOutputReverseValue,
      gains,
      followers
    );
  }

  public TalonVelocity(
    TalonSRX master,
    Gains gains,
    TalonSRX... followers
  ) {
    this(
      master,
      false,
      false,
      PIDTalonConstants.isSensorPhase,
      PIDTalonConstants.nominalOutputForwardValue,
      PIDTalonConstants.nominalOutputReverseValue,
      PIDTalonConstants.peakOutputForwardValue,
      PIDTalonConstants.peakOutputReverseValue,
      gains,
      followers
    );
  }
  
  private void setVelocity(double velocity, double dutyCycle) {
    var velocityUnitsPer100ms = dutyCycle * velocity * 4096 / 600;

    super.master.set(ControlMode.Velocity, velocityUnitsPer100ms);
  }

  public void setVelocityRPM(double velocityRPM, double dutyCycle) {
    this.setVelocity(velocityRPM, dutyCycle);
  } 

  public void setVelocityMetersPerSecond(double velocityMetersPerSecond, double dutyCycle, double wheelRadius) {
    var velocityRPM = (velocityMetersPerSecond * 60) / (2 * Math.PI * wheelRadius);

    this.setVelocity(velocityRPM, dutyCycle);
  }

  public void stop() {
    super.master.set(ControlMode.PercentOutput, 0);
  }
}
