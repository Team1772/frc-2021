package frc.robot.commands.autons.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
  import frc.core.util.TrajectoryBuilder;
  
  public class Slalom extends SequentialCommandGroup {
  
    public Slalom(TrajectoryBuilder trajectoryBuilder) {
      super.addCommands(
        trajectoryBuilder.buildTrajectory("slalom")
      );
    }
  }
