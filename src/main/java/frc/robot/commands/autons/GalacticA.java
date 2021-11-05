package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.intake.CollectPowerCell;
import frc.robot.subsystems.Intake;

public class GalacticA extends ParallelCommandGroup {

  public GalacticA(TrajectoryBuilder trajectoryBuilder, Intake intake) {
    super.addCommands(
      trajectoryBuilder.build("galactic-a"),
      new CollectPowerCell(intake)
    );
  }
}
