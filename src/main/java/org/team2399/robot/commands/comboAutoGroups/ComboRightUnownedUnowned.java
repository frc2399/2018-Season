package org.team2399.robot.commands.comboAutoGroups;

import org.team2399.robot.RobotMap;
import org.team2399.robot.commands.LiftToHeight;
import org.team2399.robot.commands.auto.DriveDistance;
import org.team2399.robot.commands.auto.TurnAngle;
import org.team2399.robot.commands.intake.CloseArms;
import org.team2399.robot.commands.intake.GrabCube;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ComboRightUnownedUnowned extends CommandGroup{

	public ComboRightUnownedUnowned(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		// drive forward 220
		addSequential(new DriveDistance(dt, sh, navx, 220));
		// rotate left 90
		addSequential(new TurnAngle(dt, sh, navx, -90, TurnAngle.EndAngleMeaning.RELATIVE));
		// drive forward 205
		addSequential(new DriveDistance(dt, sh, navx, 205));
		// rotate right 100
		addSequential(new TurnAngle(dt, sh, navx, 100, TurnAngle.EndAngleMeaning.RELATIVE));
		// drive forward 60 & lift intake scale
		addParallel(new DriveDistance(dt, sh, navx, 50));
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MAX_SCALE));
		// drive backwards 35
		addSequential(new DriveDistance(dt, sh, navx, -25));
		// lift intake ground
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
		// rotate right 150
		addSequential(new TurnAngle(dt, sh, navx, 150, TurnAngle.EndAngleMeaning.RELATIVE));
		// drive forward 55 & intake cube
		addParallel(new DriveDistance(dt, sh, navx, 55));
		addSequential(new GrabCube(in), 3);
		// close intake
		addSequential(new CloseArms(in));
		// drive backwards 25
		addSequential(new DriveDistance(dt, sh, navx, -25));
		// lift intake switch
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.SWITCH_PORTAL));
		// drive forwards 25
		addSequential(new DriveDistance(dt, sh, navx, 25));
		// drive backwards 25
		addSequential(new DriveDistance(dt, sh, navx, -25));
		// lift intake ground
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
	}

}
