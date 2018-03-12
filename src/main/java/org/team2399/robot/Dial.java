package org.team2399.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class Dial {
	
	private static final boolean SELECTED = false;
	
	DigitalInput[] inputs;
	
	public Dial(int startInput, int numPos) {
		inputs = new DigitalInput[numPos];
		for(int i = 0; i < numPos; i++) {
			inputs[i] = new DigitalInput(i + startInput);
		}
	}
	
	public int getPosition(int defaultPos) {
		for(int i = 0; i < inputs.length; i++) {
			if(inputs[i].get() == SELECTED) {
				return i;
			}
		}
		
		return defaultPos;
	}
}
