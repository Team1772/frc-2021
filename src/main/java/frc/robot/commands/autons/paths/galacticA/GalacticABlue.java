package frc.robot.commands.autons.paths.galacticA;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticABlue extends SequentialCommandGroup {

  public GalacticABlue(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("galacticA_Blue")
    );
  }
}