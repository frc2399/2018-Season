package org.team2399.robot.commands.intake;

import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class GrabCube extends Command{

	Intake in;
	
	public GrabCube(Intake in) {
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
		in.setSpeed(-1);
		in.grab();
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		in.setSpeed(0);
	}

}
