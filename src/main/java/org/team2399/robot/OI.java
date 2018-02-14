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
	public boolean using2Joysticks = false;
	public boolean using3Joysticks = false;
	public boolean usingGamepad = false;
	public boolean usingXBox = false;
	
	
	public Joystick gamepad;
	public Joystick joyLeft;
	public Joystick joyRight;
	public Joystick joyControl;
	public Joystick xBox;
	
	Button[] gamepadButtons;
	Button[] joyLeftButtons;
	Button[] joyRightButtons;
	Button[] joyControlButtons;
	Button[] xBoxButtons;
	
	final int THROTTLEAXIS =3;
	
	
	public double getLeftThrottle() {
		return (joyLeft.getRawAxis(THROTTLEAXIS) * -1 + 1) / 2;
	}
	
	public double getRightThrottle() {
		return (joyRight.getRawAxis(THROTTLEAXIS) * -1 + 1) / 2;
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
	
	public OI(Shifter sh, DriveTrain dt, Intake in, AHRS navx, Console con) {
		
		gamepad = new Joystick(0);
		joyLeft = new Joystick(1);
		joyRight = new Joystick(2);
		joyControl = new Joystick (4);
		xBox = new Joystick(3);
		
		gamepadButtons = new Button[gamepad.getButtonCount() + 1];
		for(int i = 1; i < gamepadButtons.length; i++) {
			gamepadButtons[i] = new JoystickButton(gamepad, i);
		}
		
		joyLeftButtons = new Button[joyLeft.getButtonCount() + 1];
		for(int i = 1; i < joyLeftButtons.length; i++) {
			joyLeftButtons[i] = new JoystickButton(joyLeft, i);
		}
		
		joyRightButtons = new Button[joyRight.getButtonCount() + 1];
		for(int i = 1; i < joyRightButtons.length; i++) {
			joyRightButtons[i] = new JoystickButton(joyRight, i);
		}
		
		joyControlButtons = new Button[joyControl.getButtonCount() + 1];
		for(int i = 1; i < joyControlButtons.length; i++) {
			joyControlButtons[i] = new JoystickButton(joyControl, i);
		}
		
		xBoxButtons = new Button[xBox.getButtonCount() + 1];
		for(int i = 1; i < xBoxButtons.length; i++) {
			xBoxButtons[i] = new JoystickButton(xBox, i);
		}
		
		if(con.getConsole() == 0) {
			joyLeftButtons[3].whenPressed(new Shift(sh, Shift.State.SLOW));
			joyLeftButtons[4].whenPressed(new Shift(sh, Shift.State.FAST));
			
			joyLeftButtons[5].whenPressed(new TankDrive(dt, this, con));
			joyLeftButtons[6].whenPressed(new KajDrive(dt, this, con));
			
			joyLeftButtons[12].whenPressed(new TurnAngle(dt, sh, navx, 90, TurnAngle.EndAngleMeaning.RELATIVE));
			joyLeftButtons[11].whenPressed(new TurnAngle(dt, sh, navx, -90, TurnAngle.EndAngleMeaning.RELATIVE));
			
			joyLeftButtons[9].whenPressed(new DriveDistance(dt, sh, navx, 40.0));
			joyLeftButtons[10].whenPressed(new DriveDistance(dt, sh, navx, 100.0));
			
			joyLeftButtons[8].whenPressed(new TestGroup(dt, sh, navx));
			
			joyLeftButtons[1].whileHeld(new GrabCube(in));
			joyLeftButtons[2].whenPressed(new OpenArms(in));
			
			joyRightButtons[3].whileHeld(new EjectCube(in, this, con));
			joyRightButtons[5].whenPressed(new ExtendRetract(in));
			
			//lift manual = while held button 4 + y axis
			//lift ground = when pressed button 12
			//lift switch = when pressed button 10
			//lift scale = when pressed button 8
		}
		if(con.getConsole() == 1) {
			joyLeftButtons[3].whenPressed(new Shift(sh, Shift.State.SLOW));
			joyLeftButtons[4].whenPressed(new Shift(sh, Shift.State.FAST));
			
			joyControlButtons[1].whileHeld(new GrabCube(in));
			joyControlButtons[2].whenPressed(new OpenArms(in));
			
			joyControlButtons[3].whileHeld(new EjectCube(in, this, con));
			joyControlButtons[5].whenPressed(new ExtendRetract(in));
			
			//lift manual = while held button 4 + y axis
			//lift ground = when pressed button 12
			//lift switch = when pressed button 10
			//lift scale = when pressed button 8
			
		}
		if(con.getConsole() == 2) { 
			joyLeftButtons[5].whenPressed(new Shift(sh, Shift.State.SLOW));
			joyLeftButtons[6].whenPressed(new Shift(sh, Shift.State.FAST));
			
			joyControlButtons[1].whileHeld(new GrabCube(in));
			joyControlButtons[2].whenPressed(new OpenArms(in));
			
			joyControlButtons[3].whileHeld(new EjectCube(in, this, con));
			joyControlButtons[4].whenPressed(new ExtendRetract(in));
		}
		if(con.getConsole() == 3) { 
			joyLeftButtons[5].whenPressed(new Shift(sh, Shift.State.SLOW));
			joyLeftButtons[6].whenPressed(new Shift(sh, Shift.State.FAST));
			
			joyControlButtons[1].whileHeld(new GrabCube(in));
			joyControlButtons[2].whenPressed(new OpenArms(in));
			
			joyControlButtons[3].whileHeld(new EjectCube(in, this, con));
			joyControlButtons[4].whenPressed(new ExtendRetract(in));
		}
	}
}
