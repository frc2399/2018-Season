package org.team2399.robot;

import java.util.HashMap;
import java.util.Map;

import org.team2399.robot.commands.auto.CenterAuto;
import org.team2399.robot.commands.auto.CenterLeftSwitch;
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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class AutoChooser {
	public static enum Position{Left,Right,Center;}
	public static enum Scoring{Switch,Scale,Auto;}
	
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
			result = prime * result + getOuterType().hashCode();
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
			if (!getOuterType().equals(other.getOuterType()))
				return false;
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
		private AutoChooser getOuterType() {
			return AutoChooser.this;
		}
	
	}
	
	public static Command makeAutoCommand (Position p, Scoring s) {
		
		Map<AutoState, Command> map = new HashMap<>();
		map.put(new AutoState(Position.Left, Scoring.Switch, "LL"), new LeftOwnedSwitch());
		map.put(new AutoState(Position.Left, Scoring.Switch, "LR"), new LeftOwnedSwitch());
		map.put(new AutoState(Position.Left, Scoring.Switch, "RL"), new LeftUnownedSwitch());
		map.put(new AutoState(Position.Left, Scoring.Switch, "RR"), new LeftUnownedSwitch());
		map.put(new AutoState(Position.Left, Scoring.Scale, "LL"), new LeftOwnedScale());
		map.put(new AutoState(Position.Left, Scoring.Scale, "LR"), new LeftUnownedScale());
		map.put(new AutoState(Position.Left, Scoring.Scale, "RL"), new LeftOwnedScale());
		map.put(new AutoState(Position.Left, Scoring.Scale, "RR"), new LeftUnownedScale());
		map.put(new AutoState(Position.Left, Scoring.Auto, "LL"), new LeftAuto());
		map.put(new AutoState(Position.Left, Scoring.Auto, "LR"), new LeftAuto());
		map.put(new AutoState(Position.Left, Scoring.Auto, "RL"), new LeftAuto());
		map.put(new AutoState(Position.Left, Scoring.Auto, "RR"), new LeftAuto());
		
		map.put(new AutoState(Position.Right, Scoring.Switch, "LL"), new RightUnownedSwitch());
		map.put(new AutoState(Position.Right, Scoring.Switch, "LR"), new RightUnownedSwitch());
		map.put(new AutoState(Position.Right, Scoring.Switch, "RL"), new RightOwnedSwitch());
		map.put(new AutoState(Position.Right, Scoring.Switch, "RR"), new RightOwnedSwitch());
		map.put(new AutoState(Position.Right, Scoring.Scale, "LL"), new RightUnownedScale());
		map.put(new AutoState(Position.Right, Scoring.Scale, "LR"), new RightOwnedScale());
		map.put(new AutoState(Position.Right, Scoring.Scale, "RL"), new RightUnownedScale());
		map.put(new AutoState(Position.Right, Scoring.Scale, "RR"), new RightOwnedScale());
		map.put(new AutoState(Position.Right, Scoring.Auto, "LL"), new RightAuto());
		map.put(new AutoState(Position.Right, Scoring.Auto, "LR"), new RightAuto());
		map.put(new AutoState(Position.Right, Scoring.Auto, "RL"), new RightAuto());
		map.put(new AutoState(Position.Right, Scoring.Auto, "RR"), new RightAuto());
		
		map.put(new AutoState(Position.Center, Scoring.Switch, "LL"), new CenterLeftSwitch());
		map.put(new AutoState(Position.Center, Scoring.Switch, "LR"), new CenterLeftSwitch());
		map.put(new AutoState(Position.Center, Scoring.Switch, "RL"), new CenterRightSwitch());
		map.put(new AutoState(Position.Center, Scoring.Switch, "RR"), new CenterRightSwitch());
		map.put(new AutoState(Position.Center, Scoring.Scale, "LL"), new CenterAuto());
		map.put(new AutoState(Position.Center, Scoring.Scale, "LR"), new CenterAuto());
		map.put(new AutoState(Position.Center, Scoring.Scale, "RL"), new CenterAuto());
		map.put(new AutoState(Position.Center, Scoring.Scale, "RR"), new CenterAuto());
		map.put(new AutoState(Position.Center, Scoring.Auto, "LL"), new CenterAuto());
		map.put(new AutoState(Position.Center, Scoring.Auto, "LR"), new CenterAuto());
		map.put(new AutoState(Position.Center, Scoring.Auto, "RL"), new CenterAuto());
		map.put(new AutoState(Position.Center, Scoring.Auto, "RR"), new CenterAuto());
		
		
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage().substring(0,2);
		
		
		
		return new DoNothing();
	}
}
