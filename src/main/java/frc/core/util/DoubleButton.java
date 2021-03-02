package frc.core.util;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.Button;

public class DoubleButton extends Trigger {

    private final Button buttonOne, buttonTwo;

    public DoubleButton(Button buttonOne, Button buttonTwo) {
        this.buttonOne = buttonOne;
        this.buttonTwo = buttonTwo;
    }
    
    @Override
    public boolean get() {
        return buttonOne.get() && buttonTwo.get();
    }
}