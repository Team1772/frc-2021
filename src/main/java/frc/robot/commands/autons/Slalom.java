package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

// speed = 1.2
// aceleration = 1.0

public class Slalom extends SequentialCommandGroup {
  public Slalom(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("slalom")
    );
  }
}
