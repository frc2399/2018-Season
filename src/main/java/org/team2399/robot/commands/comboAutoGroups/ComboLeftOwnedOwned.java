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

public class ComboLeftOwnedOwned  extends CommandGroup{
	public ComboLeftOwnedOwned(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		// drive forward 220
		addSequential(new DriveDistance(dt, sh, navx, 220));
		// rotate right 135
		addSequential(new TurnAngle(dt, sh, navx, 135, TurnAngle.EndAngleMeaning.RELATIVE));
		// lift intake switch
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.SWITCH_PORTAL));
		// drive forward 40
		addSequential(new DriveDistance(dt, sh, navx, 40));
		// eject cube
		addSequential(new EjectCube(in), 1);
		// drive backwards 30
		addSequential(new DriveDistance(dt, sh, navx, -30));
		// lift intake ground
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.SWITCH_PORTAL));
		// drive forward 30 & intake
		addParallel(new DriveDistance(dt, sh, navx, 30));
		addSequential(new GrabCube(in), 3);
		// close intake
		addSequential(new CloseArms(in));
		// drive backwards 30
		addSequential(new DriveDistance(dt, sh, navx, -30));
		// rotate left 45 + 80
		addSequential(new TurnAngle(dt, sh, navx, (-40 - 85), TurnAngle.EndAngleMeaning.RELATIVE));
		// drive forwards 60 & lift intake max scale
		addParallel(new DriveDistance(dt, sh, navx, 60));
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MAX_SCALE));
		// eject cube
		addSequential(new EjectCube(in), 1);
		// drive backwards 25
		addSequential(new DriveDistance(dt, sh, navx, -25));
		// lift intake ground
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
	}

}
