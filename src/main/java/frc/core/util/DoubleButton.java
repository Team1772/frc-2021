package frc.core.util;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class DoubleButton extends Trigger {

  private final GenericHID joystick;
  private final int buttonNumberOne, buttonNumberTwo;

  public DoubleButton(GenericHID joystick, int buttonNumberOne, int buttonNumberTwo) {
    this.joystick = joystick;
    this.buttonNumberOne = buttonNumberOne;
    this.buttonNumberTwo = buttonNumberTwo;
  }
  
  @Override
  public boolean get() {
    return joystick.getRawButton(buttonNumberOne) && joystick.getRawButton(buttonNumberTwo);
  }
}