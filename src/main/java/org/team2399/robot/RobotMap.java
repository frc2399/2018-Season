/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team2399.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static class CAN {
		public static final int PCM = 3;
		
	}
	
	public static class PCM {
		public static final int SHIFTER_SlOW = 0;
		public static final int SHIFTER_FAST = 1;
		public static final int EXTENDER_OUT = 5;
		public static final int EXTENDER_IN = 4;
		public static final int GRABBER_OUT = 2;
		public static final int GRABBER_IN = 3;
	}
	
	public static class Physical {
		public static class DriveTrain {
			public static final double DRIVETRAIN_WHEEL_DIAMETER = 4.0;
			public static final double WHEEL_CIRCUMFERENCE = DRIVETRAIN_WHEEL_DIAMETER * Math.PI;
			public static final double GEAR_RATIO = 1.797;
			public static final double ENCODER_COUNT = 4096;
			
			public static final int LEFT_FORWARD = -1;
			public static final int RIGHT_FORWARD = 1;
			
			public static final double ROBOT_LENGTH = 34.5;
			public static final double ROBOT_WIDTH = 29.25;
		}
	}
	
	public static class Auto {
		public static final int SHORT_LEFT_TURN = -45;
		public static final int LONG_LEFT_TURN = -90;
		public static final int SHORT_RIGHT_TURN = 45;
		public static final int LONG_RIGHT_TURN = 90;
		
		public static final double BACK_WALL_TO_SWITCH = 140.0;
		public static final double BACK_WALL_TO_SCALE = 299.65;
		public static final double BACK_WALL_TO_PLATFORM_ZONE = 196.0;
		public static final double PLATFORM_TO_SCALE = 38.18;
		
		public static final double CENTER_FORWARD = 36.0;
		public static final double CENTER_AUTO = 132.0;
		
		public static class FieldMeasurements{
			public static final double CUBE_LENGTH = 13.0;	
			public static final double FIELD_WIDTH = 323.38;
			public static final double PLATFORM_WIDTH = 132.88;
			public static final double SCALE_STICK_OUT_FROM_PLATFORM = 23.68;	
		}	
	}

	public static final double ANGLE_TOLERANCE = 0.75;
	public static final double EJECT_SPEED = 1;
	
}
