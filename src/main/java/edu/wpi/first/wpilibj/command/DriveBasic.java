package edu.wpi.first.wpilibj.command;

import org.team2399.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;

public class DriveBasic extends Command {

	private static final double MAX_VELOCITY = 100;
	private static final double MAX_ACCELERATION_TIME = 1.5;
	private static final double MAX_ACCELERATION_DISTANCE = MAX_VELOCITY * MAX_ACCELERATION_TIME / 2;
		
	private Timer timer;
	private DriveTrain dt;
	private boolean isFinished;
	
	private double coastDistance;
	private double accelerationDistance;
	private double totalDistance;

	
	public DriveBasic(DriveTrain dt, double dist) {
		timer = new Timer();
		this.dt = dt;
		requires(dt);
		isFinished = false;	
		
		totalDistance = dist;
		
		if(totalDistance > MAX_ACCELERATION_DISTANCE * 2) {
			accelerationDistance = MAX_ACCELERATION_DISTANCE; 
			coastDistance = totalDistance - MAX_ACCELERATION_DISTANCE * 2;
		} else {
			accelerationDistance = totalDistance / 2;
			coastDistance = 0;
		}

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
		
		double accelerationTime = Math.sqrt(2 * accelerationDistance / (MAX_VELOCITY / MAX_ACCELERATION_TIME));
		double coastTime = coastDistance / MAX_VELOCITY;
		
		double beginDeceleration = accelerationTime + coastTime;
		double endTime = accelerationTime * 2 + coastTime;
		double velocity = 0;
		
		if (time < accelerationTime) {
			velocity = time * MAX_VELOCITY / MAX_ACCELERATION_TIME;
		} else if (time < beginDeceleration) {
			velocity = MAX_VELOCITY;
		} else if (time < endTime) {
			velocity = (endTime - time) * MAX_VELOCITY / MAX_ACCELERATION_TIME;
		} else if (time < endTime + 1) {
			velocity = 0;
		} else {
			velocity = 0;
			isFinished = true;
		}
		
		dt.driveVelocity(velocity, velocity);
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
