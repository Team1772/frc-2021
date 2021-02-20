package frc.robot.routines;

import static java.util.Objects.isNull;

public class Teleoperated implements IRoutines {
  private static Teleoperated teleop;

  private Teleoperated() { }

  public static Teleoperated getInstance(){
    if (isNull(teleop)) teleop = new Teleoperated();

    return teleop;
}

  @Override
  public void init() {
    // TODO Auto-generated method stub

  }

  @Override
  public void periodic() {
    // TODO Auto-generated method stub

  }

  @Override
  public void cancel() {
    // TODO Auto-generated method stub

  }
  
}
