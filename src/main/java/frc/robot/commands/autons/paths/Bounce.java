package frc.robot.commands.autons.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class Bounce extends SequentialCommandGroup {

  public Bounce(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.buildTrajectory("bounce")
    );
  }
}