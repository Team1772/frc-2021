package frc.robot.commands.autons.paths.galacticB;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticBRed extends SequentialCommandGroup {

  public GalacticBRed(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("galacticB_Red")
    );
  }
}