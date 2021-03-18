package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.core.components.Limelight;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.buffer.SmartFeed;
import frc.robot.commands.intake.CollectPowerCell;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;

public class GalacticA extends ParallelCommandGroup {
  public GalacticA(TrajectoryBuilder trajectoryBuilder, Buffer buffer, Intake intake) {
    Limelight.setPipeline(1);

    var galacticCommand = Limelight.isTarget() ? "red" : "blue";
    var path =  String.format("galacticA_%s", galacticCommand);

    super.addCommands(
      trajectoryBuilder.build(path),
      new SmartFeed(buffer),
      new CollectPowerCell(intake)
    );
  }
}
