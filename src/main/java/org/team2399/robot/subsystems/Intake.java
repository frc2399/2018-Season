package org.team2399.robot.subsystems;

import org.team2399.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem{

	private TalonSRX leftTalon, rightTalon;
	private DoubleSolenoid extender, grabber;
	public boolean isExtended;
	
	//factor later
	public Intake() {
		leftTalon = new TalonSRX(10);
	//	rightTalon = new TalonSRX(0);
		
		extender = new DoubleSolenoid(RobotMap.CAN.PCM, RobotMap.PCM.EXTENDER_OUT, RobotMap.PCM.EXTENDER_IN);
		grabber = new DoubleSolenoid(RobotMap.CAN.PCM, RobotMap.PCM.GRABBER_OUT, RobotMap.PCM.GRABBER_IN);
		isExtended = false;
	}
	
	// forward is positive, backwards is negative, -1 to 1
	public void setSpeed(double speed) {
		leftTalon.set(ControlMode.PercentOutput, speed);
	//	rightTalon.set(ControlMode.PercentOutput, speed);
	}
	
	public void extend() {
		extender.set(DoubleSolenoid.Value.kForward);
		isExtended = true;
	}
	
	public void retract() {
		extender.set(DoubleSolenoid.Value.kReverse);
		isExtended = false;
	}
	
	public void grab() {
		grabber.set(DoubleSolenoid.Value.kForward);
	}
	
	public void release() {
		grabber.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void defaultCommand(Command c) {
	    	setDefaultCommand(c);
	}
	
	@Override
	protected void initDefaultCommand() {}
	
}
