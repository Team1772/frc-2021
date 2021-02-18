package frc.robot.commands.autons.galacticA;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticABlueWithPieces extends SequentialCommandGroup {

  public GalacticABlueWithPieces(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("galacticA_blue_0"),
      trajectoryBuilder.buildTrajectory("galacticA_blue_1"),
      trajectoryBuilder.buildTrajectory("galacticA_blue_2")

    );
  }
}
