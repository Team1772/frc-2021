package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonPosition extends PIDTalon {

    //constructor
    public TalonPosition(
        TalonSRX motor, 
        TalonSRX follower, 
        int kPIDLoopIdx, 
        int kTimeoutMs, 
        boolean kSensorPhase,
        double nominalOutputForwardValue, 
        double nominalOutputReverseValue, 
        double peakOutputForwardValue,
        double peakOutputReverseValue, 
        boolean kMotorInvert
    )
    {
        super(
            motor, 
            follower, 
            kPIDLoopIdx, 
            kTimeoutMs, 
            kSensorPhase, 
            nominalOutputForwardValue,
            nominalOutputReverseValue, 
            peakOutputForwardValue, 
            peakOutputReverseValue, 
            kMotorInvert, 
            null
        );

        this.setAbsolutePosition(kPIDLoopIdx, kTimeoutMs, kMotorInvert, kSensorPhase);
    }

    //getters
    private int getAbsolutePosition(){
        return super.motor.getSensorCollection().getPulseWidthPosition();
    }

    public boolean isMaxPosition(double value){
        return (this.getSelectedSensorPosition() - this.getSelectedSensorPosition()) < (-value);
    }

    public double getSelectedSensorPosition(){
        return super.motor.getSelectedSensorPosition(0);
    }

    public double getMotorOutput(){
        return super.motor.getMotorOutputPercent();
    }

    //setters
    public void setPostion(int position){
        super.motor.set(ControlMode.Position, this.getSelectedSensorPosition() - position);
    }

    private void setAbsolutePosition(
        int kPIDLoopIdx, 
        int kTimeoutMs, 
        boolean kMotorInvert, 
        boolean kSensorPhase
    )
    {
        int absolutePosition = this.getAbsolutePosition();

        absolutePosition &= 0xFFF;

        if (kSensorPhase) { absolutePosition *= -1; }
        if (kMotorInvert) { absolutePosition *= -1; }
        
        super.motor.setSelectedSensorPosition(absolutePosition, kPIDLoopIdx, kTimeoutMs);
    }
}
