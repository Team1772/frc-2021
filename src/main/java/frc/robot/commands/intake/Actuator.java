package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class Actuator extends CommandBase {
    private final Intake intake;
    
    public Actuator(Intake intake) {
        this.intake = intake;
        
        addRequirements(this.intake);
    }
    
    @Override
    public void execute(){
        this.intake.setSpeed(0);
        this.intake.toggleIntake();
    }
}
