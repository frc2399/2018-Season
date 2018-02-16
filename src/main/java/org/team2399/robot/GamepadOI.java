/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team2399.robot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.team2399.robot.commands.KajDrive;
import org.team2399.robot.commands.Shift;
import org.team2399.robot.commands.TankDrive;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class GamepadOI extends OI {
	Joystick gamepad;
	
	Button[] gamepadButtons;
	
	private Command defaultDrive;
	private Command defaultShift;
	private KajDrive kajDrive;
	
	public GamepadOI(Shifter sh, DriveTrain dt, Intake in, AHRS navx) {
		
		gamepad = new Joystick(0);
		
		BooleanSupplier rightShoulder = ()->(gamepad.getRawButton(6));
		BooleanSupplier leftShoulder = ()->(gamepad.getRawButton(5));
		DoubleSupplier rightX = ()->(gamepad.getRawAxis(2));
		DoubleSupplier rightY = ()->(gamepad.getRawAxis(3) * -1);
		DoubleSupplier leftY = ()->(gamepad.getRawAxis(1) * -1);
		
		kajDrive = new KajDrive(dt, leftY, rightX, leftShoulder, rightShoulder);
		TankDrive tankDrive = new TankDrive(dt, leftY, rightY);
		
		defaultShift = new Shift(sh, Shift.State.SLOW);
		defaultDrive = kajDrive;
		
		gamepadButtons = getButtons(gamepad);
			     
	    gamepadButtons[7].whenPressed(new Shift(sh, Shift.State.SLOW)); 
	    gamepadButtons[8].whenPressed(new Shift(sh, Shift.State.FAST)); 
	     
	    gamepadButtons[9].whenPressed(tankDrive); 
	    gamepadButtons[10].whenPressed(kajDrive);
	}

	@Override
	public Command defaultDrive() {
		return defaultDrive;
	}

	@Override
	public Command defaultShift() {
		return defaultShift;
	}
}
