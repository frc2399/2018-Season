package org.team2399.robot.commands;

import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnAngle extends Command {

	private static final double P_GAIN = 0.02;
	private static final double I_GAIN = 0.001;
	private static final double D_GAIN = 0.1;

	
	private double tempP;
	private double tempI;
	private double tempD;
	// 243
	private static final int ANGULAR_RATE = 120;
	private Timer timer;
	private Shifter sh;
	private DriveTrain dt;
	private AHRS navx;
	private boolean isFinished;
	private double startAngle;
	private double endAngle;
	private double angularVelocity;
	
	private double fuzz;
	
	private double prevError;
	private double errorSum;
	
	public TurnAngle(DriveTrain dt, Shifter sh, AHRS navx, double endAngle) {
		timer = new Timer();
		this.dt = dt;
		this.sh = sh;
		this.navx = navx;
		this.endAngle = endAngle;
		
//		setInterruptible(true);
		
		requires(dt);
		requires(sh);
		isFinished = false;
		
		SmartDashboard.putNumber("pGain", P_GAIN);
		SmartDashboard.putNumber("iGain", I_GAIN);
		SmartDashboard.putNumber("dGain", D_GAIN);
	}

	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void initialize() {
		timer.start();
		sh.setShifterFast();
		startAngle = navx.getAngle();
		
		if (endAngle > startAngle) {
			angularVelocity = ANGULAR_RATE;
		} else {
			angularVelocity = ANGULAR_RATE * -1;
		} 
				
		isFinished = false;
		fuzz = .001;
		
		prevError = 0;
		errorSum = 0;
		
		tempP = SmartDashboard.getNumber("pGain", P_GAIN);
		tempI = SmartDashboard.getNumber("iGain", I_GAIN);
		tempD = SmartDashboard.getNumber("dGain", D_GAIN);
		
	//super.initialize();
	}

	@Override
	protected void execute() {
		flipFuzz();
		
		double currentAngle = navx.getAngle();
		double relativeAngle = currentAngle - startAngle;
		double relativeAngleArr[] = {relativeAngle, fuzz};
		double angleRate = navx.getRate();
		double angleRateArr[] = {angleRate, fuzz};
		
		double time = timer.get();
		double endTime = (endAngle - startAngle) / angularVelocity;
		
//		SmartDashboard.putNumberArray("relativeAngleTurn", relativeAngleArr);
//		SmartDashboard.putNumberArray("angleTurnRate", angleRateArr);
		
		double desiredAngle = angularVelocity * time + startAngle;
		
		if (time > endTime) {
			desiredAngle = endAngle;
//			isFinished = true;
		}
		
		double angleError = desiredAngle - currentAngle;
		
		double errorDifference = angleError - prevError;
		double pContrib = angleError * tempP;
		double iContrib = errorSum * tempI;
		if (iContrib > 0.25) {
			iContrib = 0.25;
		}
		if (iContrib < -0.25) {
			iContrib = -0.25;
		}
		double dContrib = errorDifference * tempD;
		double percent = pContrib + iContrib + dContrib;
		dt.drivePercent(percent, -percent);
		
		double angle[] = {desiredAngle, currentAngle, fuzz};
		SmartDashboard.putNumberArray("angle", angle);
		
		//velocityDifference = angleError * .75 * Math.abs(velocity) / (MAX_VELOCITY * .3);		
		//dt.driveVelocity(velocity - velocityDifference, velocity + velocityDifference);
		
		double percentArr[] = {pContrib, iContrib, dContrib, fuzz};
		SmartDashboard.putNumberArray("percent", percentArr);
		
		if( time > 10) {
			isFinished = true;
		}
		
		
		// must stay at end
		prevError = angleError;
		errorSum += angleError;
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
