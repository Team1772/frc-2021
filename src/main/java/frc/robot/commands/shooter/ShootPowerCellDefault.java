package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootPowerCellDefault extends CommandBase {
	private final Shooter shooter;
	
	public ShootPowerCellDefault(Shooter shooter){
		this.shooter = shooter;
		
		addRequirements(this.shooter);
	}
	
	@Override
	public void execute() {
		this.shooter.setSpeed(0.8);
	}

	@Override
	public void end(boolean isInterrupted) {
		this.shooter.stop();
  }
}