package frc.core.components;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class SmartNavX { 
    private final AHRS ahrs; 

    public SmartNavX() {
        this.ahrs = new AHRS(SPI.Port.kMXP);
    }
}
