package org.team2399.robot.subsystems;

import org.team2399.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shifter extends Subsystem {

	private DoubleSolenoid solenoid;
	
	public Shifter() {
		solenoid = new DoubleSolenoid(RobotMap.PCM_ADDRESS, RobotMap.SHIFTER_SlOW_SOLENOID_PORT, RobotMap.SHIFTER_FAST_SOLENOID_PORT);
	}
	
	public void setShifterSlow() {
		solenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void setShifterFast() {
		solenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void defaultCommand(Command c) {
    	setDefaultCommand(c);
    }
	
	@Override
	protected void initDefaultCommand() {
	}

}
