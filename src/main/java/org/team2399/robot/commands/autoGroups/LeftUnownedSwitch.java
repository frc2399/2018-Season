package org.team2399.robot.commands.autoGroups;

import org.team2399.robot.RobotMap;
import org.team2399.robot.commands.LiftToHeight;
import org.team2399.robot.commands.auto.DriveDistance;
import org.team2399.robot.commands.auto.TurnAngle;
import org.team2399.robot.commands.intake.DeployIntake;
import org.team2399.robot.commands.intake.EjectCube;
import org.team2399.robot.commands.intake.SpinIn;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LeftUnownedSwitch extends CommandGroup {

	public LeftUnownedSwitch(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		addSequential(new DeployIntake(in));
		addSequential(new LiftToHeight(li, 2));
		addSequential(new DriveDistance(dt, sh, navx, RobotMap.Auto.SIDE_UNOWNED_SWITCH_FORWARD));
		addSequential(new TurnAngle(dt, sh, navx, RobotMap.Auto.LONG_RIGHT_TURN, TurnAngle.EndAngleMeaning.RELATIVE));
		addSequential(new DriveDistance(dt, sh, navx, RobotMap.Auto.SWITCH_THROUGH_PLATFORM_ZONE));
		addParallel(new SpinIn(in), 1);
		addSequential(new TurnAngle(dt, sh, navx, RobotMap.Auto.LONG_RIGHT_TURN, TurnAngle.EndAngleMeaning.RELATIVE));
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.SWITCH_PORTAL));
		addSequential(new DriveDistance(dt, sh, navx, 40));
		addSequential(new DeployIntake(in));
		addSequential(new EjectCube(in), 1);
		addSequential(new DriveDistance(dt, sh, navx, -30));
		addSequential(new LiftToHeight(li, RobotMap.FieldMeasurements.Heights.GROUND));
	}
}