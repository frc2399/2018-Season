package org.team2399.robot.commands.intake;

import java.util.function.DoubleSupplier;

import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class EjectCube extends Command{

	Intake in;
	double speed;
	
	public EjectCube(Intake in, double speed) {
		this.in = in;
		this.speed = speed;
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
		in.setSpeed(speed);
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		in.setSpeed(0);
	}

}
