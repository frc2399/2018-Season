package org.team2399.robot.commands;

import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestGroup extends CommandGroup {
	public TestGroup(DriveTrain dt, Shifter sh, AHRS navx) {
		addSequential(new DriveDistance(dt, sh, navx, 40.0));
		addSequential(new TurnAngle(dt, sh, navx, 90, TurnAngle.EndAngleMeaning.RELATIVE));
		addSequential(new DriveDistance(dt, sh, navx, 10.0));
		addSequential(new TurnAngle(dt, sh, navx, -90, TurnAngle.EndAngleMeaning.RELATIVE));
	}
	
//	joyLeftButtons[12].whenPressed(new TurnAngle(dt, sh, navx, 90, TurnAngle.EndAngleMeaning.RELATIVE));
//	joyLeftButtons[11].whenPressed(new TurnAngle(dt, sh, navx, -90, TurnAngle.EndAngleMeaning.RELATIVE));
//	
//	joyLeftButtons[9].whenPressed(new DriveDistance(dt, sh, navx, 40.0));
//	joyLeftButtons[10].whenPressed(new DriveDistance(dt, sh, navx, 100.0));
	
}
