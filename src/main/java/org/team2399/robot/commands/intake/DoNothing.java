package org.team2399.robot.commands.intake;
import org.team2399.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class DoNothing extends Command{

	private Intake in;

	public DoNothing(Intake in) {
		this.in = in;
		requires(in);
		setInterruptible(true);
	}
	
	@Override
	protected void execute() {
		in.setSpeed(0);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
