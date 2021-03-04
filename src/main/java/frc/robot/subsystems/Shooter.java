package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartSolenoid;
import frc.core.util.PID.Gains;
import frc.core.util.PID.TalonVelocity;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
	private TalonSRX motorLeft, motorRight;
	private TalonVelocity shooterPID;
	private SmartSolenoid activator;

	public Shooter() {
		this.motorLeft = new TalonSRX(ShooterConstants.motorLeftPort);
		this.motorRight = new TalonSRX(ShooterConstants.motorRightPort);
		this.shooterPID = new TalonVelocity(
			this.motorLeft, 
			new Gains(
				ShooterConstants.PID.kPVelocity,
				ShooterConstants.PID.kIVelocity,
				ShooterConstants.PID.kDVelocity,
				ShooterConstants.PID.kFVelocity,
				ShooterConstants.PID.kIZoneVelocity,
				ShooterConstants.PID.kPeakOutputVelocity
			),
			this.motorRight
		);

		this.activator = new SmartSolenoid(ShooterConstants.activatorOne, ShooterConstants.activatorTwo);
	}

	public void setVelocityMetersPerSecond(double velocityMetersPerSecond) {
		this.shooterPID.setVelocityMetersPerSecond(
			velocityMetersPerSecond, 
			ShooterConstants.PID.dutyCycle, 
			ShooterConstants.wheelRadius
		);
	}

	public void setVelocityRPM(double velocityRPM) {
		this.shooterPID.setVelocityRPM(
			velocityRPM, 
			ShooterConstants.PID.dutyCycle
		);
	}

	public void setSpeed(double speed) {
		this.motorLeft.set(ControlMode.PercentOutput, speed);
		this.motorRight.set(ControlMode.PercentOutput, speed);
	}

	public void enableAngle(){
		this.activator.enable();
}

	public void disableAngle(){
		this.activator.disable();
	}

	public void stop() {
		this.shooterPID.stop();
	}
}
