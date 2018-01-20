package org.team2399.robot.subsystems;

import org.team2399.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shifter extends Subsystem {

	private DoubleSolenoid solenoid;
	
	public Shifter() {
		solenoid = new DoubleSolenoid(RobotMap.CAN.PCM, RobotMap.PCM.SHIFTER_SlOW, RobotMap.PCM.SHIFTER_FAST);
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
