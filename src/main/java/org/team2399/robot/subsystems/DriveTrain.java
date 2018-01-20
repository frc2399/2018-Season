package org.team2399.robot.subsystems;

import org.team2399.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	private TalonSRX leftFrontTalon = new TalonSRX(8);
	private TalonSRX leftMiddleTalon = new TalonSRX(7);
	private TalonSRX leftBackTalon = new TalonSRX(3);
	
	private TalonSRX rightFrontTalon = new TalonSRX(1);
	private TalonSRX rightMiddleTalon = new TalonSRX(2);
	private TalonSRX rightBackTalon = new TalonSRX(5);
	
    public void DriveTrain() {

    	// timeout constants
    	leftFrontTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    	rightFrontTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    	leftFrontTalon.setSensorPhase(true);
    	rightFrontTalon.setSensorPhase(true);
    	
    	// timeout constants
		leftFrontTalon.configNominalOutputForward(0, 10);
		leftFrontTalon.configNominalOutputReverse(0, 10);
		leftFrontTalon.configPeakOutputForward(1, 10);
		leftFrontTalon.configPeakOutputReverse(-1, 10);

		leftFrontTalon.config_kF(0, 0.5, 10);
		leftFrontTalon.config_kP(0, 0.5, 10);
		leftFrontTalon.config_kI(0, 0.0015, 10);
		leftFrontTalon.config_kD(0, 0.0, 10);
		
		rightFrontTalon.configNominalOutputForward(0, 10);
		rightFrontTalon.configNominalOutputReverse(0, 10);
		rightFrontTalon.configPeakOutputForward(1, 10);
		rightFrontTalon.configPeakOutputReverse(-1, 10);

		rightFrontTalon.config_kF(0, 0.5, 10);
		rightFrontTalon.config_kP(0, 0.5, 10);
		rightFrontTalon.config_kI(0, 0.0015, 10);
		rightFrontTalon.config_kD(0, 0.0, 10);
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

    public void driveVelocity(double leftVelocity, double rightVelocity) {
		double leftVelocityForward = leftVelocity * 4096 / 600 * RobotMap.Physical.DriveTrain.LEFT_FORWARD ;
		double rightVelocityForward = rightVelocity * 4096 / 600 * RobotMap.Physical.DriveTrain.RIGHT_FORWARD;
		
		leftFrontTalon.set(ControlMode.Velocity, leftVelocityForward);
		rightFrontTalon.set(ControlMode.Velocity, rightVelocityForward);
		
		follow(leftMiddleTalon, leftFrontTalon);
		follow(leftBackTalon, leftFrontTalon);
		follow(rightMiddleTalon, rightFrontTalon);
		follow(rightBackTalon, rightFrontTalon);
    }

	@Override
	protected void initDefaultCommand() {
		
	}
}

