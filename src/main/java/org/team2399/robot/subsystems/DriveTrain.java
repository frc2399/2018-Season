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
	
	private static final int WHEEL_DIAMETER = 4;
	private static final int ENCODER_TICKS_PER_REVOLUTION = 4096;
	private static final double GEAR_RATIO = 24.0 / 60.0;
	private static final int TALON_100MS_IN_1S = 10;
	private static final double DRIVETRAIN_KD = 15.0;
	private static final double DRIVETRAIN_KI = 0.001;
	private static final double DRIVETRAIN_KP = 0.3;
	private static final double DRIVETRAIN_KF = 0.33;
	
	private static final double DRIVETRAIN_FAST_KP = 0.8;
	private static final double DRIVETRAIN_FAST_KI = 0.004;
	private static final double DRIVETRAIN_FAST_KD = 35;
	private static final double DRIVETRAIN_FAST_KF = 0.1;
	
	
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
	
	private double fuzz;
	
    public DriveTrain() {
    	
    	leftFrontTalon = new TalonSRX(8);
    	leftMiddleTalon = new TalonSRX(7);
    	leftBackTalon = new TalonSRX(3);
    	
    	rightFrontTalon = new TalonSRX(1);
    	rightMiddleTalon = new TalonSRX(2);
    	rightBackTalon = new TalonSRX(5);
    	
		follow(leftMiddleTalon, leftFrontTalon);
		follow(leftBackTalon, leftFrontTalon);
		follow(rightMiddleTalon, rightFrontTalon);
		follow(rightBackTalon, rightFrontTalon);
    	
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
		
		fuzz = 0.001;
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
	
	}
    
    public double toInPerSecFromNativeTalon(double talonNative) {
    	return talonNative * (WHEEL_DIAMETER * (Math.PI / ENCODER_TICKS_PER_REVOLUTION)) * GEAR_RATIO * TALON_100MS_IN_1S;
    }
    
    public double toNativeTalonFromInPerSec(double inPerSec) {
    	// 60.0 / 24.0
    	return inPerSec * (4096.0 / (4.0 * Math.PI)) * (1.79) * (1.0 / 10.0);
    }

    public void driveVelocity(double leftVelocity, double rightVelocity) {
		flipFuzz();
    	
    	double desiredLeftVelocityForward = toNativeTalonFromInPerSec(leftVelocity) * RobotMap.Physical.DriveTrain.LEFT_FORWARD ;
		double desiredRightVelocityForward = toNativeTalonFromInPerSec(rightVelocity) * RobotMap.Physical.DriveTrain.RIGHT_FORWARD;
		
		leftFrontTalon.set(ControlMode.Velocity, desiredLeftVelocityForward);
		rightFrontTalon.set(ControlMode.Velocity, desiredRightVelocityForward);
		
		double actualLeftVelocityForward = leftFrontTalon.getSelectedSensorVelocity(0);
		double actualRightVelocityForward = rightFrontTalon.getSelectedSensorVelocity(0);
		
		double[] leftVelocityArr = {desiredLeftVelocityForward, actualLeftVelocityForward, fuzz};
		double[] rightVelocityArr = {desiredRightVelocityForward, actualRightVelocityForward, fuzz};
		
		SmartDashboard.putNumberArray("leftVelocity", leftVelocityArr);
		SmartDashboard.putNumberArray("rightVelocity", rightVelocityArr);
		
		
		double desiredLeftAccel = desiredLeftVelocityForward - desiredLeftVelPrev;
		double desiredRightAccel = desiredRightVelocityForward - desiredRightVelPrev;
		
		double actualLeftAccel = actualLeftVelocityForward - actualLeftVelPrev;
		double actualRightAccel = actualRightVelocityForward - actualRightVelPrev;
		
		
		double[] leftAccelArr = {desiredLeftAccel, actualLeftAccel, fuzz};
		double[] rightAccelArr = {desiredRightAccel, actualRightAccel, fuzz};
		
		SmartDashboard.putNumberArray("leftAccel",  leftAccelArr);
		SmartDashboard.putNumberArray("rightAccel", rightAccelArr);
		
		
		desiredLeftVelPrev = desiredLeftVelocityForward;
		desiredRightVelPrev = desiredRightVelocityForward;
		
		actualLeftVelPrev = actualLeftVelocityForward;
		actualRightVelPrev = actualRightVelocityForward;
		
    }
    
    private void flipFuzz() {
    	fuzz *= -1;
    }

	@Override
	protected void initDefaultCommand() {
		
	}
}

