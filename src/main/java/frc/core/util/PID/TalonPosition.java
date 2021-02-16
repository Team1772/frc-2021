package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants.PIDTalonConstants;

public class TalonPosition extends PIDTalon {

  public TalonPosition(
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

    this.setAbsolutePosition(isMasterInverted, isSensorPhase);
  }

  public TalonPosition(
    TalonSRX master,
    boolean isMasterInverted,
    boolean isFollowerInverted,
    boolean isSensorPhase,
    Gains gains,
    TalonSRX... followers
  ) {
    this(
      master,
      isMasterInverted,
      isFollowerInverted,
      isSensorPhase,
      PIDTalonConstants.nominalOutputForwardValue,
      PIDTalonConstants.nominalOutputReverseValue,
      PIDTalonConstants.peakOutputForwardValue,
      PIDTalonConstants.peakOutputReverseValue,
      gains,
      followers
    );
  }

  public TalonPosition(
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

  private int getAbsolutePosition() {
    return super.master.getSensorCollection().getPulseWidthPosition();
  }

  public boolean isMaxPosition(double value) {
    return (this.getSelectedSensorPosition() - this.getSelectedSensorPosition()) < (-value);
  }

  public double getSelectedSensorPosition() {
    return super.master.getSelectedSensorPosition(0);
  }

  public double getMasterOutput() {
    return super.master.getMotorOutputPercent();
  } 

  public void setPostion(int position) {
    super.master.set(ControlMode.Position, this.getSelectedSensorPosition() - position);
  }

  private void setAbsolutePosition( boolean isMasterInverted, boolean isSensorPhase) {
    int absolutePosition = this.getAbsolutePosition();

    absolutePosition &= 0xFFF;

    if (isSensorPhase) { absolutePosition *= -1; }
    if (isMasterInverted) { absolutePosition *= -1; }
    
    super.master.setSelectedSensorPosition(
      absolutePosition, 
      PIDTalonConstants.kPIDLoopIdx, 
      PIDTalonConstants.kTimeoutMs
    );
  }
}
