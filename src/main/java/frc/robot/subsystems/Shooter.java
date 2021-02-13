package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private final TalonSRX motorRight, motorLeft;

    public Shooter() {
        this.motorRight = new TalonSRX(ShooterConstants.motorRightPort);
        this.motorLeft = new TalonSRX(ShooterConstants.motorLeftPort);
    }

    public void setSpeed(double speed) {
        this.motorRight.set(ControlMode.PercentOutput, speed);
        this.motorLeft.set(ControlMode.PercentOutput, speed);
    }
}
