package org.team2399.robot.commands.intake;

import java.util.function.DoubleSupplier;

import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class EjectCube extends Command{

	Intake in;
	
	public EjectCube(Intake in) {
		this.in = in;
		requires(this.in);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		in.setSpeed(1);
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		in.setSpeed(0);
	}

}
