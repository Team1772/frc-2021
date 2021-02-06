package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartSolenoid;
import frc.robot.Constants.IntakeConstants;


public class Intake extends SubsystemBase {
  private  VictorSP motor;
  private  SmartSolenoid intakeActivator;
  
  public Intake() {
    this.motor = new VictorSP(IntakeConstants.motorPort);
    this.intakeActivator = new SmartSolenoid(IntakeConstants.intakeActivatorOne, IntakeConstants.intakeActivatorTwo);
  }

  public void setSpeed(double speed) {
    this.motor.set(speed);
  }

  public void enableIntake(){
    this.intakeActivator.enable();

  }

  public void disableIntake(){
    this.intakeActivator.disable();
  }
}



