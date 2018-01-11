package org.team2399.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	PWMTalonSRX leftTalon1 = new PWMTalonSRX(1);
	PWMTalonSRX leftTalon2 = new PWMTalonSRX(2);
	PWMTalonSRX rightTalon1 = new PWMTalonSRX(3);
	PWMTalonSRX rightTalon2 = new PWMTalonSRX(4);
	TalonSRX test = new TalonSRX(5);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void defaultCommand(Command c) {
    	setDefaultCommand(c);
    }
    
    public DriveTrain() {
    	
    }
    
    public void tankDrive(double leftPercent, double rightPercent) {
    	leftTalon1.set(leftPercent);
    	leftTalon2.set(leftPercent);
		rightTalon1.set(rightPercent);
		rightTalon2.set(rightPercent);
    }
}

