package frc.robot.commands.autons.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class Barrel extends SequentialCommandGroup {

  public Barrel(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("barrel")
    );
  }
}