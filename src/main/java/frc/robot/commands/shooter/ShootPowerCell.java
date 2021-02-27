package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootPowerCell extends CommandBase {
	private final Shooter shooter;
	
	public ShootPowerCell(Shooter shooter){
		this.shooter = shooter;
		
		addRequirements(this.shooter);
	}
	
	@Override
	public void execute() {
		this.shooter.enable();
		this.shooter.setSpeed(1);
	}
}