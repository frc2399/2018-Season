package org.team2399.robot.subsystems;

import org.team2399.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift extends Subsystem {

	public static final int LIFT_MAX_HEIGHT_ENCODERS = 60000;
	public static final int LIFT_MAX_HEIGHT_INCHES = 79;
	private static final int PID_IDX = 0;
	private static final double CLOSED_LOOP_VOLTAGE_SATURATION = 10;
	private double fuzz = 0.001;

	private PowerDistributionPanel pdp;
	private IMotorControllerEnhanced talon;
	private IMotorController victor;
	private double desiredHeight;
	private boolean isManual;
	private boolean zeroed;
	
	private double[] filter;
	private int filterIndex;

	//change can timeout
	private static final int CAN_TIMEOUT = 10;
	private static final double LIFT_KP = 0.16;
	private static final double LIFT_KI = 0.00001;
	private static final double LIFT_KD = 5.0;
	private static final double LIFT_KF = 0;
	
	private double pLift, iLift, dLift, fLift;
	
	public Lift(PowerDistributionPanel pdp) {
		desiredHeight = 0;
		isManual = true;
		zeroed = false;
		
		this.pdp = pdp;
		
		talon = new TalonSRX(23);
		victor = new VictorSPX(24);
		
		talon.configVoltageCompSaturation(CLOSED_LOOP_VOLTAGE_SATURATION, CAN_TIMEOUT);
		talon.enableVoltageCompensation(true);
		
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
		talon.setSensorPhase(true);
		
		talon.configNominalOutputForward(0, CAN_TIMEOUT);
		talon.configNominalOutputReverse(0, CAN_TIMEOUT);
		talon.configPeakOutputForward(1, CAN_TIMEOUT);
		talon.configPeakOutputReverse(-1, CAN_TIMEOUT);
		
		talon.setNeutralMode(NeutralMode.Brake);
		victor.setNeutralMode(NeutralMode.Brake);
		
		talon.configVoltageCompSaturation(CLOSED_LOOP_VOLTAGE_SATURATION, CAN_TIMEOUT);
		talon.enableVoltageCompensation(true);
		victor.configVoltageCompSaturation(CLOSED_LOOP_VOLTAGE_SATURATION, CAN_TIMEOUT);
		victor.enableVoltageCompensation(true);
		
		
		victor.follow(talon);
		victor.setInverted(true);
		
		talon.configForwardSoftLimitThreshold(LIFT_MAX_HEIGHT_ENCODERS, CAN_TIMEOUT);
		talon.configForwardSoftLimitEnable(true, CAN_TIMEOUT);
		
		talon.configReverseSoftLimitThreshold(0, CAN_TIMEOUT);
		talon.configReverseSoftLimitEnable(true, CAN_TIMEOUT);
		
		pLift = LIFT_KP;
		iLift = LIFT_KI;
		dLift = LIFT_KD;
		fLift = LIFT_KF;
		
		setConstants(pLift, iLift, dLift, fLift);
		
		SmartDashboard.putNumber("pLift", pLift);
		SmartDashboard.putNumber("iLift", iLift);
		SmartDashboard.putNumber("dLift", dLift);
		SmartDashboard.putNumber("fLift", fLift);
		
		filter = new double[20];
		filterIndex = 0;
		
		for(int i = 0; i < filter.length; i++)
			filter[i] = 0.0;
	}
	
	 @Override
	public void periodic() {
		if(!zeroed) {
			talon.setSelectedSensorPosition(0, PID_IDX, CAN_TIMEOUT);
			zeroed = true;
		}
		
		flipFuzz();
		setHeight();
		
		double[] inputCurrentArr = {pdp.getCurrent(3), pdp.getCurrent(12), fuzz};
		double inputVoltage = pdp.getVoltage();
		double[] outputVoltage = {talon.getMotorOutputVoltage(), victor.getMotorOutputVoltage(), fuzz};
		
		double[] liftPosArr = {getHeight(), desiredHeight, fuzz};
		double[] percentArr = {talon.getMotorOutputPercent(), victor.getMotorOutputPercent(), fuzz};
		
		double[] outputCurrentCalc = new double[3];
		for(int i = 0; i < outputCurrentCalc.length - 1; i++) {
			double d = inputCurrentArr[i] * inputVoltage / outputVoltage[i];
			outputCurrentCalc[i] = Double.isNaN(d) ? inputCurrentArr[i] : d;
		}
		outputCurrentCalc[2] = fuzz;
		
		SmartDashboard.putNumberArray("liftPos", liftPosArr);
		SmartDashboard.putNumberArray("percent", percentArr);
		
		pLift = SmartDashboard.getNumber("pLift", 0);
		iLift = SmartDashboard.getNumber("iLift", 0);
		dLift = SmartDashboard.getNumber("dLift", 0);
		fLift = SmartDashboard.getNumber("fLift", 0);
		
		setConstants(pLift, iLift, dLift, fLift);
		
	}
	 
	private void setHeight() {
		if(!isManual){
			talon.set(ControlMode.Position, inchesToEncoderTicks(desiredHeight));
		}
	}
	
	public void setVarHeight(double desiredHeight) {
		this.desiredHeight = desiredHeight;
		isManual = false;
	}
	
	public void manualControl(double percent) {
		isManual = true;
		talon.set(ControlMode.PercentOutput, percent * RobotMap.Physical.Lift.LIFT_UP);
	}
	
	public double getDesiredHeight() {
		return desiredHeight;
	}
	
	public boolean isManual() {
		return isManual;
	}

	public void setConstants(double p, double i, double d, double f) {
		talon.config_kP(PID_IDX, p, CAN_TIMEOUT);
		talon.config_kI(PID_IDX, i, CAN_TIMEOUT);
		talon.config_kD(PID_IDX, d, CAN_TIMEOUT);
		talon.config_kF(PID_IDX, f, CAN_TIMEOUT);
	}
	
	public void defaultCommand(Command c) {
	    	setDefaultCommand(c);
	}
	
	public double getHeight() {
		return encoderTicksToInches(talon.getSelectedSensorPosition(PID_IDX));
	}
	
	private void flipFuzz() {
    	fuzz *= -1;
    }
	
	private double inchesToEncoderTicks(double inches) {
		return inches * LIFT_MAX_HEIGHT_ENCODERS / LIFT_MAX_HEIGHT_INCHES;
	}
	
	private double encoderTicksToInches(double ticks) {
		return ticks * LIFT_MAX_HEIGHT_INCHES / LIFT_MAX_HEIGHT_ENCODERS;
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	 
}
