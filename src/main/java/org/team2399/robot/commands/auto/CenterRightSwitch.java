package org.team2399.robot.commands.auto;

import org.team2399.robot.GamepadOI;
import org.team2399.robot.RobotMap;
import org.team2399.robot.commands.DriveDistance;
import org.team2399.robot.commands.LiftToHeight;
import org.team2399.robot.commands.TurnAngle;
import org.team2399.robot.commands.intake.EjectCube;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterRightSwitch extends CommandGroup {
	public CenterRightSwitch(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		addSequential(new DriveDistance(dt, sh, navx, RobotMap.Auto.CENTER_FORWARD));
		addSequential(new TurnAngle(dt, sh, navx, RobotMap.Auto.SHORT_RIGHT_TURN, TurnAngle.EndAngleMeaning.RELATIVE));
		addSequential(new DriveDistance(dt, sh, navx, RobotMap.Auto.CENTER_AUTO));
		addSequential(new TurnAngle(dt, sh, navx, RobotMap.Auto.LONG_LEFT_TURN, TurnAngle.EndAngleMeaning.RELATIVE));
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.SWITCH_PORTAL));
		addSequential(new EjectCube(in, RobotMap.EJECT_SPEED));		
	}
}
