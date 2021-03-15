package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class PullPowerCell extends CommandBase {
	private final Shooter shooter;
	
	public PullPowerCell(Shooter shooter) {
		this.shooter = shooter;
		
		addRequirements(this.shooter);
	}
	
	@Override
	public void execute() {
		this.shooter.setSpeed(-1);
	}
		
	@Override
	public void end(boolean isInterrupted) {
		this.shooter.stop();
	}
}