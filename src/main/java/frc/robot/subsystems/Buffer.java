package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BufferConstants;

public class Buffer extends SubsystemBase {
  private final VictorSP motor;
  private final DigitalInput sensorBottom, sensorQueue, sensorTop;

  public Buffer() {
    this.motor = new VictorSP(BufferConstants.motorPort);
    this.sensorBottom = new DigitalInput(BufferConstants.sensorsPorts[0]);
    this.sensorQueue = new DigitalInput(BufferConstants.sensorsPorts[1]);
    this.sensorTop = new DigitalInput(BufferConstants.sensorsPorts[2]);

    this.motor.setInverted(true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("[BUFFER] SENSOR BOTTOM", this.isSensorBottom());
    SmartDashboard.putBoolean("[BUFFER] SENSOR TOP", this.isSensorTop());
    SmartDashboard.putBoolean("[BUFFER] SENSOR LINE", this.isSensorQueue());
  }

  public void setSpeed(double speed) {
    this.motor.set(speed);
  }

  public boolean isSensorTop() {
    return !this.sensorTop.get();
  }

  public boolean isSensorQueue() {
    return !this.sensorQueue.get();
  }

  public boolean isSensorBottom() {
    return !this.sensorBottom.get();
  }
}
