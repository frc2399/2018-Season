package org.team2399.robot.commands;

import org.team2399.robot.OI;
import org.team2399.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class PIDTest extends Command {

	private DriveTrain dt;
	private OI oi;
	
	public PIDTest(DriveTrain dt, OI oi) {
		requires(dt);
		setInterruptible(true);
		
		this.dt = dt;
		this.oi = oi;
	}
	
	protected void initialize() {
	
	}
	
	protected void execute() {
		dt.driveVelocity(oi.getLeftStickY() * 500, oi.getLeftStickY() * 500);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
