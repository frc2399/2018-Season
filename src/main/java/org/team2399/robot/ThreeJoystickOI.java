package org.team2399.robot;

import java.util.function.DoubleSupplier;

import org.team2399.robot.commands.DriveDistance;
import org.team2399.robot.commands.KajDrive;
import org.team2399.robot.commands.Shift;
import org.team2399.robot.commands.TankDrive;
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
import edu.wpi.first.wpilibj.command.Command;

public class ThreeJoystickOI extends OI {

	Joystick leftJoy, rightJoy, controlJoy;
	Button[] leftJoyButtons, rightJoyButtons, controlJoyButtons;
	
	final int THROTTLEAXIS = 1;
	
	private Command defaultDrive;
	private Command defaultShift;
	private KajDrive kajDrive;
	
	
	public ThreeJoystickOI(Shifter sh, DriveTrain dt, Intake in, AHRS navx) {
		leftJoy = new Joystick(0);
		rightJoy = new Joystick(1);
		controlJoy = new Joystick(2);
		
		DoubleSupplier controlThrottle = ()->(throttleToPositiveRange(controlJoy.getRawAxis(THROTTLEAXIS) * -1));
		DoubleSupplier rightX = ()->(rightJoy.getRawAxis(0));
		DoubleSupplier rightY = ()->(rightJoy.getRawAxis(1) * -1);
		DoubleSupplier leftY = ()->(leftJoy.getRawAxis(1) * -1); 
		
		kajDrive = new KajDrive(dt, leftY, rightX, ()->false, ()->false);
		TankDrive tankDrive = new TankDrive(dt, leftY, rightY);
		
		leftJoyButtons = getButtons(leftJoy);
		rightJoyButtons = getButtons(rightJoy);
		controlJoyButtons = getButtons(controlJoy);
		
		defaultShift = new Shift(sh, Shift.State.SLOW);
		defaultDrive = kajDrive;
		
		leftJoyButtons[3].whenPressed(new Shift(sh, Shift.State.SLOW));
		leftJoyButtons[4].whenPressed(new Shift(sh, Shift.State.FAST));
		
		leftJoyButtons[5].whenPressed(tankDrive);
		leftJoyButtons[6].whenPressed(kajDrive);
		
		leftJoyButtons[12].whenPressed(new TurnAngle(dt, sh, navx, 90, TurnAngle.EndAngleMeaning.RELATIVE));
		leftJoyButtons[11].whenPressed(new TurnAngle(dt, sh, navx, -90, TurnAngle.EndAngleMeaning.RELATIVE));
		
		leftJoyButtons[9].whenPressed(new DriveDistance(dt, sh, navx, 40.0));
		leftJoyButtons[10].whenPressed(new DriveDistance(dt, sh, navx, 100.0));
		
		controlJoyButtons[1].whileHeld(new GrabCube(in));
		controlJoyButtons[2].whenPressed(new OpenArms(in));
		
		controlJoyButtons[3].whileHeld(new EjectCube(in, dt, controlThrottle));
		controlJoyButtons[4].whenPressed(new ExtendRetract(in));
		
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
