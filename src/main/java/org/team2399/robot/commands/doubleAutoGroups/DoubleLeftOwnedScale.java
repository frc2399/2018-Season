package org.team2399.robot.commands.doubleAutoGroups;

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

public class DoubleLeftOwnedScale extends CommandGroup {
	public DoubleLeftOwnedScale(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		// drive forward 260
		addSequential(new DriveDistance(dt, sh, navx, 260));
		// lift intake max scale
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MAX_SCALE));
		// rotate right 45
		addSequential(new TurnAngle(dt, sh, navx, 45, TurnAngle.EndAngleMeaning.RELATIVE));
		// drive forward 15
		addSequential(new DriveDistance(dt, sh, navx, 15));
		// eject cube
		addSequential(new EjectCube(in), 1);
		// drive backwards 15
		addSequential(new DriveDistance(dt, sh, navx, -15));
		// lift intake ground
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
		// rotate right 45 + 60
		addSequential(new TurnAngle(dt, sh, navx, (45 + 60), TurnAngle.EndAngleMeaning.RELATIVE));
		// drive forward 50 + 20 & intake cube
		addParallel(new DriveDistance(dt, sh, navx, 70));
		addSequential(new GrabCube(in), 3);
		// close intake
		addSequential(new CloseArms(in));
		// drive backwards 25
		addSequential(new DriveDistance(dt, sh, navx, -25));
		// rotate left to 0
		addSequential(new TurnAngle(dt, sh, navx, -150, TurnAngle.EndAngleMeaning.RELATIVE));
		// lift intake max scale
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MAX_SCALE));
		// drive forward 40 + 15
		addSequential(new DriveDistance(dt, sh, navx, 55));
		// eject cube
		addSequential(new EjectCube(in), 1);
		// drive backwards 25
		addSequential(new DriveDistance(dt, sh, navx, -25));
		// lift intake ground
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
	}	
}
