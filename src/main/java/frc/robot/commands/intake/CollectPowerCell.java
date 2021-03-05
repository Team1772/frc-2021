package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class CollectPowerCell extends CommandBase {
	private final Intake intake;
	
	public CollectPowerCell(Intake intake){
		this.intake = intake;
		
		addRequirements(this.intake);
	}
	
	@Override
	public void execute() {
		this.intake.enable();
		this.intake.setSpeed(0.40);
	}
	
	@Override
	public void end(boolean isInterrupted) {
		this.intake.stop();
		this.intake.disable();
  }
}
