package org.team2399.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {

	private IMotorControllerEnhanced leftTalon, rightTalon;
	private double height;

	private static final double LIFT_KD = 0;
	private static final double LIFT_KI = 0;
	private static final double LIFT_KP = 0;
	private static final double LIFT_KF = 0.33;
	
	public Lift() {
		height = 0;
		
		//initialize talons
		//leftTalon = new TalonSRX();
		///rightTalon = new TalonSRX(
		
		//initialize sensors
		//leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, CAN_TIMEOUT);
    	//rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, CAN_TIMEOUT);
		//leftTalon.setSensorPhase(false);
		//rightTalon.setSensorPhase(false);
	}
	
	 @Override
	public void periodic() {
		setHeight();
	}
	 
	private void setHeight() {
		 //run PID loop on height (AKA setpoint)
	}
	
	public void setVarHeight(double height) {
		this.height = height;
	}

	public void defaultCommand(Command c) {
	    	setDefaultCommand(c);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	 
}
