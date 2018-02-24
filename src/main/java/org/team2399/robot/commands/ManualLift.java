package org.team2399.robot.commands;

import java.util.function.DoubleSupplier;

import org.team2399.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

public class ManualLift extends Command {

	Lift li;
	
	private DoubleSupplier height;
	
	public ManualLift(Lift li, DoubleSupplier height) {
		this.li = li;
		this.height = height;
		requires(li);
		
		setInterruptible(true);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		li.manualControl(height.getAsDouble());
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {

	}

}
