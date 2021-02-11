package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TalonVelocity extends PIDTalon {

    public TalonVelocity(
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
        super(
            master, 
            kSensorPhase,
            kFollowerInverted,
            nominalOutputForwardValue,
            nominalOutputReverseValue, 
            peakOutputForwardValue, 
            peakOutputReverseValue, 
            kMasterInverted, 
            gains,
            followers
        );
    }

    /*
     * @param kFollowerInverted - if true ? estará desinvertido : estará invertido
     * @param kMasterInverted - if true ? estará desinvertido : estará invertido
     */
    public TalonVelocity(
        TalonSRX master,
        boolean kFollowerInverted,
        boolean kMasterInverted,
        Gains gains,
        TalonSRX... followers
    )
    {
        this(
            master,
            true,
            kFollowerInverted,
            0,
            0,
            0,
            0,
            kMasterInverted,
            gains,
            followers
        );
    }
    /*
     * kMasterInverted e kFollowerInverted estão como default desinvertidos
     */
    public TalonVelocity(
        TalonSRX master,
        Gains gains,
        TalonSRX... followers
    )
    {
        this(
            master,
            true,
            true,
            0,
            0,
            0,
            0,
            true,
            gains,
            followers
        );
    }
    
    public double getSensorVelocity() {
        SmartDashboard.putNumber(
            "master velocity", 
            super.master.getSelectedSensorVelocity()
        );
        
        return super.master.getSelectedSensorVelocity();
    }

    public void setVelocity(double velocity) {
        super.master.set(ControlMode.Velocity, velocity);
    }

    public void stop(){
        super.master.set(ControlMode.PercentOutput, 0);
    }
}
