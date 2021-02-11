package frc.robot.commands.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drivetrain;

public class CurvatureDrive extends CommandBase {
	private Drivetrain drivetrain;
	private DoubleSupplier forward, rotation;

	public CurvatureDrive(Drivetrain drivetrain, DoubleSupplier forward, DoubleSupplier rotation) {
		this.drivetrain = drivetrain;
		this.forward = forward;
		this.rotation = rotation;

		addRequirements(this.drivetrain);
	}

	@Override
  public void execute() {
		this.drivetrain.curvatureDrive(this.forward.getAsDouble(), this.rotation.getAsDouble(), true);
  }
}