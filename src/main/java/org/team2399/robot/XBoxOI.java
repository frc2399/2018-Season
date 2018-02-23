package org.team2399.robot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.team2399.robot.commands.KajDrive;
import org.team2399.robot.commands.ManualLift;
import org.team2399.robot.commands.Shift;
import org.team2399.robot.commands.TankDrive;
import org.team2399.robot.commands.auto.DriveDistance;
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

public class XBoxOI extends OI {
	
	Joystick xBox; 
	Button [] xBoxButtons;
	
	private Command defaultDrive;
	private Command defaultShift;
	private KajDrive kajDrive;

	public XBoxOI(Shifter sh, DriveTrain dt, Intake in, Lift li, AHRS navx) {
		
		xBox = new Joystick(0);
		
		BooleanSupplier rightShoulder = thresholdDoubleSupplier(()->(xBox.getRawAxis(3)), 0.25);
		BooleanSupplier leftShoulder = thresholdDoubleSupplier(()->(xBox.getRawAxis(2)), 0.25);
		DoubleSupplier leftThrottle = ()->(throttleToPositiveRange(xBox.getRawAxis(1) * -1));
		DoubleSupplier rightX = ()->(xBox.getRawAxis(4));
		DoubleSupplier rightY = ()->(xBox.getRawAxis(5) * -1);
		DoubleSupplier leftY = ()->(xBox.getRawAxis(1) * -1);
		
		//kajDrive = new KajDrive(dt, leftY, rightX, leftShoulder, rightShoulder);
		TankDrive tankDrive = new TankDrive(dt, leftY, rightY);
		
		defaultShift = new Shift(sh, Shift.State.SLOW);
		defaultDrive = kajDrive;
		
		xBoxButtons = getButtons(xBox);
			     
		xBoxButtons[5].whenPressed(new Shift(sh, Shift.State.SLOW)); 
		xBoxButtons[6].whenPressed(new Shift(sh, Shift.State.FAST)); 
	     
		xBoxButtons[7].whenPressed(tankDrive); 
		xBoxButtons[8].whenPressed(kajDrive);
		
//		xBoxButtons[1].whileHeld(new GrabCube(in));
//		xBoxButtons[2].whileHeld(new EjectCube(in, dt, leftThrottle));
//		xBoxButtons[3].whenPressed(new OpenArms(in));
		//xBoxButtons[4].whenPressed(new ExtendRetract(in));
		
//		xBoxButtons[1].whenPressed(new DriveDistance(dt, sh, navx, 40.0));
//		xBoxButtons[2].whenPressed(new DriveDistance(dt, sh, navx, 100.0));
		xBoxButtons[1].whenPressed(new TurnAngle(dt, sh, navx, 90, TurnAngle.EndAngleMeaning.RELATIVE));
		xBoxButtons[2].whenPressed(new TurnAngle(dt, sh, navx, -90, TurnAngle.EndAngleMeaning.RELATIVE));
		xBoxButtons[3].whenPressed(new TurnAngle(dt, sh, navx, 180, TurnAngle.EndAngleMeaning.RELATIVE));
		
		xBoxButtons[4].whileHeld(new ManualLift(li, leftY));
		xBoxButtons[4].whileHeld(new TankDrive(dt, ()-> 0, ()-> 0));		
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
