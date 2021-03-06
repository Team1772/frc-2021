package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private VictorSPX motorLeft, motorRight;
  
  public Intake() {
    this.motorLeft = new VictorSPX(IntakeConstants.motorsPorts[0]);
    this.motorRight = new VictorSPX(IntakeConstants.motorsPorts[1]);
  }

  public void setSpeed(double speedY, double speedX) {
    this.motorLeft.set(ControlMode.PercentOutput, speedY);
    this.motorRight.set(ControlMode.PercentOutput, speedX);
  }

  public void stop() {
    this.motorLeft.set(ControlMode.PercentOutput, 0);
    this.motorRight.set(ControlMode.PercentOutput, 0);
  }
}
