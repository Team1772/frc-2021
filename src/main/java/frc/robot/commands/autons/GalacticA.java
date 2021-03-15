package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class GalacticA extends ParallelCommandGroup {

  public GalacticA(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      // trajectoryBuilder.build("galacticA")
    );
  }
}
