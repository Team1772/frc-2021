package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TalonVelocity extends PIDTalon {

    public TalonVelocity(
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
    }
    
    public double getSensorVelocity(){
        SmartDashboard.putNumber(
            "Motor velocity", 
            super.motor.getSelectedSensorVelocity()
        );
        
        return super.motor.getSelectedSensorVelocity();
    }

    public void setVelocity(double velocity) {
        super.motor.set(ControlMode.Velocity, velocity);
    }

    public void stop(){
        super.motor.set(ControlMode.PercentOutput, 0);
    }
}
