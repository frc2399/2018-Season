package org.team2399.robot.commands.intake;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class XBoxShoulders extends Button {

	DoubleSupplier value;
	
	public XBoxShoulders(DoubleSupplier value) {
		this.value = value;
	}

	@Override
	public boolean get() {
		return value.getAsDouble() > 0.5;
	}
}
