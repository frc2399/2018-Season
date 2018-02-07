package org.team2399.robot.commands.intake;

import org.team2399.robot.OI;
import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class EjectCube extends Command{

	Intake in;
	OI oi;
	
	public EjectCube(Intake in, OI oi) {
		this.in = in;
		this.oi = oi;
		requires(this.in);
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
		in.setSpeed(oi.getRightThrottle());
	}

	@Override
	protected void end() {
		in.setSpeed(0);
	}

	@Override
	protected void interrupted() {
		in.setSpeed(0);
	}

}
