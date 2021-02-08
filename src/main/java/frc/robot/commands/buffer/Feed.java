package frc.robot.commands.buffer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Buffer;

public class Feed extends CommandBase {
    private final Buffer buffer;
    
    public Feed(Buffer buffer) {
        this.buffer = buffer;
        
        addRequirements(this.buffer);
    }
    @Override
        public void execute(){
        this.buffer.setSpeed(1);
    }
}
