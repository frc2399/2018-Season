package org.team2399.robot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.team2399.robot.commands.KajDrive;
import org.team2399.robot.commands.LiftToHeight;
import org.team2399.robot.commands.LiftToPercent;
import org.team2399.robot.commands.ManualLift;
import org.team2399.robot.commands.Shift;
import org.team2399.robot.commands.TankDrive;
import org.team2399.robot.commands.auto.TurnAngle;
import org.team2399.robot.commands.intake.EjectCube;
import org.team2399.robot.commands.intake.ExtendRetract;
import org.team2399.robot.commands.intake.GrabCube;
import org.team2399.robot.commands.intake.OpenCloseArms;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class XBoxJoystickOIMoreButtons extends OI {
	
	Joystick xBox, stick; 
	Button [] xBoxButtons, stickButtons;
	
	private Command defaultDrive;
	private Command defaultShift;
	private KajDrive kajDrive;

	public XBoxJoystickOIMoreButtons(Shifter sh, DriveTrain dt, Intake in, Lift li, AHRS navx) {
		
		xBox = new Joystick(0);
		stick = new Joystick(1);
		
		DoubleSupplier rightShoulder = ()->xBox.getRawAxis(3);
		DoubleSupplier leftShoulder = ()->(xBox.getRawAxis(2));
		DoubleSupplier rightX = ()->(xBox.getRawAxis(4));
		DoubleSupplier rightY = ()->(xBox.getRawAxis(5) * -1);
		DoubleSupplier leftY = ()->(xBox.getRawAxis(1) * -1);
		DoubleSupplier stickY = ()->(stick.getRawAxis(1) * -1);
		DoubleSupplier stickThrottle = ()->(throttleToPositiveRange(stick.getRawAxis(2) * -1));
		
		kajDrive = new KajDrive(dt, leftY, rightX, leftShoulder, rightShoulder);
		TankDrive tankDrive = new TankDrive(dt, leftY, rightY);
		
		defaultShift = new Shift(sh, Shift.State.SLOW);
		defaultDrive = kajDrive;
		
		xBoxButtons = getButtons(xBox);
		stickButtons = getButtons(stick);
		
		xBoxButtons[1].whenPressed(new ExtendRetract(in));
		xBoxButtons[2].whileHeld(new LiftToPercent(li, stickThrottle));
			     
		xBoxButtons[5].whenPressed(new Shift(sh, Shift.State.SLOW)); 
		xBoxButtons[6].whenPressed(new Shift(sh, Shift.State.FAST)); 
	     
		xBoxButtons[7].whenPressed(tankDrive); 
		xBoxButtons[8].whenPressed(kajDrive);
		
		// --------------------------------------------------------------------------------------------------------------------
		
		stickButtons[2].whenPressed(new OpenCloseArms(in));
		stickButtons[3].whileHeld(new GrabCube(in));
		stickButtons[4].whileHeld(new EjectCube(in));
		
		stickButtons[1].whileHeld(new ManualLift(li, stickY));
		stickButtons[5].whenPressed(new ExtendRetract(in));
		
		stickButtons[12].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MIN_SCALE));
		stickButtons[10].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MED_SCALE));
		stickButtons[8].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MAX_SCALE));
		stickButtons[7].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.SWITCH_PORTAL));
		stickButtons[11].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
		stickButtons[9].whenPressed(new LiftToHeight(li, 2));
		
		// 12 = minimum scale
		// 10 = medium scale
		// 8 = maximum scale
		// 7 = switch/portal
		// 9 = inch off the ground
		// 11 = ground
		
	}
	
	public static BooleanSupplier thresholdDoubleSupplier(DoubleSupplier d, double threshold) {
		
		return () -> d.getAsDouble() > threshold;
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
