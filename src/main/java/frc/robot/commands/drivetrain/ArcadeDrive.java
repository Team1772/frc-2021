package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDrive extends CommandBase {
  private Drivetrain drivetrain;
  private DoubleSupplier forward, rotation;

  public ArcadeDrive(Drivetrain drivetrain, DoubleSupplier forward, DoubleSupplier rotation) {
    this.drivetrain = drivetrain;
    this.forward = forward;
    this.rotation = rotation;

    addRequirements(this.drivetrain);
  }

  @Override
  public void execute() {
    this.drivetrain.arcadeDrive(this.forward.getAsDouble(), this.rotation.getAsDouble());
  }
}