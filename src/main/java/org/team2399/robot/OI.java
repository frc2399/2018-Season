/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team2399.robot;

import org.team2399.robot.commands.DriveBasic;
import org.team2399.robot.commands.KajDrive;
import org.team2399.robot.commands.PIDTest;
import org.team2399.robot.commands.Shift;
import org.team2399.robot.commands.TankDrive;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

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
	
	Joystick gamepad;
	
	Joystick joyLeft;
	Joystick joyRight;
	
	Button[] joyLeftButtons;
	Button[] joyRightButtons;
	
	Button button7, button8;
	Button button9, button10;
	Button button4, button3, button1;
	
	public double getLeftStickY() {
		return gamepad.getRawAxis(1) * -1;
//		return joyLeft.getRawAxis(1) * -1;
	}
	
	public double getRightStickY() {
		return gamepad.getRawAxis(3) * -1;
//		return joyRight.getRawAxis(1) * -1;
	}
	
	public double getLeftStickX() {
		return gamepad.getRawAxis(0);
//		return joyLeft.getRawAxis(0);
	}
	
	public double getRightStickX() {
		return gamepad.getRawAxis(2);
//		return joyRight.getRawAxis(0);
//		return joyLeft.getRawAxis(2);
	}
	
	public OI(Shifter sh, DriveTrain dt, AHRS navx) {
		
		gamepad = new Joystick(0);
//		joyLeft = new Joystick(1);
//		joyRight = new Joystick(2);
//		
//		joyLeftButtons = new Button[13];
//		for(int i = 1; i < joyLeftButtons.length; i++) {
//			joyLeftButtons[i] = new JoystickButton(joyLeft, i);
//		}
		
//		joyLeftButtons[3].whenPressed(new Shift(sh, Shift.State.SLOW));
//		joyLeftButtons[4].whenPressed(new Shift(sh, Shift.State.FAST));
//		
//		joyLeftButtons[5].whenPressed(new TankDrive(dt, this));
//		joyLeftButtons[6].whenPressed(new KajDrive(dt, this));
		
		
		button7 = new JoystickButton(gamepad, 7);
		button8 = new JoystickButton(gamepad, 8);
		
		button9 = new JoystickButton(gamepad, 9);
		button10 = new JoystickButton(gamepad, 10);
		
		button3 = new JoystickButton(gamepad, 3);
		button4 = new JoystickButton(gamepad, 4);
		button1 = new JoystickButton(gamepad, 1);
		
		button7.whenPressed(new Shift(sh, Shift.State.SLOW));
		button8.whenPressed(new Shift(sh, Shift.State.FAST));
		
		button9.whenPressed(new TankDrive(dt, this));
		button10.whenPressed(new KajDrive(dt, this));
		
		button4.whileHeld(new PIDTest(dt, this));
		button3.whenPressed(new DriveBasic(dt, sh, navx, 175.0));
		button1.whenPressed(new DriveBasic(dt, sh, navx, 100.0));
		
	}
}
