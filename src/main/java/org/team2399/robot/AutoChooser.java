package org.team2399.robot;

import java.util.HashMap;
import java.util.Map;

import org.team2399.robot.commands.auto.CenterLeftAuto;
import org.team2399.robot.commands.auto.CenterLeftSwitch;
import org.team2399.robot.commands.auto.CenterRightAuto;
import org.team2399.robot.commands.auto.CenterRightSwitch;
import org.team2399.robot.commands.auto.DoNothing;
import org.team2399.robot.commands.auto.LeftAuto;
import org.team2399.robot.commands.auto.LeftOwnedScale;
import org.team2399.robot.commands.auto.LeftOwnedSwitch;
import org.team2399.robot.commands.auto.LeftUnownedScale;
import org.team2399.robot.commands.auto.LeftUnownedSwitch;
import org.team2399.robot.commands.auto.RightAuto;
import org.team2399.robot.commands.auto.RightOwnedScale;
import org.team2399.robot.commands.auto.RightOwnedSwitch;
import org.team2399.robot.commands.auto.RightUnownedScale;
import org.team2399.robot.commands.auto.RightUnownedSwitch;
import org.team2399.robot.subsystems.DriveTrain;
import org.team2399.robot.subsystems.Intake;
import org.team2399.robot.subsystems.Lift;
import org.team2399.robot.subsystems.Shifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class AutoChooser {
	public static enum Position{LEFT, RIGHT, CENTER;}
	public static enum Scoring{SWITCH, SCALE, AUTO_LEFT, AUTO_RIGHT;}
	private String[] gameDataOptions = {"LL", "LR", "RL", "RR"};
	
	private Map<AutoState, Command> map;
	
	public AutoChooser(OI oi, DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {		
		
		map = new HashMap<>();
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, "LL"), new LeftOwnedSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, "LR"), new LeftOwnedSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, "RL"), new LeftUnownedSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, "RR"), new LeftUnownedSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, "LL"), new LeftOwnedScale(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, "LR"), new LeftUnownedScale(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, "RL"), new LeftOwnedScale(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, "RR"), new LeftUnownedScale(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, "LL"), new LeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, "LR"), new LeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, "RL"), new LeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, "RR"), new LeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, "LL"), new LeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, "LR"), new LeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, "RL"), new LeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, "RR"), new LeftAuto(oi, dt, sh, navx, li, in));
		
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, "LL"), new RightUnownedSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, "LR"), new RightUnownedSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, "RL"), new RightOwnedSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, "RR"), new RightOwnedSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, "LL"), new RightUnownedScale(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, "LR"), new RightOwnedScale(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, "RL"), new RightUnownedScale(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, "RR"), new RightOwnedScale(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, "LL"), new RightAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, "LR"), new RightAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, "RL"), new RightAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, "RR"), new RightAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, "LL"), new RightAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, "LR"), new RightAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, "RL"), new RightAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, "RR"), new RightAuto(oi, dt, sh, navx, li, in));
		
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, "LL"), new CenterLeftSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, "LR"), new CenterLeftSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, "RL"), new CenterRightSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, "RR"), new CenterRightSwitch(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, "LL"), new CenterLeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, "LR"), new CenterLeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, "RL"), new CenterLeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, "RR"), new CenterLeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, "LL"), new CenterLeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, "LR"), new CenterLeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, "RL"), new CenterLeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, "RR"), new CenterLeftAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, "LL"), new CenterRightAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, "LR"), new CenterRightAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, "RL"), new CenterRightAuto(oi, dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, "RR"), new CenterRightAuto(oi, dt, sh, navx, li, in));

	}
	
	public static class AutoState {
		public final Position pos;
		public final Scoring score;
		public final String gameData;
		public AutoState (Position pos, Scoring score, String gameData) {
			this.pos = pos;
			this.score = score;
			this.gameData = gameData;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((gameData == null) ? 0 : gameData.hashCode());
			result = prime * result + ((pos == null) ? 0 : pos.hashCode());
			result = prime * result + ((score == null) ? 0 : score.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AutoState other = (AutoState) obj;
			if (gameData == null) {
				if (other.gameData != null)
					return false;
			} else if (!gameData.equals(other.gameData))
				return false;
			if (pos != other.pos)
				return false;
			if (score != other.score)
				return false;
			return true;
		}	
	}
	
	public Command makeAutoCommand (Position p, Scoring s) {		
		String gameData = DriverStation.getInstance().getGameSpecificMessage().substring(0,2);
		return makeAutoCommand(p, s, gameData);
	}
	
	public Command makeAutoCommand (Position p, Scoring s, String gameData) {		
		AutoState state = new AutoState(p, s, gameData);
		
		return map.getOrDefault(state, new DoNothing());
	}
	
	public void test() {
		if(makeAutoCommand(Position.CENTER, Scoring.AUTO_LEFT, "WW") instanceof DoNothing) {
			System.out.println("Default works.");
		}
		
		for(Position p : Position.values()) {
			for(Scoring s : Scoring.values()) {
				for(String gameData : gameDataOptions) {
					makeAutoCommand(p, s, gameData);
					System.out.println("Position " + p.name() + ", Strategy " + s.name() + ", Game Data " + gameData + " works.");
				}
			}
		}
	}
}
