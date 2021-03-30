package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootPowerCellAngle extends CommandBase {
	private final Shooter shooter;
	private double speed;
	
	public ShootPowerCellAngle(Shooter shooter, double speed) {
		this.shooter = shooter;
		this.speed = speed;
		
		addRequirements(this.shooter);
	}
	
	@Override
	public void execute() {
		this.shooter.enableAngle();
		this.shooter.setSpeed(this.speed);
	}
	
	@Override
	public void end(boolean isInterrupted) {
		this.shooter.stop();
		this.shooter.disableAngle();
	}
}