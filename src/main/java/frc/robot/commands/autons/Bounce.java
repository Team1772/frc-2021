package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

// speed = 1.5
// aceleration = 1

public class Bounce extends SequentialCommandGroup {

  public Bounce(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.build("bounce")
    );
  }
}
