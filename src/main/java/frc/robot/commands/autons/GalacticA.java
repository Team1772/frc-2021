package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticA extends SequentialCommandGroup {

  public GalacticA(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("autoAwards_0", "autoAwards_1", "autoAwards")
    );
  }
}
