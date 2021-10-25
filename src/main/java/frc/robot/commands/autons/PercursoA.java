package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class PercursoA extends ParallelCommandGroup {

  public PercursoA(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.build("percurso-a")
    );
  }
}

