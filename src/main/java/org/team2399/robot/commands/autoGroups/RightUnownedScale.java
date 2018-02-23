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
import edu.wpi.first.wpilibj.command.WaitCommand;

public class RightUnownedScale extends CommandGroup {
	public RightUnownedScale(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		addSequential(new DriveDistance(dt, sh, navx, 230.0));
		addSequential(new TurnAngle(dt, sh, navx, RobotMap.Auto.LONG_LEFT_TURN, TurnAngle.EndAngleMeaning.RELATIVE));
		addSequential(new DriveDistance(dt, sh, navx, 190.0));
		addSequential(new WaitCommand(1));
		addSequential(new TurnAngle(dt, sh, navx, RobotMap.Auto.LONG_RIGHT_TURN, TurnAngle.EndAngleMeaning.RELATIVE));
		//addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MAX_SCALE));
		addSequential(new DriveDistance(dt, sh, navx, 25.0));
		addSequential(new EjectCube(in, RobotMap.EJECT_SPEED));		
	}
}