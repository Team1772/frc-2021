package frc.robot.commands.autons.galacticA;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticARedWithPieces extends SequentialCommandGroup {

  public GalacticARedWithPieces(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("galacticA_red_0"),
      trajectoryBuilder.buildTrajectory("galacticA_red_1"),
      trajectoryBuilder.buildTrajectory("galacticA_red_2")
    );
  }
}
