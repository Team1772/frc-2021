package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartSolenoid;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private VictorSP motor;
  private SmartSolenoid activator;
  
  public Intake() {
    this.motor = new VictorSP(IntakeConstants.motorPort);
    this.activator = new SmartSolenoid(IntakeConstants.activatorOne, IntakeConstants.activatorTwo);
  }

  public void setSpeed(double speed) {
    this.motor.set(speed);
  }

  public void enable(){
    this.activator.enable();
  }

  public void disable(){
    this.activator.disable();
  }

  public void toggle(){
    this.activator.toggle();
  }
}
