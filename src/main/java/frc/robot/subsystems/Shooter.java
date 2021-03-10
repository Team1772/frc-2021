package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartSolenoid;
import frc.core.util.PID.Gains;
import frc.core.util.PID.TalonVelocity;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
	private final TalonSRX motorLeft, motorRight;
	private final TalonVelocity shooterPID;
	private final SmartSolenoid activator;
	
	public Shooter() {
		this.motorLeft = new TalonSRX(ShooterConstants.motorsPorts[0]);
		this.motorRight = new TalonSRX(ShooterConstants.motorsPorts[1]);

		this.shooterPID = new TalonVelocity(
			this.motorLeft, 
			ShooterConstants.isMotorsInverted,
			ShooterConstants.isFollowerInverted,
			ShooterConstants.isSensorPhase,
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

		this.activator = new SmartSolenoid(ShooterConstants.activatorPorts[0], ShooterConstants.activatorPorts[1]);
	}

	public void setVelocityMetersPerSecond(double velocityMetersPerSecond) {
		this.shooterPID.setVelocityMetersPerSecond(
			velocityMetersPerSecond, 
			ShooterConstants.PID.dutyCycle, 
			ShooterConstants.wheelRadius
		);
	}

	public void setRPM(double rpm) {
		this.shooterPID.setRPM(
			rpm, 
			ShooterConstants.PID.dutyCycle
		);
	}

	public void setSpeed(double speed) {
		this.motorLeft.set(ControlMode.PercentOutput, speed);
		this.motorRight.set(ControlMode.PercentOutput, speed);
	}

	public void enableAngle() {
		this.activator.enable();
	}

	public void disableAngle() {
		this.activator.disable();
	}

	public void stop() {
		this.shooterPID.stop();
	}

	public boolean atSettedVelocity() {
		return this.shooterPID.atSettedVelocity();
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("[SHOOTER] Selected Sensor Velocity", this.shooterPID.getSelectedSensorVelocity());
		SmartDashboard.putNumber("[SHOOTER] Closed Loop Error", this.shooterPID.getClosedLoopError());
		SmartDashboard.putString("[SHOOTER] Activator", this.activator.getValue().toString());
	}
}
