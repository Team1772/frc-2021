package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.routines.Autonomous;
import frc.robot.routines.Teleoperated;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class Robot extends TimedRobot {
  public static Drivetrain drivetrain;
  public static Intake intake;

  @Override
  public void robotInit() { 
    drivetrain = new Drivetrain();
    intake = new Intake();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    Autonomous.getInstance().init();
  }

  @Override
  public void autonomousPeriodic() {
    Autonomous.getInstance().periodic();
  }

  @Override
  public void teleopInit() {
    Autonomous.getInstance().cancel();
    Teleoperated.getInstance().init();
  }

  @Override
  public void teleopPeriodic() {
    Teleoperated.getInstance().periodic();

  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}
}
