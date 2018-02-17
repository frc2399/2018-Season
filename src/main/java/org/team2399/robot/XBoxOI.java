package org.team2399.robot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.team2399.robot.commands.KajDrive;
import org.team2399.robot.commands.Shift;
import org.team2399.robot.commands.TankDrive;
import org.team2399.robot.commands.TurnAngle;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class XBoxOI extends OI {
	
	Joystick XBox; 
	Button [] xBoxButtons;
	
	private Command defaultDrive;
	private Command defaultShift;
	private KajDrive kajDrive;

	public XBoxOI(Shifter sh, DriveTrain dt, Intake in, AHRS navx) {
		
		XBox = new Joystick(0);
		
		BooleanSupplier rightShoulder = thresholdDoubleSupplier(()->(XBox.getRawAxis(3)), 0.25);
		BooleanSupplier leftShoulder = thresholdDoubleSupplier(()->(XBox.getRawAxis(2)), 0.25);
		DoubleSupplier rightX = ()->(XBox.getRawAxis(4));
		DoubleSupplier rightY = ()->(XBox.getRawAxis(5) * -1);
		DoubleSupplier leftY = ()->(XBox.getRawAxis(1) * -1);
		
		kajDrive = new KajDrive(dt, leftY, rightX, leftShoulder, rightShoulder);
		TankDrive tankDrive = new TankDrive(dt, leftY, rightY);
		
		defaultShift = new Shift(sh, Shift.State.SLOW);
		defaultDrive = kajDrive;
		
		xBoxButtons = getButtons(XBox);
			     
		xBoxButtons[5].whenPressed(new Shift(sh, Shift.State.SLOW)); 
		xBoxButtons[6].whenPressed(new Shift(sh, Shift.State.FAST)); 
	     
		xBoxButtons[7].whenPressed(tankDrive); 
		xBoxButtons[8].whenPressed(kajDrive);
		xBoxButtons[1].whenPressed(new TurnAngle(dt, sh, navx, 90, TurnAngle.EndAngleMeaning.RELATIVE));
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
