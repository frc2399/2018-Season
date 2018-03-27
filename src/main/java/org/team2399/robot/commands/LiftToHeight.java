package org.team2399.robot.commands;

import org.team2399.robot.Utility;
import org.team2399.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

public class LiftToHeight extends Command{

	private Lift li;
	private double height;
	
	private static final double HEIGHT_TOLERANCE = 1;
	
	public LiftToHeight(Lift li, double height) {
		this.li = li;
		this.height = height;
		requires(li);
	}
	
	@Override
	protected void initialize() {	
	}

	@Override
	protected void execute() {
		li.setVarHeight(height);
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
		//return Utility.inRange(height, li.getDesiredHeight(), HEIGHT_TOLERANCE)
	}

}
