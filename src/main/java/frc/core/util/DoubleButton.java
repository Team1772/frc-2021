package frc.core.util;

import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class DoubleButton extends Trigger {

    private final XboxController joystick;
    private final int buttonOne, buttonTwo;

    public DoubleButton(XboxController joystick, int buttonOne, int buttonTwo) {
        this.joystick = joystick;
        this.buttonOne = buttonOne;
        this.buttonTwo = buttonTwo;
    }
    
    @Override
    public boolean get() {
        return joystick.getRawButton(buttonOne) && joystick.getRawButton(buttonTwo);
    }
}