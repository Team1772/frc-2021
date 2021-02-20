package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartSolenoid;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private VictorSPX motor;
  private SmartSolenoid activator;
  
  public Intake() {
    this.motor = new VictorSPX(IntakeConstants.motorPort);
    this.activator = new SmartSolenoid(IntakeConstants.activatorOne, IntakeConstants.activatorTwo);
  }

  public void setSpeed(double speed) {
    this.motor.set(VictorSPXControlMode.PercentOutput, speed);
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

  public void stop() {
    this.motor.set(VictorSPXControlMode.PercentOutput, 0);
}
}
