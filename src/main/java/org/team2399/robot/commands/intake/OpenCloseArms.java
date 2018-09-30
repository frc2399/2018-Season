package org.team2399.robot.commands.intake;

import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class OpenCloseArms extends Command{

	private Intake in;
	private boolean isOpen;
	
	public OpenCloseArms(Intake in) {
		this.in = in;
		requires(this.in);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void initialize() {
		isOpen = in.isOpen;
	}

	@Override
	protected void execute() {
		if(isOpen) {
			in.grab();
			in.setSpeed(0);
		}
		if(!isOpen) {
			in.release();
			in.setSpeed(0);
		}
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
