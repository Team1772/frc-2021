package frc.robot.commands.autons.galacticB;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticBBlueWithPieces extends SequentialCommandGroup {

  public GalacticBBlueWithPieces(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("galacticB_blue_0"),
      trajectoryBuilder.buildTrajectory("galacticB_blue_1"),
      trajectoryBuilder.buildTrajectory("galacticB_blue_2"),
      trajectoryBuilder.buildTrajectory("galacticB_blue_3")
    );
  }
}
