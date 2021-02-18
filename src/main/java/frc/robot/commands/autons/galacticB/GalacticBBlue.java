package frc.robot.commands.autons.galacticB;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticBBlue extends SequentialCommandGroup {

  public GalacticBBlue(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("galacticB_blue")
    );
  }
}
