package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.core.util.TrajectoryBuilder;
import frc.robot.routines.Autonomous;
import frc.robot.routines.Teleoperated;
import frc.robot.routines.Autonomous.RobotPath;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Robot extends TimedRobot {
  public static Drivetrain drivetrain;
  public static Intake intake;
  public static Buffer buffer;
  public static Shooter shooter;

  public static Autonomous autonomous;
  public static TrajectoryBuilder trajectoryBuilder;

  @Override
  public void robotInit() { 
    drivetrain = new Drivetrain();
    intake = new Intake();
    buffer = new Buffer();
    shooter = new Shooter();

    
    trajectoryBuilder = new TrajectoryBuilder(drivetrain, RobotPath.get());
    
    Autonomous.getInstance().configOptions();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    drivetrain.reset();
  }

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
    //its may causing bugs 
    Teleoperated.getInstance().periodic();

  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}
}
