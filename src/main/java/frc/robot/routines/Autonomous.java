package frc.robot.routines;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.autons.GalacticA;

public class Autonomous implements IRoutines {
  private static Autonomous autonomous;

  private Command automonousCommand;
  private SendableChooser<Command> chooser;
  private TrajectoryBuilder trajectoryBuilder;

  private Autonomous() { }

  public static Autonomous getInstance(){
    if (isNull(autonomous)) return new Autonomous();

    return autonomous;
  }

  @Override
  public void init() {
    this.chooser = new SendableChooser<>();
    this.setAutonomousOptions();

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

  private void setAutonomousOptions() {
    this.chooser.setDefaultOption("Galactic A", new GalacticA(this.trajectoryBuilder));
    this.chooser.addOption("Galactic A", new GalacticA(this.trajectoryBuilder));

    SmartDashboard.putData("SELECT AUTONOMOUS", this.chooser);
  } 
}