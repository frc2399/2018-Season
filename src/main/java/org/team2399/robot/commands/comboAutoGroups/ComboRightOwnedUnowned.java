package org.team2399.robot.commands.comboAutoGroups;

import org.team2399.robot.RobotMap;
import org.team2399.robot.commands.LiftToHeight;
import org.team2399.robot.commands.auto.DriveDistance;
import org.team2399.robot.commands.auto.TurnAngle;
import org.team2399.robot.commands.intake.CloseArms;
import org.team2399.robot.commands.intake.EjectCube;
import org.team2399.robot.commands.intake.GrabCube;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ComboRightOwnedUnowned extends CommandGroup{

	public ComboRightOwnedUnowned(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		// drive forward 220
		addSequential(new DriveDistance(dt, sh, navx, 220));
		// rotate left 135
		addSequential(new TurnAngle(dt, sh, navx, -135, TurnAngle.EndAngleMeaning.RELATIVE));
		// lift intake switch
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.SWITCH_PORTAL));
		// drive forward 40
		addSequential(new DriveDistance(dt, sh, navx, 40));
		// eject cube
		addSequential(new EjectCube(in), 1);
		// drive backwards 30
		addSequential(new DriveDistance(dt, sh, navx, -30));
		// lift intake ground
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
		// drive forward 30 & intake cube 
		addParallel(new DriveDistance(dt, sh, navx, 30));
		addSequential(new GrabCube(in), 3);
		// close intake
		addSequential(new CloseArms(in));
		// drive backwards 40
		addSequential(new DriveDistance(dt, sh, navx, -40));
		// rotate right 45
		addSequential(new TurnAngle(dt, sh, navx, 45, TurnAngle.EndAngleMeaning.RELATIVE));
		// drive forward 205
		addSequential(new DriveDistance(dt, sh, navx, 205));
		// rotate right 100
		addSequential(new TurnAngle(dt, sh, navx, 100, TurnAngle.EndAngleMeaning.RELATIVE));
		// lift intake max scale
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MAX_SCALE));
		// drive forward 60
		addSequential(new DriveDistance(dt, sh, navx, 60));
		// eject cube
		addSequential(new EjectCube(in), 1);
		// drive backwards 25
		addSequential(new DriveDistance(dt, sh, navx, -25));
		// lift intake ground
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
	}

}
