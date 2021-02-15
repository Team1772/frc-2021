package frc.robot.commands.buffer;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Buffer;

public class Feed extends CommandBase {
    private final Buffer buffer;
    private final DoubleSupplier speed;
    
    public Feed(Buffer buffer, DoubleSupplier speed) {
        this.buffer = buffer;
        this.speed = speed;
        
        addRequirements(this.buffer);
    }
    @Override
        public void execute(){
        this.buffer.setSpeed(speed.getAsDouble());
    }
}
