package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BufferConstants;

public class Buffer extends SubsystemBase {
    private Victor motor;

    public Buffer() {
        this.motor = new Victor(BufferConstants.motorPort);
    }

    public void setSpeed(double speed) {
        this.motor.set(speed);
    }
}
