package frc.core.util.PID;

import java.util.List;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants.DrivetrainConstants.PIDConstants;

/*
 * lista de refatoração
 *  - ordenação de contexto nos construtores
 *  - tirar o k das variaveis no folder PID e no Constants
 *  - refatoração de indentação de metodos
 *  - mudar o atributo master para master
 *  - arrumar identacao de todas as classes do PID + TrajectoryBuilder
 *  - mudar o nome das variaveis dos isInverted && MasterInverted && FollowerInverted
 * 
 */

 /*
  * ------------- ATENÇÃO ---------------
  * Os motores do shooter estão invertidos no robô, então
  *
  * se kMasterInverted = true, ele estará desinvertido
  * se kMasterInverted = false, ele estará invertido
  *
  * se kFollowerInverd = true, ele estará desinvertido
  * se kFollowerInverd = false, ele estará invertido
  */

public abstract class PIDTalon {
    protected TalonSRX master;
    protected List<TalonSRX> followers;

    /*
     * @param kFollowerInverted - if true ? estará desinvertido : estará invertido
     * @param kMasterInverted - if true ? estará desinvertido : estará invertido
     */
    public PIDTalon(
        TalonSRX master,
        boolean kSensorPhase,
        boolean kFollowerInverted,
        double nominalOutputForwardValue,
        double nominalOutputReverseValue,
        double peakOutputForwardValue,
        double peakOutputReverseValue, 
        boolean kMasterInverted,
        Gains gains,
        TalonSRX... followers
    )
    {
        this.master = master;
        this.setMasterInverted(kMasterInverted);

        this.setFollowers(followers);

        this.configSelectedFeedbackSensor();
        this.setSensorPhase(kSensorPhase);

        this.setOutputs(
        nominalOutputForwardValue, 
        nominalOutputReverseValue, 
        peakOutputForwardValue, 
        peakOutputReverseValue
        );
        this.setPIDValues(gains);
    }

    private void configSelectedFeedbackSensor() {
        this.master.configSelectedFeedbackSensor(
            FeedbackDevice.CTRE_MagEncoder_Absolute,
            PIDConstants.kPIDLoopIdx,
            PIDConstants.kTimeoutMs
        );                               
    }
    
    private void setSensorPhase(boolean kSensorPhase) {
        this.master.setSensorPhase(kSensorPhase);
    }

    /*
     * @param isInverted - if true ? estará desinvertido : estará invertido
     */
    private void setMasterInverted(boolean isInverted) {
        this.master.setInverted(isInverted);
    }

    public void setFollowers(TalonSRX... followers) {
        for (TalonSRX follower : followers) {
            this.followers.add(follower);
            follower.configFactoryDefault();
            follower.follow(this.master);
        }
    }

    /*
     * @param isInvertedList - if true ? estará desinvertido : estará invertido
     */
    public void setFollowersInverted(Boolean... isInvertedList){
        int index = 0;
        if(isInvertedList.length > 0){
            for (TalonSRX follower : followers) {
                follower.setInverted(isInvertedList[index]);
                index++;
            }
        }
    }

    private void setOutputs(
        double nominalOutputForwardValue, 
        double nominalOutputReverseValue,
        double peakOutputForwardValue, 
        double peakOutputReverseValue
    )
    {
        this.master.configNominalOutputForward(nominalOutputForwardValue, PIDConstants.kTimeoutMs);
        this.master.configNominalOutputReverse(nominalOutputReverseValue, PIDConstants.kTimeoutMs);
        this.master.configPeakOutputForward(peakOutputForwardValue, PIDConstants.kTimeoutMs);
        this.master.configPeakOutputReverse(peakOutputReverseValue, PIDConstants.kTimeoutMs);
    }
    
    private void setPIDValues(Gains gains) {
        this.master.config_kF(PIDConstants.kPIDLoopIdx, gains.kF, PIDConstants.kTimeoutMs);
        this.master.config_kP(PIDConstants.kPIDLoopIdx, gains.kP, PIDConstants.kTimeoutMs);
        this.master.config_kI(PIDConstants.kPIDLoopIdx, gains.kI, PIDConstants.kTimeoutMs);
        this.master.config_kD(PIDConstants.kPIDLoopIdx, gains.kD, PIDConstants.kTimeoutMs);       
    }
}