package org.team2399.robot.commands.autoGroups;

import org.team2399.robot.RobotMap;
import org.team2399.robot.commands.LiftToHeight;
import org.team2399.robot.commands.auto.DriveDistance;
import org.team2399.robot.commands.intake.DeployIntake;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftAuto extends CommandGroup {
	public LeftAuto(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {
		addSequential(new DeployIntake(in));
		addSequential(new LiftToHeight(li, 2));
		addSequential(new DriveDistance(dt, sh, navx, RobotMap.Auto.SIDE_CROSS_BASELINE));	
	}
}
