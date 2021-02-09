package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants.DrivetrainConstants.PIDConstants;

/*
 * O Gabriela da uma olhada no que fiz na questão dos followers
 * 
 * No construtor, tratei o atributo follower e o spread de followers como um
 * "list of followers", então, ao usar o metodo setFollowersInverted(param),
 * follower e spread de follower vão receber o valor do param.  
 *
 */

/*
 * lista de refatoração
 *  - ordenação de contexto nos construtores
 *  - tirar o k das variaveis no folder PID e no Constants
 *  - refatoração de indentação de metodos
 * 
 */

public abstract class PIDTalon {
    protected TalonSRX motor, follower;

    public PIDTalon(
        TalonSRX motor,
        TalonSRX follower,
        boolean kSensorPhase,
        boolean kFollowerInverted,
        double nominalOutputForwardValue,
        double nominalOutputReverseValue,
        double peakOutputForwardValue,
        double peakOutputReverseValue, 
        boolean kMotorInverted,
        Gains gains,
        TalonSRX... followers
    )
    {
        this.motor = motor;
        this.follower = follower;

        this.configSelectedFeedbackSensor();
        this.setSensorPhase(kSensorPhase);

        this.setMotorInverted(kMotorInverted);

        this.setFollower();
        this.setFollowers(followers);
        this.setFollowersInverted(kFollowerInverted, this.follower);

        this.setOutputs(
        nominalOutputForwardValue, 
        nominalOutputReverseValue, 
        peakOutputForwardValue, 
        peakOutputReverseValue
        );
        this.setPIDValues(gains);
    }

    //can set true or false at @param kMotorInvert
    public PIDTalon(
        TalonSRX motor, 
        TalonSRX follower, 
        boolean kSensorPhase,
        boolean kFollowerInverted, 
        double peakOutputForwardValue,
        double peakOutputReverseValue, 
        boolean kMotorInvert, 
        Gains gains
    )
    {
    this(
        motor, 
        follower, 
        kSensorPhase,
        kFollowerInverted,
        0,
        0,
        peakOutputForwardValue, 
        peakOutputReverseValue, 
        kMotorInvert, 
        gains
    );
    }

    //@param kMotorInvert is false
    public PIDTalon(
        TalonSRX motor, 
        TalonSRX follower, 
        boolean kSensorPhase,
        boolean kFollowerInverted,
        double peakOutputForwardValue,
        double peakOutputReverseValue, 
        Gains gains
    )
    {
    this(
        motor, 
        follower, 
        kSensorPhase,
        kFollowerInverted,
        0,
        0,
        peakOutputForwardValue, 
        peakOutputReverseValue, 
        false, 
        gains
    );
    }

    private void configSelectedFeedbackSensor() {
        this.motor.configSelectedFeedbackSensor(
            FeedbackDevice.CTRE_MagEncoder_Absolute,
            PIDConstants.kPIDLoopIdx,
            PIDConstants.kTimeoutMs
        );                               
    }
    
    private void setSensorPhase(boolean kSensorPhase) {
        this.motor.setSensorPhase(kSensorPhase);
    }

    private void setMotorInverted(boolean bool) {
        this.motor.setInverted(bool);
    }
    
    private void setFollower() {
        this.follower.configFactoryDefault();
        this.follower.follow(this.motor);
    }

    public void setFollowers(TalonSRX... followers) {
        for (TalonSRX follower : followers) {
            follower.follow(this.motor);
       }
    }

    public void setFollowersInverted(boolean bool, TalonSRX... followers){
        for (TalonSRX follower : followers) {
            follower.setInverted(bool);
       }
    }
   
    private void setOutputs(
        double nominalOutputForwardValue, 
        double nominalOutputReverseValue,
        double peakOutputForwardValue, 
        double peakOutputReverseValue
    )
    {
        this.motor.configNominalOutputForward(nominalOutputForwardValue, PIDConstants.kTimeoutMs);
        this.motor.configNominalOutputReverse(nominalOutputReverseValue, PIDConstants.kTimeoutMs);
        this.motor.configPeakOutputForward(peakOutputForwardValue, PIDConstants.kTimeoutMs);
        this.motor.configPeakOutputReverse(peakOutputReverseValue, PIDConstants.kTimeoutMs);
    }
    
    private void setPIDValues(Gains gains) {
        this.motor.config_kF(PIDConstants.kPIDLoopIdx, gains.kF, PIDConstants.kTimeoutMs);
        this.motor.config_kP(PIDConstants.kPIDLoopIdx, gains.kP, PIDConstants.kTimeoutMs);
        this.motor.config_kI(PIDConstants.kPIDLoopIdx, gains.kI, PIDConstants.kTimeoutMs);
        this.motor.config_kD(PIDConstants.kPIDLoopIdx, gains.kD, PIDConstants.kTimeoutMs);       
    }
}