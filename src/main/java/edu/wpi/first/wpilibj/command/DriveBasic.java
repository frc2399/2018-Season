package edu.wpi.first.wpilibj.command;

import org.team2399.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;

public class DriveBasic extends Command {

	private static final int MAX_VELOCITY = 75;
	private Timer timer;
	private DriveTrain dt;
	private boolean isFinished;
	
	public DriveBasic(DriveTrain dt) {
		timer = new Timer();
		this.dt = dt;
		requires(dt);
		isFinished = false;
	}
	
	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void initialize() {
		timer.start();
		isFinished = false;
	}

	@Override
	protected void execute() {
		double time = timer.get();
		if (time < 1.5) {
			dt.driveVelocity(time * MAX_VELOCITY / 1.5, time * MAX_VELOCITY / 1.5);
		} else if (time < 2.5) {
			dt.driveVelocity(MAX_VELOCITY, MAX_VELOCITY);
		} else if (time < 4) {
			dt.driveVelocity((4 - time) * MAX_VELOCITY / 1.5, (4 - time) * MAX_VELOCITY / 1.5);
		} else if (time < 6){
			dt.driveVelocity(0, 0);
		} else {
			dt.driveVelocity(0, 0);
			isFinished = true;
		}
	}

	@Override
	protected void end() {
		timer.stop();
	}

	@Override
	protected void interrupted() {
		timer.stop();
	}

}
