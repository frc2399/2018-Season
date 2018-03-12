package org.team2399.robot.commands;

import org.team2399.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

public class LiftHold extends Command{

	private Lift li;
	
	public LiftHold(Lift li) {
		this.li = li;
		requires(li);
	}
	
	@Override
	protected void initialize() {	
	}

	@Override
	protected void execute() {
		li.setVarHeight(li.getDesiredHeight());
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
