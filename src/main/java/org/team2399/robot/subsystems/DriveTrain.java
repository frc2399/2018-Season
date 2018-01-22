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
	
	private static final double DRIVETRAIN_FAST_KP = 0.3;
	private static final double DRIVETRAIN_FAST_KI = 0.001;
	private static final double DRIVETRAIN_FAST_KD = 0;
	private static final double DRIVETRAIN_FAST_KF = 0.13;
	
	
	private static final int CAN_TIMEOUT = 0;
	
	private double desiredLeftVelPrev;
	private double desiredRightVelPrev;
	private double actualLeftVelPrev;
	private double actualRightVelPrev;
	
	
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

		leftFrontTalon.config_kF(0, DRIVETRAIN_FAST_KF, CAN_TIMEOUT);
		leftFrontTalon.config_kP(0, DRIVETRAIN_FAST_KP, CAN_TIMEOUT);
		leftFrontTalon.config_kI(0, DRIVETRAIN_FAST_KI, CAN_TIMEOUT);
		leftFrontTalon.config_kD(0, DRIVETRAIN_FAST_KD, CAN_TIMEOUT);
		
		rightFrontTalon.configNominalOutputForward(0, CAN_TIMEOUT);
		rightFrontTalon.configNominalOutputReverse(0, CAN_TIMEOUT);
		rightFrontTalon.configPeakOutputForward(1, CAN_TIMEOUT);
		rightFrontTalon.configPeakOutputReverse(-1, CAN_TIMEOUT);

		rightFrontTalon.config_kF(0, DRIVETRAIN_FAST_KF, CAN_TIMEOUT);
		rightFrontTalon.config_kP(0, DRIVETRAIN_FAST_KP, CAN_TIMEOUT);
		rightFrontTalon.config_kI(0, DRIVETRAIN_FAST_KI, CAN_TIMEOUT);
		rightFrontTalon.config_kD(0, DRIVETRAIN_FAST_KD, CAN_TIMEOUT);
		
		desiredLeftVelPrev = 0;
		desiredRightVelPrev = 0;
		actualLeftVelPrev = 0;
		actualRightVelPrev = 0;
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
    
    public double toInPerSecFromNativeTalon(double talonNative) {
    	return talonNative * (4 * (Math.PI / 4096)) * (24 / 60) * 10;
    }
    
    public double toNativeTalonFromInPerSec(double inPerSec) {
    	return inPerSec * (4096.0 / (4.0 * Math.PI)) * (60.0 / 24.0) * (1.0 / 10.0);
    }

    public void driveVelocity(double leftVelocity, double rightVelocity) {
		double desiredLeftVelocityForward = toNativeTalonFromInPerSec(leftVelocity) * RobotMap.Physical.DriveTrain.LEFT_FORWARD ;
		double desiredRightVelocityForward = toNativeTalonFromInPerSec(rightVelocity) * RobotMap.Physical.DriveTrain.RIGHT_FORWARD;
		
		leftFrontTalon.set(ControlMode.Velocity, desiredLeftVelocityForward);
		rightFrontTalon.set(ControlMode.Velocity, desiredRightVelocityForward);
		
		follow(leftMiddleTalon, leftFrontTalon);
		follow(leftBackTalon, leftFrontTalon);
		follow(rightMiddleTalon, rightFrontTalon);
		follow(rightBackTalon, rightFrontTalon);
		
		double actualLeftVelocityForward = leftFrontTalon.getSelectedSensorVelocity(0);
		double actualRightVelocityForward = rightFrontTalon.getSelectedSensorVelocity(0);
		
		double[] leftVelocityArr = {desiredLeftVelocityForward, actualLeftVelocityForward};
		double[] rightVelocityArr = {desiredRightVelocityForward, actualRightVelocityForward};
		
		SmartDashboard.putNumberArray("leftVelocity", leftVelocityArr);
		SmartDashboard.putNumberArray("rightVelocity", rightVelocityArr);
		
		
		double desiredLeftAccel = desiredLeftVelocityForward - desiredLeftVelPrev;
		double desiredRightAccel = desiredRightVelocityForward - desiredRightVelPrev;
		
		double actualLeftAccel = actualLeftVelocityForward - actualLeftVelPrev;
		double actualRightAccel = actualRightVelocityForward - actualRightVelPrev;
		
		
		double[] leftAccelArr = {desiredLeftAccel, actualLeftAccel};
		double[] rightAccelArr = {desiredRightAccel, actualRightAccel};
		
		SmartDashboard.putNumberArray("leftAccel",  leftAccelArr);
		SmartDashboard.putNumberArray("rightAccel", rightAccelArr);
		
		
		desiredLeftVelPrev = desiredLeftVelocityForward;
		desiredRightVelPrev = desiredRightVelocityForward;
		
		actualLeftVelPrev = actualLeftVelocityForward;
		actualRightVelPrev = actualRightVelocityForward;
		
		
		
    }

	@Override
	protected void initDefaultCommand() {
		
	}
}

