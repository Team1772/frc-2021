package frc.core.components;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class SmartNavX { 
    private final AHRS ahrs; 

    public SmartNavX() {
        this.ahrs = new AHRS(SPI.Port.kMXP);
    }
    
    public double getAngle() {
        return this.ahrs.getAngle();
    }

    public void reset(){
        this.ahrs.reset();
    }

    public Rotation2d getRotation2d() {
        return this.ahrs.getRotation2d();
    }

    public double getRate() {
        return -(this.ahrs.getRate());
    }
}