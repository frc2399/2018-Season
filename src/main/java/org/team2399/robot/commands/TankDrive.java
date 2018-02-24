package org.team2399.robot.commands;

import java.util.function.DoubleSupplier;

import org.team2399.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TankDrive extends Command {

	DriveTrain dt;
	DoubleSupplier leftPercent, rightPercent;
	
	public TankDrive(DriveTrain dt, DoubleSupplier leftPercent, DoubleSupplier rightPercent) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.dt = dt;
    	this.leftPercent = leftPercent;
    	this.rightPercent = rightPercent;
    	
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
    	dt.drivePercent(leftPercent.getAsDouble(), rightPercent.getAsDouble());
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
