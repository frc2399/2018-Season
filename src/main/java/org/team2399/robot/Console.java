package org.team2399.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Console {
	
	//CHECK BUTTONS + CONSTANTS
	
	private OI oi;
	private int type;
	
	public double intakeSpeed;
	public double tankPercentLeft, tankPercentRight;
	public double kajPercentForward, kajPercentTurn;
	public boolean leftShoulder, rightShoulder;
	
	// 0 = 2 joysticks
	// 1 = 3 joysticks
	// 2 = gamepad
	// 3 = xBox
	public Console(OI oi, int type) {
		this.oi = oi;
		this.type = type;
		switch (type) {
		case 0:	intakeSpeed = oi.joyLeft.getRawAxis(1) * -1;
				tankPercentLeft = oi.joyLeft.getRawAxis(1) * -1;
				tankPercentRight = oi.joyRight.getRawAxis(1) * -1;
				kajPercentForward = oi.joyLeft.getRawAxis(1) * -1;
				kajPercentTurn = oi.joyRight.getRawAxis(0);
				
		case 1: intakeSpeed = oi.joyControl.getRawAxis(1) * -1;
				tankPercentLeft = oi.joyLeft.getRawAxis(1) * -1;
				tankPercentRight = oi.joyRight.getRawAxis(1) * -1;
				kajPercentForward = oi.joyLeft.getRawAxis(1) * -1;
				kajPercentTurn = oi.joyLeft.getRawAxis(0);
				
		case 2: intakeSpeed = oi.gamepad.getRawAxis(1) * -1;
				tankPercentLeft = oi.gamepad.getRawAxis(1) * -1;
				tankPercentRight = oi.gamepad.getRawAxis(3);
				kajPercentForward = oi.gamepad.getRawAxis(1) * -1;
				kajPercentTurn = oi.gamepad.getRawAxis(2);
				leftShoulder = oi.gamepad.getRawButton(5);
				rightShoulder = oi.gamepad.getRawButton(6);
						
		case 3: intakeSpeed = oi.xBox.getRawAxis(4);
				tankPercentLeft = oi.xBox.getRawAxis(1) * -1;
				tankPercentRight = oi.xBox.getRawAxis(3);
				kajPercentForward = oi.xBox.getRawAxis(1) * -1;
				kajPercentTurn = oi.xBox.getRawAxis(2);
				leftShoulder = oi.xBox.getRawButton(5);
				rightShoulder = oi.xBox.getRawButton(6);
		}
	}
	
	public int getConsole() {
		return type;
	}
	
}
