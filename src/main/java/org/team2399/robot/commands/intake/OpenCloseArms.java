package org.team2399.robot.commands.intake;

import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class OpenArms extends Command{

	Intake in;
	
	public OpenArms(Intake in) {
		this.in = in;
		requires(this.in);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		in.release();
		in.setSpeed(0);
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
