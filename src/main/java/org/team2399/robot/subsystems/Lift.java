package org.team2399.robot.subsystems;

import org.team2399.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {

	private IMotorControllerEnhanced talon;
	private IMotorController victor;
	private double desiredHeight;
	private boolean isManual;

	//change can timeout
	private static final int CAN_TIMEOUT = 10;
	private static final double LIFT_KD = 0;
	private static final double LIFT_KI = 0;
	private static final double LIFT_KP = 0;
	private static final double LIFT_KF = 0.33;
	
	public Lift() {
		desiredHeight = 0;
		isManual = true;
		
		//set talon IDs
		talon = new TalonSRX(23);
		victor = new VictorSPX(24);
		
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, CAN_TIMEOUT);
		talon.setSensorPhase(false);
		
		victor.follow(talon);
		victor.setInverted(true);
	}
	
	 @Override
	public void periodic() {
		setHeight();
	}
	 
	private void setHeight() {
		 //run PID loop on height (AKA setpoint)
	}
	
	public void setVarHeight(double desiredHeight) {
		this.desiredHeight = desiredHeight;
		isManual = false;
	}
	
	public void manualControl(double percent) {
		isManual = true;
		talon.set(ControlMode.PercentOutput, percent * RobotMap.Physical.Lift.LIFT_UP);
	}
	
	public boolean isManual() {
		return isManual;
	}

	public void defaultCommand(Command c) {
	    	setDefaultCommand(c);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	 
}
