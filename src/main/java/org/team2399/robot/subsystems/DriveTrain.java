package org.team2399.robot.subsystems;

import org.team2399.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	private static final double DRIVETRAIN_KD = 15.0;
	private static final double DRIVETRAIN_KI = 0.001;
	private static final double DRIVETRAIN_KP = 0.3;
	private static final double DRIVETRAIN_KF = 0.33;
	
	private static final double DRIVETRAIN_FAST_KP = 0.15;
	private static final double DRIVETRAIN_FAST_KI = 0.001;
	private static final double DRIVETRAIN_FAST_KD = 0;
	private static final double DRIVETRAIN_FAST_KF = 0.13;
	
	
	private static final int CAN_TIMEOUT = 0;
	
	
	private TalonSRX leftFrontTalon;
	private TalonSRX leftMiddleTalon;
	private TalonSRX leftBackTalon;
	
	private TalonSRX rightFrontTalon;
	private TalonSRX rightMiddleTalon;
	private TalonSRX rightBackTalon;
	
    public DriveTrain() {
    	
    	leftFrontTalon = new TalonSRX(8);
    	leftMiddleTalon = new TalonSRX(7);
    	leftBackTalon = new TalonSRX(3);
    	
    	rightFrontTalon = new TalonSRX(1);
    	rightMiddleTalon = new TalonSRX(2);
    	rightBackTalon = new TalonSRX(5);
    	
    	// timeout constants
    	leftFrontTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, CAN_TIMEOUT);
    	rightFrontTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, CAN_TIMEOUT);
    	leftFrontTalon.setSensorPhase(false);
    	rightFrontTalon.setSensorPhase(false);
    	
    	// timeout constants
		leftFrontTalon.configNominalOutputForward(0, CAN_TIMEOUT);
		leftFrontTalon.configNominalOutputReverse(0, CAN_TIMEOUT);
		leftFrontTalon.configPeakOutputForward(1, CAN_TIMEOUT);
		leftFrontTalon.configPeakOutputReverse(-1, CAN_TIMEOUT);

		leftFrontTalon.config_kF(0, DRIVETRAIN_KF, CAN_TIMEOUT);
		leftFrontTalon.config_kP(0, DRIVETRAIN_KP, CAN_TIMEOUT);
		leftFrontTalon.config_kI(0, DRIVETRAIN_KI, CAN_TIMEOUT);
		leftFrontTalon.config_kD(0, DRIVETRAIN_KD, CAN_TIMEOUT);
		
		rightFrontTalon.configNominalOutputForward(0, CAN_TIMEOUT);
		rightFrontTalon.configNominalOutputReverse(0, CAN_TIMEOUT);
		rightFrontTalon.configPeakOutputForward(1, CAN_TIMEOUT);
		rightFrontTalon.configPeakOutputReverse(-1, CAN_TIMEOUT);

		rightFrontTalon.config_kF(0, DRIVETRAIN_KF, CAN_TIMEOUT);
		rightFrontTalon.config_kP(0, DRIVETRAIN_KP, CAN_TIMEOUT);
		rightFrontTalon.config_kI(0, DRIVETRAIN_KI, CAN_TIMEOUT);
		rightFrontTalon.config_kD(0, DRIVETRAIN_KD, CAN_TIMEOUT);
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
		
		double[] left = {leftVelocityForward, leftFrontTalon.getSelectedSensorVelocity(0)};
		double[] right = {rightVelocityForward, rightFrontTalon.getSelectedSensorVelocity(0)};
		
		SmartDashboard.putNumberArray("left", left);
		SmartDashboard.putNumberArray("right", right);
    }

	@Override
	protected void initDefaultCommand() {
		
	}
}

