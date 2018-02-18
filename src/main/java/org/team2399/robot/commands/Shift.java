package org.team2399.robot.commands;

import org.team2399.robot.subsystems.Shifter;

import edu.wpi.first.wpilibj.command.Command;

public class Shift extends Command {

	Shifter shifter;
	State state;
	
	public enum State{SLOW, FAST;}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	public Shift(Shifter sh, State s) {
		shifter = sh;
		state = s;
		requires(shifter);
		setInterruptible(true);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if(state == State.FAST) {
			shifter.setShifterFast();
		} else if(state == State.SLOW){
			shifter.setShifterSlow();
		}
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		super.interrupted();
	}

	@Override
	public synchronized void cancel() {
		// TODO Auto-generated method stub
		super.cancel();
	}
	
	

}
