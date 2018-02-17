package org.team2399.robot.commands.intake;

import java.util.function.DoubleSupplier;

import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class EjectCube extends Command{

	Intake in;
	DriveTrain dt;
	DoubleSupplier speed;
	
	public EjectCube(Intake in, DriveTrain dt, DoubleSupplier speed) {
		this.in = in;
		this.dt = dt;
		this.speed = speed;
		requires(this.in);
		requires(this.dt);
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
		in.setSpeed(speed.getAsDouble());
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		in.setSpeed(0);
	}

}
