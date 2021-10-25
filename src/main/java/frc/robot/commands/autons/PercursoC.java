package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class PercursoC extends ParallelCommandGroup {

  public PercursoC(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.build("percurso-c")
    );
  }
}