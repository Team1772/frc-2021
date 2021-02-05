package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/* Ideia
 * 
 * implementar uma maneira de escalonar os followers;
 * Perguntar para Gabriela sobre como fazer isso.
 */

public abstract class PIDTalon {
    //attributes
    protected TalonSRX motor, follower;

    //constructor
    public PIDTalon(
        TalonSRX motor, 
        TalonSRX follower, 
        int kPIDLoopIdx, 
        int kTimeoutMs, 
        boolean kSensorPhase, 
        double nominalOutputForwardValue,
        double nominalOutputReverseValue, 
        double peakOutputForwardValue, 
        double peakOutputReverseValue, 
        boolean kMotorInvert, 
        Gains gains
    )
    {
        this.motor = motor;
        this.follower = follower;

        this.configSelectedFeedbackSensor(kPIDLoopIdx, kTimeoutMs);
        this.setSensorPhase(kSensorPhase);
        this.setInverted(kMotorInvert);
        this.setFollower();
        this.setOutputs(
        nominalOutputForwardValue, 
        nominalOutputReverseValue, 
        peakOutputForwardValue, 
        peakOutputReverseValue, 
        kTimeoutMs
        );
        this.setPIDValues(kPIDLoopIdx, gains, kTimeoutMs);
    }

    private void configSelectedFeedbackSensor(int kPIDLoopIdx, int kTimeoutMs){
        this.motor.configSelectedFeedbackSensor(
            FeedbackDevice.CTRE_MagEncoder_Absolute,
            kPIDLoopIdx,
            kTimeoutMs
        );                               
    }
    
    private void setSensorPhase(boolean kSensorPhase){
        this.motor.setSensorPhase(kSensorPhase);
    }
    
    private void setFollower(){
        this.follower.configFactoryDefault();
        this.follower.setInverted(true);
        this.follower.follow(motor);
    }

    private void setInverted(boolean kMotorInvert){
        this.motor.setInverted(kMotorInvert);
    }
    
    private void setOutputs(
        double nominalOutputForwardValue, 
        double nominalOutputReverseValue,
        double peakOutputForwardValue, 
        double peakOutputReverseValue, 
        int kTimeoutMs
    )
    {
        this.motor.configNominalOutputForward(nominalOutputForwardValue, kTimeoutMs);
        this.motor.configNominalOutputReverse(nominalOutputReverseValue, kTimeoutMs);
        this.motor.configPeakOutputForward(peakOutputForwardValue, kTimeoutMs);
        this.motor.configPeakOutputReverse(peakOutputReverseValue, kTimeoutMs);
    }
    
    private void setPIDValues(int kPIDLoopIdx, Gains gains, int kTimeoutMs){
        motor.config_kF(kPIDLoopIdx, gains.kF, kTimeoutMs);
        motor.config_kP(kPIDLoopIdx, gains.kP, kTimeoutMs);
        motor.config_kI(kPIDLoopIdx, gains.kI, kTimeoutMs);
        motor.config_kD(kPIDLoopIdx, gains.kD, kTimeoutMs);       
    }
}



