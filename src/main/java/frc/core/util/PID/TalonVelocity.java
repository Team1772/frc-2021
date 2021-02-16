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

  public void setVelocity(double velocity) {
    super.master.set(ControlMode.Velocity, velocity);
  }

  public void stop() {
    super.master.set(ControlMode.PercentOutput, 0);
  }
}
