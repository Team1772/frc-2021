package frc.core.util.PID;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants.PIDTalonConstants;

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
	) {
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
			PIDTalonConstants.kPIDLoopIdx,
			PIDTalonConstants.kTimeoutMs
		);
	}

	private void setSensorPhase(boolean isSensorPhase) {
		this.master.setSensorPhase(isSensorPhase);
	}

	private void setMasterInverted(boolean isInverted) {
		this.master.setInverted(isInverted);
	}

	public void setFollowers(TalonSRX... followers) {
		this.followers = this.configFollowers(followers)
												 .stream()
												 .collect(Collectors.toList());
	}

	public List<TalonSRX> configFollowers(TalonSRX... followers) {
		var configuredFollowers = Arrays.stream(followers)
																		.map(follower -> {
																			follower.configFactoryDefault();
																			return follower;
																		}).collect(Collectors.toList());

		return configuredFollowers;
	}

	//this method has functional programming at branch "bruno's paths"
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
	) {
		this.master.configNominalOutputForward(nominalOutputForwardValue, PIDTalonConstants.kTimeoutMs);
		this.master.configNominalOutputReverse(nominalOutputReverseValue, PIDTalonConstants.kTimeoutMs);
		this.master.configPeakOutputForward(peakOutputForwardValue, PIDTalonConstants.kTimeoutMs);
		this.master.configPeakOutputReverse(peakOutputReverseValue, PIDTalonConstants.kTimeoutMs);
	}

	private void setPIDValues(Gains gains) {
		this.master.config_kF(PIDTalonConstants.kPIDLoopIdx, gains.kF, PIDTalonConstants.kTimeoutMs);
		this.master.config_kP(PIDTalonConstants.kPIDLoopIdx, gains.kP, PIDTalonConstants.kTimeoutMs);
		this.master.config_kI(PIDTalonConstants.kPIDLoopIdx, gains.kI, PIDTalonConstants.kTimeoutMs);
		this.master.config_kD(PIDTalonConstants.kPIDLoopIdx, gains.kD, PIDTalonConstants.kTimeoutMs);
	}
}