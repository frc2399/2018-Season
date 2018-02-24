package org.team2399.robot.commands;

import java.util.function.DoubleSupplier;

import org.team2399.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

public class LiftToPercent extends Command{

	private Lift li;
	private DoubleSupplier height;
	
	public LiftToPercent(Lift li, DoubleSupplier height) {
		this.li = li;
		this.height = height;
		requires(li);
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		li.setVarHeight(height.getAsDouble() * Lift.LIFT_MAX_HEIGHT_INCHES);
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
