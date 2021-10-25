package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class PercursoB extends ParallelCommandGroup {

  public PercursoB(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.build("percurso-b")
    );
  }
}
