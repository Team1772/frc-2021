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
    var isSensorBottom = this.buffer.isSensorBottom();
    var isSensorQueue = this.buffer.isSensorQueue();
    var isSensorTop = this.buffer.isSensorTop();
    
    if (isSensorTop) this.buffer.setSpeed(0);
    else if ((isSensorBottom && !isSensorQueue) || (isSensorBottom && isSensorQueue)) {
      this.buffer.setSpeed(1);
    } else if (isSensorQueue && !isSensorBottom) {
      this.buffer.setSpeed(0);
    }
  }
}
