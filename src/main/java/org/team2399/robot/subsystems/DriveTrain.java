package org.team2399.robot.subsystems;

import org.team2399.robot.RobotMap;

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
    	follower.set(ControlMode.Follower, leader.getDeviceID());
	}
    
    public void drivePercent(double leftPercent, double rightPercent) {
		
		double leftPercentForward = leftPercent * RobotMap.Physical.DriveTrain.LEFT_FORWARD;
		double rightPercentForward = rightPercent * RobotMap.Physical.DriveTrain.RIGHT_FORWARD;
		
		leftFrontTalon.set(ControlMode.PercentOutput, leftPercentForward);
		rightFrontTalon.set(ControlMode.PercentOutput, rightPercentForward);
		
		follow(leftMiddleTalon, leftFrontTalon);
		follow(leftBackTalon, leftFrontTalon);
		follow(rightMiddleTalon, rightFrontTalon);
		follow(rightBackTalon, rightFrontTalon);
	}

	@Override
	protected void initDefaultCommand() {
	}
}

