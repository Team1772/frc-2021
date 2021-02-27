package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartSolenoid;
import frc.core.util.PID.Gains;
import frc.core.util.PID.TalonVelocity;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private TalonVelocity shooterPID;
    private SmartSolenoid activator;

    public Shooter() {
        this.shooterPID = new TalonVelocity(
            new TalonSRX(ShooterConstants.motorRightPort), 
            new Gains(
                ShooterConstants.kPVelocity,
                ShooterConstants.kIVelocity,
                ShooterConstants.kDVelocity,
                ShooterConstants.kFVelocity,
                ShooterConstants.kIZoneVelocity,
                ShooterConstants.kPeakOutputVelocity
            ),
            new TalonSRX(ShooterConstants.motorLeftPort)
        );

        this.activator = new SmartSolenoid(ShooterConstants.activatorOne, ShooterConstants.activatorTwo);
    }

    public void setSpeed(double speed) {
        this.shooterPID.setVelocity(speed);
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
