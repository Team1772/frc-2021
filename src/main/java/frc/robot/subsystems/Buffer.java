package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BufferConstants;

public class Buffer extends SubsystemBase {
	private VictorSP motor;

	public Buffer() {
		this.motor = new VictorSP(BufferConstants.motorPort);
	}

	public void setSpeed(double speed) {
		this.motor.set(speed);
	}
}
