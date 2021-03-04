package frc.core.util.PID;

import static frc.core.util.function.For.forWithCounter;

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

		this.followers = Arrays.stream(followers).collect(Collectors.toList());
		this.configFollowers();

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

	public void configFollowers() {
	this.followers.stream()
								.forEach(follower -> {
									follower.configFactoryDefault();
									follower.follow(this.master);
								});
	}

	public void setFollowersInverted(Boolean... isInverted) {
		if (isInverted.length >= this.followers.size()) {
			forWithCounter(this.followers, (i, follower) -> {
				follower.setInverted(isInverted[i]);		
		 	});
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