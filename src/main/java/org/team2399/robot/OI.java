/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team2399.robot;

import org.team2399.robot.commands.DriveDistance;
import org.team2399.robot.commands.KajDrive;
import org.team2399.robot.commands.PIDTest;
import org.team2399.robot.commands.Shift;
import org.team2399.robot.commands.TankDrive;
import org.team2399.robot.commands.TestGroup;
import org.team2399.robot.commands.TurnAngle;
import org.team2399.robot.commands.intake.EjectCube;
import org.team2399.robot.commands.intake.ExtendRetract;
import org.team2399.robot.commands.intake.GrabCube;
import org.team2399.robot.commands.intake.OpenArms;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
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
	public static final double DEADBAND_WIDTH = 0.1;
	
	Joystick gamepad;
	
	Joystick joyLeft;
	Joystick joyRight;
	
	Button[] joyLeftButtons;
	Button[] joyRightButtons;
	
	Button button7, button8;
	Button button9, button10;
	Button button4, button3, button1;
	
	final int THROTTLEAXIS =3;
	
	
	public double getLeftStickY() {
//		return deadBand(gamepad.getRawAxis(1) * -1, DEADBAND_WIDTH);
		return joyLeft.getRawAxis(1) * -1;
//		return gamepad.getRawAxis(1) * -1; 
	}
	
	public double getRightStickY() {
//		return deadBand(gamepad.getRawAxis(3) * -1, DEADBAND_WIDTH);
		return joyRight.getRawAxis(1) * -1;
//		return gamepad.getRawAxis(3) * -1; 
	}
	
	public double getLeftStickX() {
//		return deadBand(gamepad.getRawAxis(0), DEADBAND_WIDTH);
		return joyLeft.getRawAxis(0);
//		return gamepad.getRawAxis(0); 
	}
	
	public double getRightStickX() {
//		return deadBand(gamepad.getRawAxis(2), DEADBAND_WIDTH);
		return joyRight.getRawAxis(0);
//		return joyLeft.getRawAxis(2);
//		return gamepad.getRawAxis(2); 
	}
	
	public double getLeftThrottle() {
		return (joyLeft.getRawAxis(THROTTLEAXIS) * -1 + 1) / 2;
	}
	
	public double getRightThrottle() {
		return (joyRight.getRawAxis(THROTTLEAXIS) * -1 + 1) / 2;
	}
	
	public boolean getLeftShoulder() {
		return gamepad.getRawButton(5);
	}
	
	public boolean getRightShoulder() {
		return gamepad.getRawButton(6);
	}
	
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
	
	public OI(Shifter sh, DriveTrain dt, Intake in, AHRS navx) {
		
		gamepad = new Joystick(0);
		joyLeft = new Joystick(1);
		joyRight = new Joystick(2);
		
		joyLeftButtons = new Button[joyLeft.getButtonCount() + 1];
		for(int i = 1; i < joyLeftButtons.length; i++) {
			joyLeftButtons[i] = new JoystickButton(joyLeft, i);
		}
		
		joyRightButtons = new Button[joyRight.getButtonCount() + 1];
		for(int i = 1; i < joyRightButtons.length; i++) {
			joyRightButtons[i] = new JoystickButton(joyRight, i);
		}
		
		joyLeftButtons[3].whenPressed(new Shift(sh, Shift.State.SLOW));
		joyLeftButtons[4].whenPressed(new Shift(sh, Shift.State.FAST));
		
		joyLeftButtons[5].whenPressed(new TankDrive(dt, this));
		joyLeftButtons[6].whenPressed(new KajDrive(dt, this));
		
		joyLeftButtons[12].whenPressed(new TurnAngle(dt, sh, navx, 90, TurnAngle.EndAngleMeaning.RELATIVE));
		joyLeftButtons[11].whenPressed(new TurnAngle(dt, sh, navx, -90, TurnAngle.EndAngleMeaning.RELATIVE));
		
		joyLeftButtons[9].whenPressed(new DriveDistance(dt, sh, navx, 40.0));
		joyLeftButtons[10].whenPressed(new DriveDistance(dt, sh, navx, 100.0));
		
		joyLeftButtons[8].whenPressed(new TestGroup(dt, sh, navx));
		
		joyLeftButtons[1].whileHeld(new GrabCube(in));
		joyLeftButtons[2].whenPressed(new OpenArms(in));
		
		joyRightButtons[3].whileHeld(new EjectCube(in, this));
		joyRightButtons[4].whenPressed(new ExtendRetract(in));
		
//		button7 = new JoystickButton(gamepad, 7); 
//	    button8 = new JoystickButton(gamepad, 8); 
//	     
//	    button9 = new JoystickButton(gamepad, 9); 
//	    button10 = new JoystickButton(gamepad, 10); 
//	     
//	    button7.whenPressed(new Shift(sh, Shift.State.SLOW)); 
//	    button8.whenPressed(new Shift(sh, Shift.State.FAST)); 
//	     
//	    button9.whenPressed(new TankDrive(dt, this)); 
//	    button10.whenPressed(new KajDrive(dt, this));
	}
}
