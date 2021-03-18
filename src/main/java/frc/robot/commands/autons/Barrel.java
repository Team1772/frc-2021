package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

// speed = 1.5
// aceleration = 1.2

public class Barrel extends SequentialCommandGroup {

  public Barrel(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      // trajectoryBuilder.build("barrel")
    );
  }
}
