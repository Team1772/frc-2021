package frc.robot.routines;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Robot;
import frc.robot.commands.autons.GalacticA;

public class Autonomous implements IRoutines {
  private static Autonomous autonomous;

  private Command automonousCommand = this.getAutonomousCommand();
  private SendableChooser<Command> chooser = new SendableChooser<>();

  public enum RobotPath {
    //add all paths here
    GALACTIC_A("galaticA");
  
    public final String fileName;
    private RobotPath(String fileName) {
      this.fileName = fileName;
    }

    public static String[] get() {
      var robotPathValues = RobotPath.values();
      var pathsArray = Arrays
                        .stream(robotPathValues)
                        .map(p -> p.fileName)
                        .toArray(String[]::new);

      return pathsArray;
    }
  }

  private Autonomous() { }

  public static Autonomous getInstance(){
    if (isNull(autonomous)) autonomous = new Autonomous();

    return autonomous;
  }

  @Override
  public void init() {
    this.setAutonomousOptions();

    if (nonNull(this.automonousCommand)) this.automonousCommand.schedule();
  }

  @Override
  public void periodic() { }

  @Override
  public void cancel() {
    if (nonNull(this.automonousCommand)) this.automonousCommand.cancel();
  }

  private Command getAutonomousCommand() {
    return this.chooser.getSelected();
  } 

  private void setAutonomousOptions() {
    this.chooser.setDefaultOption("Galactic A", new GalacticA(Robot.trajectoryBuilder));
    this.chooser.addOption("Galactic A", new GalacticA(Robot.trajectoryBuilder));

    SmartDashboard.putData("SELECT AUTONOMOUS", this.chooser);
  }
}