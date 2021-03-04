package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BufferConstants;

public class Buffer extends SubsystemBase {
    private final VictorSP motor;
    private final DigitalInput sensorBottom, sensorLine, sensorTop;

    public Buffer() {
        this.motor = new VictorSP(BufferConstants.motorPort);
        this.sensorBottom = new DigitalInput(BufferConstants.bufferSensors[0]);
        this.sensorLine = new DigitalInput(BufferConstants.bufferSensors[1]);
        this.sensorTop = new DigitalInput(BufferConstants.bufferSensors[2]);

        this.setMotorInverted(true);
        this.periodic();
    }

    @Override
    public void periodic(){
        SmartDashboard.putBoolean("[BUFFER] SENSOR BOTTOM", this.isSensorBottom());
        SmartDashboard.putBoolean("[BUFFER] SENSOR TOP", this.isSensorTop());
        SmartDashboard.putBoolean("[BUFFER] SENSOR LINE", this.isSensorLine());
    }

    public void setSpeed(double speed) {
        this.motor.set(speed);
    }

    public boolean isSensorTop() {
        return !this.sensorTop.get();
      }
    
      public boolean isSensorLine() {
        return !this.sensorLine.get();
      }
    
      public boolean isSensorBottom() {
        return !this.sensorBottom.get();
      }

      
  public void pull() {
    this.motor.set(1);
  }

  public void push() {
    this.motor.set(-1);
  }

  public void stop() {
    this.motor.set(0);
  }

  public void setMotorInverted(boolean isInverted){
      this.motor.setInverted(isInverted);
  }
}
