package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartSolenoid;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private VictorSPX motorLeft, motorRight;
  private SmartSolenoid activator;
  
  public Intake() {
    this.motorLeft = new VictorSPX(IntakeConstants.motorsPorts[0]);
    this.motorRight = new VictorSPX(IntakeConstants.motorsPorts[1]);

    this.activator = new SmartSolenoid(IntakeConstants.activatorsPorts[0], IntakeConstants.activatorsPorts[1]);
  }

  //os motores tem que ter velocidades diferentes
  public void setSpeed(double speed) {
    this.motorLeft.set(ControlMode.PercentOutput, speed);
    this.motorRight.set(ControlMode.PercentOutput, speed);

    System.out.println(speed);
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
    this.motorLeft.set(ControlMode.PercentOutput, 0);
    this.motorRight.set(ControlMode.PercentOutput, 0);
  }
}
