package org.team2399.robot.commands.intake;

import org.team2399.robot.GamepadOI;
import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class ExtendRetract extends Command{

	private Intake in;
	private boolean isExtended;
	
	public ExtendRetract(Intake in) {
		this.in = in;
		requires(this.in);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void initialize() {
		isExtended = in.isExtended;
	}

	@Override
	protected void execute() {
		if(isExtended) {
			in.retract();
		}
		if(!isExtended){
			in.extend();
		}
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
