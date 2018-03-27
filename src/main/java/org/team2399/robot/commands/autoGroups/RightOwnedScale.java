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

public class RightOwnedScale extends CommandGroup {
	public RightOwnedScale(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		addSequential(new DeployIntake(in));
		addSequential(new DriveDistance(dt, sh, navx, RobotMap.Auto.SIDE_OWNED_SCALE_FORWARD));
		addSequential(new TurnAngle(dt, sh, navx, RobotMap.Auto.SHORT_LEFT_TURN, TurnAngle.EndAngleMeaning.RELATIVE));
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.MAX_SCALE));
		addSequential(new DriveDistance(dt, sh, navx, 35));
		addSequential(new EjectCube(in), 1);
		addSequential(new DriveDistance(dt, sh, navx, -20));
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
	}
}
