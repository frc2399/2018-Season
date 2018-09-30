package org.team2399.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public abstract class OI {

	public static final double DEADBAND_WIDTH = 0.1;

	public static double deadBand(double num, double width) {
		double slope = 1.0 / (1.0 - width);
		if (num >= width) {
			return slope * (num - width);
		} else if(num <= -width) {
			return slope * (num + width);
		} else {
			return 0;
		}
	}
	
	public static double throttleToPositiveRange(double input) {
		return (input + 1) / 2;
	}
	
	public static Button[] getButtons(Joystick controller) {
		Button[] controllerButtons = new Button[controller.getButtonCount() + 1];
		for(int i = 1; i < controllerButtons.length; i++) {
			controllerButtons[i] = new JoystickButton(controller, i);
		}
		return controllerButtons;
	}
	
	public abstract boolean deadmanActive();

	public OI() {
		super();
	}
	
	public abstract Command defaultDrive();
	public abstract Command defaultShift();

}