package frc.core.components;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class SmartSolenoid {
	private DoubleSolenoid solenoid;

	public SmartSolenoid(int channelOne, int channelTwo){
		this.solenoid = new DoubleSolenoid(channelOne, channelTwo);
	}

	public void enable() {
		this.solenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void disable() {
		this.solenoid.set(DoubleSolenoid.Value.kReverse); 
	}

	public void toggle() {
		this.solenoid.toggle();      
	}   
}
