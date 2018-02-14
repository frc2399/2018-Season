package org.team2399.robot.commands;

import org.team2399.robot.Console;
import org.team2399.robot.OI;
import org.team2399.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class PIDTest extends Command {

	private DriveTrain dt;
	private OI oi;
	private Console con;
	
	public PIDTest(DriveTrain dt, OI oi, Console con) {
		requires(dt);
		setInterruptible(true);
		
		this.dt = dt;
		this.oi = oi;
	}
	
	protected void initialize() {
	
	}
	
	protected void execute() {
		dt.driveVelocity(con.tankPercentLeft * 500, con.tankPercentRight * 500);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
