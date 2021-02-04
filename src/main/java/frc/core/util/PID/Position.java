package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Position extends PIDTalon {

    public Position(TalonSRX motor, TalonSRX follower, int kPIDLoopIdx, int kTimeoutMs, boolean kSensorPhase,
            double nominalOutputForwardValue, double nominalOutputReverseValue, double peakOutputForwardValue,
            double peakOutputReverseValue, boolean kMotorInvert) {
        super(motor, follower, kPIDLoopIdx, kTimeoutMs, kSensorPhase, nominalOutputForwardValue,
                nominalOutputReverseValue, peakOutputForwardValue, peakOutputReverseValue, kMotorInvert, null);
        // TODO Auto-generated constructor stub

        setAbsolutePosition(kPIDLoopIdx, kTimeoutMs, kMotorInvert, kSensorPhase);
    }

    private int getAbsolutePosition(){
        return motor.getSensorCollection().getPulseWidthPosition();
    }

    private void setAbsolutePosition(int kPIDLoopIdx, int kTimeoutMs, boolean kMotorInvert, boolean kSensorPhase){
        int absolutePosition =  getAbsolutePosition();

        absolutePosition &= 0xFFF;

        if (kSensorPhase) { absolutePosition *= -1; }
        if (kMotorInvert) { absolutePosition *= -1; }
        
        motor.setSelectedSensorPosition(absolutePosition, kPIDLoopIdx, kTimeoutMs);
    }

    public boolean isMaxPosition(double value){
        return (getSelectedSensorPosition() - getSelectedSensorPosition()) <  (-value);
    }

    public double getSelectedSensorPosition(){
        return super.motor.getSelectedSensorPosition(0);
    }

    public double getMotorOutput(){
        return motor.getMotorOutputPercent();
    }

    public void setPostion(int position){
        motor.set(ControlMode.Position, getSelectedSensorPosition() - position);
    }
    
}
