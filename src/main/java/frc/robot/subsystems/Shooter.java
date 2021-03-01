package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartSolenoid;
import frc.core.util.PID.Gains;
import frc.core.util.PID.TalonVelocity;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private TalonSRX motorLeft, motorRight;
    private TalonVelocity shooterPID;
    private SmartSolenoid activator;

    public Shooter() {
        this.motorLeft = new TalonSRX(ShooterConstants.motorLeftPort);
        this.motorRight = new TalonSRX(ShooterConstants.motorRightPort);
        this.shooterPID = new TalonVelocity(
            this.motorLeft, 
            new Gains(
                ShooterConstants.PIDConstants.kPVelocity,
                ShooterConstants.PIDConstants.kIVelocity,
                ShooterConstants.PIDConstants.kDVelocity,
                ShooterConstants.PIDConstants.kFVelocity,
                ShooterConstants.PIDConstants.kIZoneVelocity,
                ShooterConstants.PIDConstants.kPeakOutputVelocity
            ),
            this.motorRight
        );

        this.activator = new SmartSolenoid(ShooterConstants.activatorOne, ShooterConstants.activatorTwo);
    }

    public void setVelocity(double speed) {
        this.shooterPID.setVelocity(speed);
    }

    public void setSpeed(double speed) {
        this.motorLeft.set(ControlMode.PercentOutput, speed);
        this.motorRight.set(ControlMode.PercentOutput, speed);
    }

    public void enableAngle(){
        this.activator.enable();
    }

    public void disableAngle(){
        this.activator.disable();
    }

    public void stop() {
       this.shooterPID.setVelocity(0);
    }
}
