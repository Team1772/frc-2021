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

  private SendableChooser<Command> chooser = new SendableChooser<>();
  private Command automonousCommand;

  public enum RobotPath {
    //add all paths here
    GALACTIC_A("galacticA");
    // GALACTIC_B("galacticB"),
    // SLALOM("slalom"),
    // BOUNCE("bounce"),
    // BARREL("barrel");
  
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
    this.configOptions();
    this.automonousCommand = this.getAutonomousCommand();

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

  public void configOptions() {
    this.chooser.setDefaultOption("Galactic A", new GalacticA(Robot.trajectoryBuilder));
    this.chooser.addOption("Galactic A", new GalacticA(Robot.trajectoryBuilder));
    // waiting for merge autonomous classes
    // this.chooser.addOption("Galactic B", new GalacticB(Robot.trajectoryBuilder));
    // this.chooser.addOption("Slalom", new Slalom(Robot.trajectoryBuilder));
    // this.chooser.addOption("Bounce", new Bounce(Robot.trajectoryBuilder));
    // this.chooser.addOption("Barrel", new Barrel(Robot.trajectoryBuilder));

    SmartDashboard.putData("SELECTED AUTONOMOUS", this.chooser);
  }
}