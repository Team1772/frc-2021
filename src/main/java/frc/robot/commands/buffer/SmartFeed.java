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
  
  //else if precisa refatorar
  public void smartFeed() {
    var isSensorBottom = buffer.isSensorBottom();
    var isSensorQueue = buffer.isSensorQueue();
    var isSensorTop = buffer.isSensorTop();
    
    if (isSensorTop) buffer.stop();
    else if (isSensorBottom && !isSensorQueue) {
      buffer.pull();
    } else if (isSensorBottom && isSensorQueue) {
      buffer.pull();
    } else if (isSensorQueue && !isSensorBottom) {
      buffer.stop();
    }
  }
}
