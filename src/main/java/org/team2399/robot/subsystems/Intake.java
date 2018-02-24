package org.team2399.robot.subsystems;

import org.team2399.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem{

	private IMotorController left, right;
	private DoubleSolenoid extender, grabber;
	public boolean isExtended, isOpen;
	
	private static final int CAN_TIMEOUT = 10;
	private static final double CLOSED_LOOP_VOLTAGE_SATURATION = 10;
	
	//factor later
	public Intake() {
		left = new VictorSPX(RobotMap.Physical.Intake.LEFT_ID);
		right = new VictorSPX(RobotMap.Physical.Intake.RIGHT_ID);
		
		right.setInverted(true);
		right.follow(left);
		
		extender = new DoubleSolenoid(RobotMap.CAN.PCM, RobotMap.PCM.EXTENDER_OUT, RobotMap.PCM.EXTENDER_IN);
		grabber = new DoubleSolenoid(RobotMap.CAN.PCM, RobotMap.PCM.GRABBER_OUT, RobotMap.PCM.GRABBER_IN);
		
		left.setNeutralMode(NeutralMode.Brake);
		right.setNeutralMode(NeutralMode.Brake);
		
		left.configVoltageCompSaturation(CLOSED_LOOP_VOLTAGE_SATURATION, CAN_TIMEOUT);
		left.enableVoltageCompensation(true);
		right.configVoltageCompSaturation(CLOSED_LOOP_VOLTAGE_SATURATION, CAN_TIMEOUT);
		right.enableVoltageCompensation(true);
		
		isExtended = false;
		isOpen = false;
	}
	
	// forward is positive, backwards is negative, -1 to 1
	public void setSpeed(double speed) {
		left.set(ControlMode.PercentOutput, speed);
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
		isOpen = true;
	}
	
	public void release() {
		grabber.set(DoubleSolenoid.Value.kReverse);
		isOpen = false;
	}
	
	public void defaultCommand(Command c) {
	    	setDefaultCommand(c);
	}
	
	@Override
	protected void initDefaultCommand() {}
	
}
