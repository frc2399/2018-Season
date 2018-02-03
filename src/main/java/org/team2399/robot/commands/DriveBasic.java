package org.team2399.robot.commands;

import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBasic extends Command {

	private static final double MAX_VELOCITY = 100;
	private static final double MAX_ACCELERATION_TIME = 1.5;
	private static final double MAX_ACCELERATION_DISTANCE = MAX_VELOCITY * MAX_ACCELERATION_TIME / 2;
	private static final double SCALE = (175.0/168.0);
		
	private Timer timer;
	private Shifter sh;
	private DriveTrain dt;
	private AHRS navx;
	private boolean isFinished;
	
	private double coastDistance;
	private double accelerationDistance;
	private double totalDistance;
	
	private double startAngle;
	
	private double fuzz;

	
	public DriveBasic(DriveTrain dt, Shifter sh, AHRS navx, double dist) {
		timer = new Timer();
		this.dt = dt;
		this.sh = sh;
		this.navx = navx;
		
		requires(dt);
		requires(sh);
		isFinished = false;	
		
		totalDistance = dist * SCALE;
		
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
		dt.enableVoltageComp();
    	dt.brakeMode();
		
		timer.start();
		sh.setShifterFast();
		startAngle = navx.getAngle();
		isFinished = false;
		fuzz = .001;
	}

	@Override
	protected void execute() {
		flipFuzz();
		double time = timer.get();
		
		double accelerationTime = Math.sqrt(2 * accelerationDistance / (MAX_VELOCITY / MAX_ACCELERATION_TIME));
		double coastTime = coastDistance / MAX_VELOCITY;
		
		double beginDeceleration = accelerationTime + coastTime;
		double endTime = accelerationTime * 2 + coastTime;
		double velocity = 0;
		double relativeAngle = navx.getAngle() - startAngle;
		double velocityDifference = 0;
		
		double relativeAngleArr[] = {relativeAngle, fuzz};
		
		SmartDashboard.putNumberArray("relativeAngle", relativeAngleArr);
		
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
		
		
		double angleRate = navx.getRate();
		double angleRateArr[] = {angleRate, fuzz};
		
		SmartDashboard.putNumberArray("angleRate", angleRateArr);
		
		velocityDifference = relativeAngle * .75 * Math.abs(velocity) / (MAX_VELOCITY * .3);		
		dt.driveVelocity(velocity - velocityDifference, velocity + velocityDifference);
		
		
		/**
		 * if gyro is clockwise/right angle = positive
		 * 		put more power to right, less to left
		 * 
		 * if gryo is counter clockwise/left angle = negative
		 * 		put more power to left, less to right
		 */
		
		
	}

	@Override
	protected void end() {
		timer.stop();
	}

	@Override
	protected void interrupted() {
		timer.stop();
	}

	private void flipFuzz() {
		fuzz *= -1;
	}
	
}
