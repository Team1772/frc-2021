package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants.DrivetrainConstants.PIDConstants;

public class TalonPosition extends PIDTalon {

    public TalonPosition(
        TalonSRX motor, 
        TalonSRX follower, 
        boolean kSensorPhase,
        boolean kFollowerInverted,
        double nominalOutputForwardValue, 
        double nominalOutputReverseValue, 
        double peakOutputForwardValue,
        double peakOutputReverseValue, 
        boolean kMotorInvert,
        Gains gains,
        TalonSRX... followers
    )
    {
        super(
            motor, 
            follower,  
            kSensorPhase,
            kFollowerInverted,
            nominalOutputForwardValue,
            nominalOutputReverseValue, 
            peakOutputForwardValue, 
            peakOutputReverseValue, 
            kMotorInvert, 
            gains,
            followers
        );

        this.setAbsolutePosition(kMotorInvert, kSensorPhase);
    }

    private int getAbsolutePosition() {
        return super.motor.getSensorCollection().getPulseWidthPosition();
    }

    public boolean isMaxPosition(double value) {
        return (this.getSelectedSensorPosition() - this.getSelectedSensorPosition()) < (-value);
    }

    public double getSelectedSensorPosition() {
        return super.motor.getSelectedSensorPosition(0);
    }

    public double getMotorOutput() {
        return super.motor.getMotorOutputPercent();
    } 

    public void setPostion(int position) {
        super.motor.set(ControlMode.Position, this.getSelectedSensorPosition() - position);
    }

    private void setAbsolutePosition(
        boolean kMotorInvert, 
        boolean kSensorPhase
    )
    {
        int absolutePosition = this.getAbsolutePosition();

        absolutePosition &= 0xFFF;

        if (kSensorPhase) { absolutePosition *= -1; }
        if (kMotorInvert) { absolutePosition *= -1; }
        
        super.motor.setSelectedSensorPosition(absolutePosition, PIDConstants.kPIDLoopIdx, PIDConstants.kTimeoutMs);
    }
}
