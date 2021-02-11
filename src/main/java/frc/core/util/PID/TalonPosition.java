package frc.core.util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants.DrivetrainConstants.PIDConstants;

public class TalonPosition extends PIDTalon {

    public TalonPosition(
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

        this.setAbsolutePosition(kMasterInverted, kSensorPhase);
    }

    /*
     * @param kFollowerInverted - if true ? estará desinvertido : estará invertido
     * @param kMasterInverted - if true ? estará desinvertido : estará invertido
     */
    public TalonPosition(
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
    public TalonPosition(
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
