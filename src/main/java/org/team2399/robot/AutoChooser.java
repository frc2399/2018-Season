package org.team2399.robot;

import java.util.HashMap;
import java.util.Map;

import org.team2399.robot.commands.autoGroups.CenterLeftAuto;
import org.team2399.robot.commands.autoGroups.CenterLeftSwitch;
import org.team2399.robot.commands.autoGroups.CenterRightAuto;
import org.team2399.robot.commands.autoGroups.CenterRightSwitch;
import org.team2399.robot.commands.autoGroups.DoNothing;
import org.team2399.robot.commands.autoGroups.LeftAuto;
import org.team2399.robot.commands.autoGroups.LeftOwnedScale;
import org.team2399.robot.commands.autoGroups.LeftOwnedSwitch;
import org.team2399.robot.commands.autoGroups.LeftUnownedScale;
import org.team2399.robot.commands.autoGroups.LeftUnownedSwitch;
import org.team2399.robot.commands.autoGroups.RightAuto;
import org.team2399.robot.commands.autoGroups.RightOwnedScale;
import org.team2399.robot.commands.autoGroups.RightOwnedSwitch;
import org.team2399.robot.commands.autoGroups.RightUnownedScale;
import org.team2399.robot.commands.autoGroups.RightUnownedSwitch;
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
	
	public AutoChooser(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {		
		
		map = new HashMap<>();
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, "LL"), new LeftOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, "LR"), new LeftOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, "RL"), new LeftUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, "RR"), new LeftUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, "LL"), new LeftOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, "LR"), new LeftUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, "RL"), new LeftOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, "RR"), new LeftUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, "LL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, "LR"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, "RL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, "RR"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, "LL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, "LR"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, "RL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, "RR"), new LeftAuto(dt, sh, navx, li, in));
		
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, "LL"), new RightUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, "LR"), new RightUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, "RL"), new RightOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, "RR"), new RightOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, "LL"), new RightUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, "LR"), new RightOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, "RL"), new RightUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, "RR"), new RightOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, "LL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, "LR"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, "RL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, "RR"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, "LL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, "LR"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, "RL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, "RR"), new RightAuto(dt, sh, navx, li, in));
		
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, "LL"), new CenterLeftSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, "LR"), new CenterLeftSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, "RL"), new CenterRightSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, "RR"), new CenterRightSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, "LL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, "LR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, "RL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, "RR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, "LL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, "LR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, "RL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, "RR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, "LL"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, "LR"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, "RL"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, "RR"), new CenterRightAuto(dt, sh, navx, li, in));

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
