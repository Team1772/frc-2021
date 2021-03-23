package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootPowerCellAngle extends CommandBase {
	private final Shooter shooter;
	
	public ShootPowerCellAngle(Shooter shooter) {
		this.shooter = shooter;
		
		addRequirements(this.shooter);
	}
	
	@Override
	public void execute() {
		this.shooter.enableAngle();
		// this.shooter.setVelocityMetersPerSecond(30);
	}
	
	@Override
	public void end(boolean isInterrupted) {
		this.shooter.stop();
		this.shooter.disableAngle();
	}
}