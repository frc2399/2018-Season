package org.team2399.robot.commands;

import java.util.function.DoubleSupplier;

import org.team2399.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftToHeight extends Command{

	private Lift li;
	private double height;
	
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
		return false;
	}

}
