package org.team2399.robot.commands.intake;

import java.util.function.DoubleSupplier;

import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class EjectCube extends Command{

	Intake in;
	DoubleSupplier speed;
	
	public EjectCube(Intake in, DoubleSupplier speed) {
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
		in.setSpeed(speed.getAsDouble());
	}

	@Override
	protected void end() {
		in.setSpeed(0);
	}

	@Override
	protected void interrupted() {
		in.setSpeed(0);
	}

}
