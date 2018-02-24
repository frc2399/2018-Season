package org.team2399.robot.commands.auto;

import org.team2399.robot.Utility;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnAngle extends Command {

	private static final double ANGLE_RATE_TOLERANCE = .1;
	private static final double ANGLE_TOLERANCE = 0.5;
	private static final double MAX_I_CONTRIB = 0.3;
	private static final double P_GAIN = 0.007;
	private static final double I_GAIN = 0.025;
	private static final double D_GAIN = 0.2;
	
	private double tempP;
	private double tempI;
	private double tempD;
	private double tempMaxIContrib;
	private static final int ANGULAR_RATE = 160;
	private Timer timer;
	private Shifter sh;
	private DriveTrain dt;
	private AHRS navx;
	private double startAngle;
	private double inputAngle;
	private double endAngle;
	private double endTime;
	private double angularVelocity;
	
	private double fuzz;
	
	private double prevError;
	private double errorSum;
	
	private EndAngleMeaning strategy;
	
	public TurnAngle(DriveTrain dt, Shifter sh, AHRS navx, double inputAngle, EndAngleMeaning strat) {
		timer = new Timer();
		this.dt = dt;
		this.sh = sh;
		this.navx = navx;
		this.inputAngle = inputAngle;
		strategy = strat;
		
//		setInterruptible(true);
		
		requires(dt);
		requires(sh);
		
		SmartDashboard.putNumber("pGain", P_GAIN);
		SmartDashboard.putNumber("iGain", I_GAIN);
		SmartDashboard.putNumber("dGain", D_GAIN);
		SmartDashboard.putNumber("maxIContrib", MAX_I_CONTRIB);
	}
	
	public enum EndAngleMeaning
	{
		/**
		 * go to exact angle, no extra math or logic
		 * @author rebec
		 *
		 */
		ABSOLUTE {
			@Override
			double endAngle(double startAngle, double inputAngle) {
				return inputAngle;
			}

			@Override
			public void test() {				
			}
		},
		/**
		 * angle relative to current angle
		 * @author rebec
		 *
		 */
		RELATIVE {
			@Override
			double endAngle(double startAngle, double inputAngle) {
				return startAngle + inputAngle;
			}

			@Override
			public void test() {
			}
		},
		/**
		 * always rotate less than 180 degrees to correct heading
		 * @author rebec
		 *
		 */
		LESS_THAN_180 {
			
			/**
			 * @param startAngle
			 * @param inputAngle is less than 360 and greater than 0
			 * @return endAngle
			 */
			@Override
			double endAngle(double startAngle, double inputAngle) {
				double relativeTurn = inputAngle - (startAngle % 360);
				
				double oppositeTurnError;
				if (relativeTurn < 0) {
					oppositeTurnError = relativeTurn + 360;
				} else {
					oppositeTurnError = relativeTurn - 360;
				}
				
				if (Math.abs(relativeTurn) < Math.abs(oppositeTurnError)) {
					return relativeTurn + startAngle;	
				} else {
					return oppositeTurnError + startAngle;
				}
			}
			
			public void test()
			{
				System.out.println(endAngle(360 * 4, 90) == 360 * 4 + 90);
				System.out.println(endAngle(360 * 4, -90) == 360 * 4 - 90);
				System.out.println(endAngle(360 * 4, -180) == 360 * 4 + 180);
				System.out.println(endAngle(360 * 4, -270) == 360 * 4 + 90);
				System.out.println(endAngle(360 * 3 + 20, 90) == 360 * 3 + 90);
			}
		};
		
		abstract double endAngle(double startAngle, double inputAngle);
		public abstract void test();
	}

	@Override
	protected boolean isFinished() {
		return timer.get() > endTime && Utility.inRange(navx.getAngle(), endAngle, ANGLE_TOLERANCE) && Utility.inRange(navx.getRate(), 0, ANGLE_RATE_TOLERANCE);
	}

	@Override
	protected void initialize() {
		dt.enableVoltageComp();
    	dt.brakeMode();
		
		timer.start();
		sh.setShifterFast();
		startAngle = navx.getAngle();
		
		endAngle = strategy.endAngle(startAngle, inputAngle);
		
		if (endAngle > startAngle) {
			angularVelocity = ANGULAR_RATE;
		} else {
			angularVelocity = ANGULAR_RATE * -1;
		} 
		
		fuzz = .001;
		
		prevError = 0;
		errorSum = 0;
		
		tempP = SmartDashboard.getNumber("pGain", P_GAIN);
		tempI = SmartDashboard.getNumber("iGain", I_GAIN);
		tempD = SmartDashboard.getNumber("dGain", D_GAIN);
		tempMaxIContrib = SmartDashboard.getNumber("maxIContrib", MAX_I_CONTRIB);
		
		endTime = (endAngle - startAngle) / angularVelocity;
		
	//super.initialize();
	}

	@Override
	protected void execute() {
		flipFuzz();
		
		double currentAngle = navx.getAngle();
		
		double time = timer.get();
		double endTime = (endAngle - startAngle) / angularVelocity;
		
		// what the angle should be at the current time
		double desiredAngle = angularVelocity * time + startAngle;
		
		if (time > endTime) {
			desiredAngle = endAngle;
		}
		
		double angleError = desiredAngle - currentAngle;
		
		double errorDifference = angleError - prevError;
		double pContrib = angleError * tempP;
		double iContrib = errorSum * tempI;
		if (iContrib > tempMaxIContrib) {
			iContrib = tempMaxIContrib;
			errorSum = iContrib / tempI;
		}
		if (iContrib < -tempMaxIContrib) {
			iContrib = -tempMaxIContrib;
			errorSum = iContrib / tempI;
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
