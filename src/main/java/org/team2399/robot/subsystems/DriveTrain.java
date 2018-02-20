package org.team2399.robot.subsystems;

import org.team2399.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	
	private static final double CLOSED_LOOP_VOLTAGE_SATURATION = 10;
	private static final int WHEEL_DIAMETER = 4;
	private static final int ENCODER_TICKS_PER_REVOLUTION = 4096;
	private static final double GEAR_RATIO = 24.0 / 60.0;
	private static final int TALON_100MS_IN_1S = 10;
	private static final double DRIVETRAIN_KD = 15.0;
	private static final double DRIVETRAIN_KI = 0.001;
	private static final double DRIVETRAIN_KP = 0.3;
	private static final double DRIVETRAIN_KF = 0.33;
	
	public static final double DRIVETRAIN_FAST_KP = 0.8;
	public static final double DRIVETRAIN_FAST_KI = 0.004;
	public static final double DRIVETRAIN_FAST_KD = 35;
	public static final double DRIVETRAIN_FAST_KF = 0.1;
	
	private static final int PID_IDX = 0;
	private static final int CAN_TIMEOUT = 10;
	
	private double desiredLeftVelPrev;
	private double desiredRightVelPrev;
	private double actualLeftVelPrev;
	private double actualRightVelPrev;
	
	
	private IMotorControllerEnhanced leftFront;
	private IMotorController leftMiddle;
	private IMotorController leftBack;
	
	private IMotorControllerEnhanced rightFront;
	private IMotorController rightMiddle;
	private IMotorController rightBack;
	
	private IMotorController[] allMotorControllers;
	
	private double fuzz;
	
    public DriveTrain() {
    	
    	leftFront = new TalonSRX(RobotMap.Physical.DriveTrain.LEFT_FRONT_ID);
    	leftMiddle = new VictorSPX(RobotMap.Physical.DriveTrain.LEFT_MIDDLE_ID);
    	leftBack = new VictorSPX(RobotMap.Physical.DriveTrain.LEFT_BACK_ID);
    	
    	rightFront = new TalonSRX(RobotMap.Physical.DriveTrain.RIGHT_FRONT_ID);
    	rightMiddle = new VictorSPX(RobotMap.Physical.DriveTrain.RIGHT_MIDDLE_ID);
    	rightBack = new VictorSPX(RobotMap.Physical.DriveTrain.RIGHT_BACK_ID);
    	
//    	leftFront = new TalonSRX(8);
//    	leftMiddle = new TalonSRX(7);
//    	leftBack = new TalonSRX(3);
//    	
//    	rightFront = new TalonSRX(1);
//    	rightMiddle = new TalonSRX(2);
//    	rightBack = new TalonSRX(5);
    	
    	IMotorController[] allMotorControllers = {leftFront, leftMiddle, leftBack, rightFront, rightMiddle, rightBack};
    	this.allMotorControllers = allMotorControllers;
    	
		leftMiddle.follow(leftFront);
		leftBack.follow(leftFront);
		rightMiddle.follow(rightFront);
		rightBack.follow(rightFront);
    	
    	// timeout constants
    	leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
    	rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
    	leftFront.setSensorPhase(false);
    	rightFront.setSensorPhase(false);
    	
    	// timeout constants
		setConstants(DRIVETRAIN_FAST_KP, DRIVETRAIN_FAST_KI, DRIVETRAIN_FAST_KD, DRIVETRAIN_FAST_KF);
		
		for(IMotorController talon : allMotorControllers) {
    		talon.configClosedloopRamp(0.15, CAN_TIMEOUT);
    		talon.configOpenloopRamp(0.15, CAN_TIMEOUT);
    	}
		
		enableVoltageComp();
		brakeMode();
		
		desiredLeftVelPrev = 0;
		desiredRightVelPrev = 0;
		actualLeftVelPrev = 0;
		actualRightVelPrev = 0;
		
		fuzz = 0.001;
    }
    
    public void defaultCommand(Command c) {
    	setDefaultCommand(c);
    }
    
    public void drivePercent(double leftPercent, double rightPercent) {
		
		double leftPercentForward = leftPercent * RobotMap.Physical.DriveTrain.LEFT_FORWARD;
		double rightPercentForward = rightPercent * RobotMap.Physical.DriveTrain.RIGHT_FORWARD;
		
		leftFront.set(ControlMode.PercentOutput, leftPercentForward);
		rightFront.set(ControlMode.PercentOutput, rightPercentForward);
	
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
		
		leftFront.set(ControlMode.Velocity, desiredLeftVelocityForward);
		rightFront.set(ControlMode.Velocity, desiredRightVelocityForward);
		
		double actualLeftVelocityForward = leftFront.getSelectedSensorVelocity(0);
		double actualRightVelocityForward = rightFront.getSelectedSensorVelocity(0);
		
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
    
    public void enableVoltageComp() {
    	for(IMotorController talon : allMotorControllers) {
    		talon.configVoltageCompSaturation(CLOSED_LOOP_VOLTAGE_SATURATION, CAN_TIMEOUT);
    		talon.enableVoltageCompensation(true);
    	}
    }
    
    public void disableVoltageComp() {
    	for(IMotorController talon : allMotorControllers) {
    		talon.enableVoltageCompensation(false);
    	}
    }
    
    public void brakeMode() {
    	for(IMotorController talon : allMotorControllers) {
    		talon.setNeutralMode(NeutralMode.Brake);
    	}
    }
    
    public void coastMode() {
    	for(IMotorController talon : allMotorControllers) {
    		talon.setNeutralMode(NeutralMode.Coast);
    	}
    }
    
    public void setConstants(double p, double i, double d, double f) {
    	leftFront.configNominalOutputForward(0, CAN_TIMEOUT);
		leftFront.configNominalOutputReverse(0, CAN_TIMEOUT);
		leftFront.configPeakOutputForward(1, CAN_TIMEOUT);
		leftFront.configPeakOutputReverse(-1, CAN_TIMEOUT);

		leftFront.config_kF(0, f, CAN_TIMEOUT);
		leftFront.config_kP(0, p, CAN_TIMEOUT);
		leftFront.config_kI(0, i, CAN_TIMEOUT);
		leftFront.config_kD(0, d, CAN_TIMEOUT);
		
		rightFront.configNominalOutputForward(0, CAN_TIMEOUT);
		rightFront.configNominalOutputReverse(0, CAN_TIMEOUT);
		rightFront.configPeakOutputForward(1, CAN_TIMEOUT);
		rightFront.configPeakOutputReverse(-1, CAN_TIMEOUT);

		rightFront.config_kF(0, f, CAN_TIMEOUT);
		rightFront.config_kP(0, p, CAN_TIMEOUT);
		rightFront.config_kI(0, i, CAN_TIMEOUT);
		rightFront.config_kD(0, d, CAN_TIMEOUT);
    }
    
    private void flipFuzz() {
    	fuzz *= -1;
    }

	@Override
	protected void initDefaultCommand() {
		
	}
}

