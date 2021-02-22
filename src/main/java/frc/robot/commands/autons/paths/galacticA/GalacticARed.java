package frc.robot.commands.autons.paths.galacticA;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticARed extends SequentialCommandGroup {

  public GalacticARed(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("galacticA_Red")
    );
  }
}