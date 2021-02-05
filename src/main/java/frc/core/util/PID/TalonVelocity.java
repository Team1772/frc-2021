package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TalonVelocity extends PIDTalon {

    //constructor
    public TalonVelocity(
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
    }
    
    //getters
    public double getSensorVelocity(){
        SmartDashboard.putNumber(
            "Motor velocity", 
            super.motor.getSelectedSensorVelocity()
        );
        
        return super.motor.getSelectedSensorVelocity();
    }

    //setters
    public void setPower(double power) {
        super.motor.set(ControlMode.Velocity, power);
    }
}
