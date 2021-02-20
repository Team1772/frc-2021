package frc.robot.routines;

import static java.util.Objects.isNull;

public class Autonomous implements IRoutines {
  private static Autonomous autonomous;

  private Autonomous() { }

  public static Autonomous getInstance(){
    if (isNull(autonomous)) autonomous = new Autonomous();

    return autonomous;
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
