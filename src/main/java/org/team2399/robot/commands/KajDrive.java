package org.team2399.robot.commands;

import java.util.function.DoubleSupplier;

import org.team2399.robot.OI;
import org.team2399.robot.Utility;
import org.team2399.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class KajDrive extends Command {

	DriveTrain dt;
	DoubleSupplier forwardPercent, turnPercent;
	DoubleSupplier leftTurn, rightTurn;
	
	public KajDrive(DriveTrain dt, DoubleSupplier forwardPercent, DoubleSupplier turnPercent, DoubleSupplier leftTurn, DoubleSupplier rightTurn) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.dt = dt;
    	this.forwardPercent = forwardPercent;
    	this.turnPercent = turnPercent;
    	//this.leftTurn = leftTurn;
    	//this.rightTurn = rightTurn;
    	this.leftTurn = ()->(0.0);
    	this.rightTurn = ()->(0.0);
    	
    	requires(this.dt);
		setInterruptible(true);
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	dt.disableVoltageComp();
    	dt.brakeMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	    	double forward = forwardPercent.getAsDouble();
	    	double turn = turnPercent.getAsDouble();
	    	
	    	double leftSideSpeed = (forward + turn * (Math.abs(forward)));
		double rightSideSpeed = (forward - turn * (Math.abs(forward)));
		
		if(Utility.inRange(forward, 0, OI.DEADBAND_WIDTH * 2))
		{
			leftSideSpeed = turn/ 2;
			rightSideSpeed = -turn / 2;
		}
			
		
//		if (leftTurn.getAsBoolean()) {
//			leftSideSpeed = -1;
//			rightSideSpeed = 1;
//		}
//		
//		if (rightTurn.getAsBoolean()) {
//			leftSideSpeed = 1;
//			rightSideSpeed = -1;
//		}
		
		if (leftTurn.getAsDouble() > 0.25) {
			leftSideSpeed = -1 * leftTurn.getAsDouble();
			rightSideSpeed = leftTurn.getAsDouble();
		}
		
		if (rightTurn.getAsDouble() > 0.25) {
			leftSideSpeed = rightTurn.getAsDouble();
			rightSideSpeed = -1 * rightTurn.getAsDouble();
		}
		
		dt.drivePercent(leftSideSpeed, rightSideSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
