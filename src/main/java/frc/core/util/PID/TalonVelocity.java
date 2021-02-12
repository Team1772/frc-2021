package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DrivetrainConstants.PIDConstants;

public class TalonVelocity extends PIDTalon {

    public TalonVelocity(
        TalonSRX master, 
        boolean kMasterInverted,
        boolean kFollowerInverted,
        boolean kSensorPhase,
        double nominalOutputForwardValue, 
        double nominalOutputReverseValue, 
        double peakOutputForwardValue,
        double peakOutputReverseValue, 
        Gains gains,
        TalonSRX... followers
    )
    {
        super(
            master, 
            kMasterInverted, 
            kFollowerInverted,
            kSensorPhase,
            nominalOutputForwardValue,
            nominalOutputReverseValue, 
            peakOutputForwardValue, 
            peakOutputReverseValue, 
            gains,
            followers
        );
    }

    /*
     * @param kFollowerInverted - if true ? 1 : -1
     * @param kMasterInverted - if true ? 1 : -1
     */
    public TalonVelocity(
        TalonSRX master,
        boolean kMasterInverted,
        boolean kFollowerInverted,
        Gains gains,
        TalonSRX... followers
    )
    {
        this(
            master,
            kMasterInverted,
            kFollowerInverted,
            true,
            PIDConstants.nominalOutputForwardValue,
            PIDConstants.nominalOutputReverseValue,
            PIDConstants.peakOutputForwardValue,
            PIDConstants.peakOutputReverseValue,
            gains,
            followers
        );
    }
    /*
     * kMasterInverted e kFollowerInverted estão como default -1
     */
    public TalonVelocity(
        TalonSRX master,
        Gains gains,
        TalonSRX... followers
    )
    {
        this(
            master,
            false,
            false,
            true,
            PIDConstants.nominalOutputForwardValue,
            PIDConstants.nominalOutputReverseValue,
            PIDConstants.peakOutputForwardValue,
            PIDConstants.peakOutputReverseValue,
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
