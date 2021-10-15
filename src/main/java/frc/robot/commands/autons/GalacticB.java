package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.core.components.Limelight;
import frc.core.util.TrajectoryBuilder;
import frc.robot.Constants.FieldConstants;
import frc.robot.commands.buffer.SmartFeed;
import frc.robot.commands.intake.CollectPowerCell;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;

public class GalacticB extends ParallelCommandGroup {

  public GalacticB(TrajectoryBuilder trajectoryBuilder, Buffer buffer, Intake intake) {

    var allianceColor = Limelight.isTarget() ? FieldConstants.red : FieldConstants.blue;
    var path =  String.format("galacticB_%s", allianceColor);

    super.addCommands(
      trajectoryBuilder.build(path),
      new SmartFeed(buffer),
      new CollectPowerCell(intake)
    );
  }
}
