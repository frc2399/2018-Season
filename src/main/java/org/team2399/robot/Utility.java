package org.team2399.robot;

public class Utility
{
	public Utility()
	{
		
	}
	
	/**
	 * Unit Conversions
	 * @param feet
	 * @return
	 */
	public static double feetToInches(double feet)
	{
		return feet * 12.0;
	}

	public static double inchesToFeet(double inches)
	{
		return inches / 12.0;
	}
	
	public static double inchesToEncoderTicks(double inches)
	{
		return (inches / RobotMap.Physical.DriveTrain.WHEEL_CIRCUMFERENCE)
				* RobotMap.Physical.DriveTrain.GEAR_RATIO
				* RobotMap.Physical.DriveTrain.ENCODER_COUNT;
	}
	
	public static double secondsToTenMs(double seconds)
	{
		return seconds / 0.010;
	}
	
	/**
	 * 
	 * @param inchesPerSecond
	 * @return Talon velocity (encoder ticks per 10 ms)
	 */
	/*public static double inchesPerSecondToTalonVelocity(double inchesPerSecond)
	{
		double seconds = 1;
		double inches = inchesPerSecond * seconds;
		
		double encoderTicks = inchesToEncoderTicks(inches);
		double tenMs = secondsToTenMs(seconds);
		
		return encoderTicks / tenMs;
	}*/
	
	/**
	 * Wheel
	 * @param inchesPerSecond
	 * @return RPM
	 */
	public static double inchesPerSecondToGearboxRPM(double inchesPerSecond)
	{
		double seconds = 1;
		double inches = inchesPerSecond * seconds;
		
		double wheelRotations = inches / RobotMap.Physical.DriveTrain.WHEEL_CIRCUMFERENCE;
		double gearboxRotations = RobotMap.Physical.DriveTrain.GEAR_RATIO * wheelRotations;
		double minutes = seconds / 60;
		
		return gearboxRotations / minutes;
	}
	
	public static boolean inRange(double first, double second, double tolerance)
	{
		double upperBound = first + tolerance;
		double lowerBound = first - tolerance;
		
		return (second < upperBound) && (second > lowerBound);
	}
}
