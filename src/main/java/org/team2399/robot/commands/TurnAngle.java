package org.team2399.robot.commands;

import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnAngle extends Command {

	private Timer timer;
	private Shifter sh;
	private DriveTrain dt;
	private AHRS navx;
	private boolean isFinished;
	private double startAngle;
	private double endAngle;
	
	private double fuzz;
	
	public TurnAngle(DriveTrain dt, Shifter sh, AHRS navx, double endAngle) {
		timer = new Timer();
		this.dt = dt;
		this.sh = sh;
		this.navx = navx;
		this.endAngle = endAngle;
		
		requires(dt);
		requires(sh);
		isFinished = false;
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
		isFinished = false;
		fuzz = .001;
	//super.initialize();
	}

	@Override
	protected void execute() {
		flipFuzz();
		
		double relativeAngle = navx.getAngle() - startAngle;
		double relativeAngleArr[] = {relativeAngle, fuzz};
		double angleRate = navx.getRate();
		double angleRateArr[] = {angleRate, fuzz};
		
		double time = timer.get();
		double endTime = Math.sqrt()
		
		SmartDashboard.putNumberArray("relativeAngleTurn", relativeAngleArr);
		SmartDashboard.putNumberArray("angleTurnRate", angleRateArr);
	}

	@Override
	protected void interrupted() {
		timer.stop();
	}
	
	private void flipFuzz() {
		fuzz *= -1;
	}

}
