package org.team2399.robot.commands.auto;

import org.team2399.robot.Console;
import org.team2399.robot.OI;
import org.team2399.robot.RobotMap;
import org.team2399.robot.commands.DriveDistance;
import org.team2399.robot.commands.TurnAngle;
import org.team2399.robot.commands.intake.EjectCube;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightOwnedSwitch extends CommandGroup {
	public RightOwnedSwitch(OI oi, DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in, Console con) {
		addSequential(new DriveDistance(dt, sh, navx, RobotMap.Auto.BACK_WALL_TO_SWITCH));
		addSequential(new TurnAngle(dt, sh, navx, RobotMap.Auto.SHORT_LEFT_TURN, TurnAngle.EndAngleMeaning.RELATIVE));
		//LIFT ELEVATOR TO ??
		addSequential(new EjectCube(in, oi, con));		
	}
}
