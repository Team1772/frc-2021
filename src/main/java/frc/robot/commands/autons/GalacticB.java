package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticB extends ParallelCommandGroup {

  public GalacticB(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("galacticB")
    );
  }
}
