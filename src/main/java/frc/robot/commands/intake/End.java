package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class End extends CommandBase {
	private final Intake intake;
	
	public End(Intake intake){
		this.intake = intake;
		
		addRequirements(this.intake);
  }
  @Override
	public void end(boolean interrupted) {
		this.intake.stop();
		this.intake.disable();
  }
}