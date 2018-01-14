package org.team2399.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	TalonSRX leftFrontTalon = new TalonSRX(8);
	TalonSRX leftMiddleTalon = new TalonSRX(7);
	TalonSRX leftBackTalon = new TalonSRX(3);
	
	TalonSRX rightFrontTalon = new TalonSRX(1);
	TalonSRX rightMiddleTalon = new TalonSRX(2);
	TalonSRX rightBackTalon = new TalonSRX(5);

    public void Drivetrain() {
    	
    }
    
    public void defaultCommand(Command c) {
    	setDefaultCommand(c);
    }
    
    public static void follow(TalonSRX follower, TalonSRX leader) {
//		follower.changeControlMode(TalonSRX.TalonControlMode.Follower);
//		follower.set(leader.getDeviceID());

    	follower.set(ControlMode.Follower, leader.getDeviceID());
	}
    
    public void tankDrive(double leftPercent, double rightPercent) {
		
		double leftSideSpeed = leftPercent * -1;
		double rightSideSpeed = rightPercent;
		
		leftFrontTalon.set(ControlMode.PercentOutput, leftSideSpeed);
		rightFrontTalon.set(ControlMode.PercentOutput, rightSideSpeed);
		
		follow(leftMiddleTalon, leftFrontTalon);
		follow(leftBackTalon, leftFrontTalon);
		follow(rightMiddleTalon, rightFrontTalon);
		follow(rightBackTalon, rightFrontTalon);
	}
    
public void kajDrive(double forward, double turn) {
		
		double leftSideSpeed = -1 * (forward + turn * (0.5 + 0.5 * Math.abs(forward)));
		double rightSideSpeed = (forward - turn * (0.5 + 0.5 * Math.abs(forward)));
		
		leftFrontTalon.set(ControlMode.PercentOutput, leftSideSpeed);
		rightFrontTalon.set(ControlMode.PercentOutput, rightSideSpeed);
		
		follow(leftMiddleTalon, leftFrontTalon);
		follow(leftBackTalon, leftFrontTalon);
		follow(rightMiddleTalon, rightFrontTalon);
		follow(rightBackTalon, rightFrontTalon);
	}

	@Override
	protected void initDefaultCommand() {
	}
}

