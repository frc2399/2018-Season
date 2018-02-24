package org.team2399.robot.commands.autoGroups;

import org.team2399.robot.GamepadOI;
import org.team2399.robot.RobotMap;
import org.team2399.robot.commands.LiftToHeight;
import org.team2399.robot.commands.auto.DriveDistance;
import org.team2399.robot.commands.auto.TurnAngle;
import org.team2399.robot.commands.intake.EjectCube;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftOwnedSwitch extends CommandGroup {
	public LeftOwnedSwitch(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		addSequential(new DriveDistance(dt, sh, navx, RobotMap.Auto.BACK_WALL_TO_SWITCH));
//		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.SWITCH_PORTAL));
		addSequential(new TurnAngle(dt, sh, navx, RobotMap.Auto.SHORT_RIGHT_TURN, TurnAngle.EndAngleMeaning.RELATIVE));
		addSequential(new EjectCube(in), 1);		
	}
}
