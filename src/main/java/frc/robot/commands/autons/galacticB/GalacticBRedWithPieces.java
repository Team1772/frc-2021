package frc.robot.commands.autons.galacticB;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticBRedWithPieces extends SequentialCommandGroup {

  public GalacticBRedWithPieces(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("galacticB_red_0"),
      trajectoryBuilder.buildTrajectory("galacticB_red_1"),
      trajectoryBuilder.buildTrajectory("galacticB_red_2"),
      trajectoryBuilder.buildTrajectory("galacticB_red_3")

    );
  }
}
