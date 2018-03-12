package org.team2399.robot.commands.auto;

import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class DeployIntake extends Command {

	private Intake in;
	
	public DeployIntake(Intake in) {
		this.in = in;
		requires(in);
	}

	
	@Override
	protected void execute() {
		in.extend();
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
