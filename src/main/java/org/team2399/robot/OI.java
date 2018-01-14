/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team2399.robot;

import org.team2399.robot.commands.KajDrive;
import org.team2399.robot.commands.Shift;
import org.team2399.robot.commands.TankDrive;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Shifter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	Joystick stick;
	Button button7, button8;
	Button button9, button10;
	
	public double getLeftStickY() {
		return stick.getRawAxis(1) * -1;
	}
	
	public double getRightStickY() {
		return stick.getRawAxis(3) * -1;
	}
	
	public double getLeftStickX() {
		return stick.getRawAxis(0);
	}
	
	public double getRightStickX() {
		return stick.getRawAxis(2);
	}
	
	public OI(Shifter sh, DriveTrain dt) {
		stick = new Joystick(0);
		
		
		button7 = new JoystickButton(stick, 7);
		button8 = new JoystickButton(stick, 8);
		
		button9 = new JoystickButton(stick, 9);
		button10 = new JoystickButton(stick, 10);
		
		button7.whenPressed(new Shift(sh, Shift.State.SLOW));
		button8.whenPressed(new Shift(sh, Shift.State.FAST));
		
		button9.whenPressed(new TankDrive(dt, this));
		button10.whenPressed(new KajDrive(dt, this));
		
	}
}
