package frc.robot.commands.buffer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Buffer;

public class SmartFeed extends CommandBase{
  private final Buffer buffer;

  public SmartFeed(Buffer buffer) {
    this.buffer = buffer;

    addRequirements(this.buffer);
  }

  @Override
  public void execute() {
    this.smartFeed();
  }
  
  //precisa refatorar identacao
  public void smartFeed() {
    if (buffer.isSensorTop()) buffer.stop();
    else if (buffer.isSensorBottom()) {
        buffer.pull();
    } else if (buffer.isSensorLine() && !buffer.isSensorBottom() & !buffer.isSensorTop()) {
      buffer.stop();
    }
  }
}
