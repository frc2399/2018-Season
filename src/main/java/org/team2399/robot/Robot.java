/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team2399.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import org.team2399.robot.AutoChooser.Position;
import org.team2399.robot.AutoChooser.Scoring;
import org.team2399.robot.commands.KajDrive;
import org.team2399.robot.commands.ManualLift;
import org.team2399.robot.commands.Shift;
import org.team2399.robot.commands.TankDrive;
import org.team2399.robot.commands.autoGroups.CenterLeftAuto;
import org.team2399.robot.commands.intake.DoNothing;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;
import edu.wpi.first.wpilibj.SPI.Port;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	private static final double NETWORK_TABLE_UPDATE_RATE = 1.0/20;
	private static final int CAMERA_HEIGHT = 120;
	private static final int CAMERA_WIDTH = 160;
	
	private PowerDistributionPanel pdp;
	private DriveTrain dt;
	private OI oi;
	private Shifter sh;
	private Intake in;
	private Lift li;
	private AHRS navx;
	private AutoChooser auto;
	
	Command autoCommand;
	
	final int NAVX_SLEEPMILLISECONDS = 50;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		pdp = new PowerDistributionPanel(RobotMap.CAN.PDP);
		navx = new AHRS(Port.kMXP);	
		Timer timer = new Timer();
		timer.start();
		while(navx.isCalibrating()) {
			try {
				Thread.sleep(NAVX_SLEEPMILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			if(timer.get() >= 5.0) {
				break;
			}
		}
		navx.reset();
		
		dt = new DriveTrain();
		sh = new Shifter();
		in = new Intake();
		li = new Lift(pdp);
		oi = new XBoxJoystickOI(sh, dt, in, li, navx);
		auto = new AutoChooser(dt, sh, navx, li, in);
		
		dt.defaultCommand(oi.defaultDrive());
		sh.defaultCommand(oi.defaultShift());
		in.defaultCommand(new DoNothing(in));
//		li.defaultCommand(new ManualLift(li, ()->0));
//		UsbCamera cam1 = CameraServer.getInstance().startAutomaticCapture();
//		UsbCamera cam2 = CameraServer.getInstance().startAutomaticCapture();
//		cam1.setResolution(CAMERA_WIDTH, CAMERA_HEIGHT);
//		cam2.setResolution(CAMERA_WIDTH, CAMERA_HEIGHT);
//		
		NetworkTableInstance.getDefault().setUpdateRate(NETWORK_TABLE_UPDATE_RATE);
		
		SmartDashboard.putData(dt);
		SmartDashboard.putData(in);
		
//		auto.test();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		boolean autoRan = false;
		
		if(gameData != null && gameData.length() == 3 && !autoRan) {
			//left = 0, right = 1, center = 2
			//switch = 0, scale = 1, auto left = 2, auto right = 3
			int pos = Integer.parseInt(gameData.substring(3, 4));
			int scoring = Integer.parseInt(gameData.substring(4, 5));
			autoCommand = auto.makeAutoCommand(Position.values()[pos], Scoring.values()[scoring], gameData.substring(0, 2));
			autoCommand.start();
			autoRan = true;
		}
		
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("gyro angle", navx.getAngle());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	@Override
	protected void loopFunc() {
		
		try {
			super.loopFunc();
		} catch (Throwable t) {
			DriverStation.reportError("Unhandled exception: " + t.toString(),
			          t.getStackTrace());
			
			System.exit(1);
		}
		
	}
	
	
}
