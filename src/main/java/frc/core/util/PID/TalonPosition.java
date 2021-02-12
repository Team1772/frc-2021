package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants.DrivetrainConstants.PIDConstants;

public class TalonPosition extends PIDTalon {

    public TalonPosition(
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

        this.setAbsolutePosition(kMasterInverted, kSensorPhase);
    }

    /*
     * @param kFollowerInverted - if true ? 1 : -1
     * @param kMasterInverted - if true ? 1 : -1
     */
    public TalonPosition(
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
    public TalonPosition(
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

    private int getAbsolutePosition() {
        return super.master.getSensorCollection().getPulseWidthPosition();
    }

    public boolean isMaxPosition(double value) {
        return (this.getSelectedSensorPosition() - this.getSelectedSensorPosition()) < (-value);
    }

    public double getSelectedSensorPosition() {
        return super.master.getSelectedSensorPosition(0);
    }

    public double getMasterOutput() {
        return super.master.getMotorOutputPercent();
    } 

    public void setPostion(int position) {
        super.master.set(ControlMode.Position, this.getSelectedSensorPosition() - position);
    }

    private void setAbsolutePosition(
        boolean kMasterInverted, 
        boolean kSensorPhase
    )
    {
        int absolutePosition = this.getAbsolutePosition();

        absolutePosition &= 0xFFF;

        if (kSensorPhase) { absolutePosition *= -1; }
        if (kMasterInverted) { absolutePosition *= -1; }
        
        super.master.setSelectedSensorPosition(absolutePosition, PIDConstants.kPIDLoopIdx, PIDConstants.kTimeoutMs);
    }
}
