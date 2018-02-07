package org.team2399.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {

	public Lift() {
		
	}
	
	 @Override
	public void periodic() {
		// TODO Auto-generated method stub
		super.periodic();
	}
	 
	 public void setHeight(double height) {
		 
	 }

	public void defaultCommand(Command c) {
	    	setDefaultCommand(c);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	 
}
