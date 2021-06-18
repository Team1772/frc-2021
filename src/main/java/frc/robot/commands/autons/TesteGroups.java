package frc.robot.commands.autons;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

// speed = 1.2
// aceleration = 1.0

public class TesteGroups extends SequentialCommandGroup {
  public TesteGroups(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.build("path1", "path2")
    );
  }
}