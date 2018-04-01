package org.team2399.robot.commands.intake;

import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class RetractIntake extends Command {

	private Intake in;
	
	public RetractIntake(Intake in) {
		this.in = in;
		requires(in);
	}

	
	@Override
	protected void execute() {
		in.retract();
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
