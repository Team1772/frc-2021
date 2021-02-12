package frc.core.util.PID;

import java.util.List;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants.DrivetrainConstants.PIDConstants;

/*
 * lista de refatoração
 *  - tirar o k das variaveis nos PIDConstants (nao da pra refatorar ainda pq tem coisa sendo usada no robotContainer)
 */

public abstract class PIDTalon {
	protected TalonSRX master;
	protected List<TalonSRX> followers;

	public PIDTalon(
		TalonSRX master, 
		boolean isMasterInverted, 
		boolean isFollowerInverted, 
		boolean isSensorPhase,
		double nominalOutputForwardValue, 
		double nominalOutputReverseValue, 
		double peakOutputForwardValue,	
		double peakOutputReverseValue, 
		Gains gains, 
		TalonSRX... followers
	)
	{
		this.master = master;
		this.setMasterInverted(isMasterInverted);

		this.setFollowers(followers);

		this.configSelectedFeedbackSensor();
		this.setSensorPhase(isSensorPhase);

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
			FeedbackDevice.CTRE_MagEncoder_Relative,
			PIDConstants.kPIDLoopIdx,
			PIDConstants.kTimeoutMs
		);
	}

	private void setSensorPhase(boolean isSensorPhase) {
		this.master.setSensorPhase(isSensorPhase);
	}

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

	public void setFollowersInverted(Boolean... isInvertedList) {
		int index = 0;
		if (isInvertedList.length >= this.followers.size()) {
			for (TalonSRX follower : followers) {
				follower.setInverted(isInvertedList[index]);
				index++;
			}
		} else {
			DriverStation.reportError(
				"the length of varags must be equal or higher than the list of followers",
				new Exception().getStackTrace()
			);
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