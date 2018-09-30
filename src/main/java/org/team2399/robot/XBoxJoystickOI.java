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
import org.team2399.robot.commands.intake.CloseArms;
import org.team2399.robot.commands.intake.DeployIntake;
import org.team2399.robot.commands.intake.EjectCube;
import org.team2399.robot.commands.intake.ExtendRetract;
import org.team2399.robot.commands.intake.GrabCube;
import org.team2399.robot.commands.intake.OpenArms;
import org.team2399.robot.commands.intake.OpenCloseArms;
import org.team2399.robot.commands.intake.RetractIntake;
import org.team2399.robot.commands.intake.XBoxShoulders;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class XBoxJoystickOI extends OI {
	
	Joystick xBox, stick; 
	Button [] xBoxButtons, stickButtons;
	
	private Command defaultDrive;
	private Command defaultShift;
	private KajDrive kajDrive;

	public XBoxJoystickOI(Shifter sh, DriveTrain dt, Intake in, Lift li, AHRS navx) {
		
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
		
		XBoxShoulders leftS = new XBoxShoulders(leftShoulder);
		XBoxShoulders rightS = new XBoxShoulders(rightShoulder);
		
		
		//Pit testing
		//xBoxButtons[1].whenPressed(new DeployIntake(in));
		//xBoxButtons[2].whileHeld(new LiftToPercent(li, stickThrottle));
		//xBoxButtons[3].whenPressed(new TurnAngle(dt, sh, navx, 90, TurnAngle.EndAngleMeaning.RELATIVE));
		
		//Match testing
//		xBoxButtons[5].whenPressed(new Shift(sh, Shift.State.SLOW)); 
//		xBoxButtons[6].whenPressed(new Shift(sh, Shift.State.FAST)); 
	     
		xBoxButtons[7].whenPressed(tankDrive); 
		xBoxButtons[8].whenPressed(kajDrive);
		
		//5 + 6
		
		xBoxButtons[5].whileHeld(new GrabCube(in));
		xBoxButtons[6].whileHeld(new EjectCube(in));
		
		xBoxButtons[1].whenPressed(new DeployIntake(in));
		xBoxButtons[2].whenPressed(new RetractIntake(in));
		xBoxButtons[3].whenPressed(new OpenArms(in));
		xBoxButtons[4].whenPressed(new CloseArms(in));
		
		leftS.whenPressed(new OpenCloseArms(in));
		rightS.whenPressed(new ExtendRetract(in));
		
		// --------------------------------------------------------------------------------------------------------------------
		
		
		//stickButtons[11].whileHeld(new LiftToPercent(li, stickThrottle));
		
//		stickButtons[5].whenPressed(new Shift(sh, Shift.State.SLOW)); 
//		stickButtons[6].whenPressed(new Shift(sh, Shift.State.FAST));
		
		//if(stickButtons[6].get() && stickButtons[7].get()) {
		//	stickButtons[6].whileHeld(new LiftToPercent(li, stickThrottle));
		//} else {
		//	stickButtons[6].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.SWITCH_PORTAL));
		//	stickButtons[7].whenPressed(new LiftToHeight(li, 2));
		//}
		
		stickButtons[1].whileHeld(new ManualLift(li, stickY));
		
		stickButtons[6].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.SWITCH_PORTAL));
		stickButtons[7].whenPressed(new LiftToHeight(li, 2));
		stickButtons[8].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
		stickButtons[9].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MIN_SCALE));
		stickButtons[10].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MED_SCALE));
		stickButtons[11].whenPressed(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MAX_SCALE));

		
		// 9 = minimum scale
		// 10 = medium scale
		// 11 = maximum scale
		// 6 = switch/portal
		// 8 = ground
		// 7 = inch off the ground
		
	}
	
	public boolean deadmanActive() {
		return stickButtons[1].get();
		//return true;
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

