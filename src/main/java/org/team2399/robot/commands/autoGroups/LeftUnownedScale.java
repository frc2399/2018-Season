package org.team2399.robot.commands.autoGroups;

import org.team2399.robot.RobotMap;
import org.team2399.robot.commands.LiftToHeight;
import org.team2399.robot.commands.auto.DeployIntake;
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

public class LeftUnownedScale extends CommandGroup {
	public LeftUnownedScale(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		addSequential(new DeployIntake(in));
		addSequential(new DriveDistance(dt, sh, navx, 220));
		addSequential(new TurnAngle(dt, sh, navx, RobotMap.Auto.LONG_RIGHT_TURN, TurnAngle.EndAngleMeaning.RELATIVE));
		addSequential(new DriveDistance(dt, sh, navx, RobotMap.Auto.SCALE_THROUGH_PLATFORM_ZONE));
		addSequential(new TurnAngle(dt, sh, navx, -100, TurnAngle.EndAngleMeaning.RELATIVE));
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MAX_SCALE));
		addSequential(new DriveDistance(dt, sh, navx, 65));
		addSequential(new EjectCube(in), 1);	
		addSequential(new DriveDistance(dt, sh, navx, -20));
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
	}

}
