package org.team2399.robot.commands.intake;

import org.team2399.robot.Console;
import org.team2399.robot.OI;
import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class EjectCube extends Command{

	Intake in;
	OI oi;
	Console con;
	
	private double speed;
	
	public EjectCube(Intake in, OI oi, Console con) {
		this.in = in;
		this.oi = oi;
		this.con = con;
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
		
		speed = con.intakeSpeed;
		
		if(speed < 0.0) {
			speed = 0.0;
		}
		
		in.setSpeed(speed);
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
