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
import org.team2399.robot.commands.comboAutoGroups.ComboLeftOwnedOwned;
import org.team2399.robot.commands.comboAutoGroups.ComboLeftOwnedUnowned;
import org.team2399.robot.commands.comboAutoGroups.ComboLeftUnownedOwned;
import org.team2399.robot.commands.comboAutoGroups.ComboLeftUnownedUnowned;
import org.team2399.robot.commands.comboAutoGroups.ComboRightOwnedOwned;
import org.team2399.robot.commands.comboAutoGroups.ComboRightOwnedUnowned;
import org.team2399.robot.commands.comboAutoGroups.ComboRightUnownedOwned;
import org.team2399.robot.commands.comboAutoGroups.ComboRightUnownedUnowned;
import org.team2399.robot.commands.doubleAutoGroups.DoubleLeftOwnedScale;
import org.team2399.robot.commands.doubleAutoGroups.DoubleLeftOwnedSwitch;
import org.team2399.robot.commands.doubleAutoGroups.DoubleLeftUnownedScale;
import org.team2399.robot.commands.doubleAutoGroups.DoubleLeftUnownedSwitch;
import org.team2399.robot.commands.doubleAutoGroups.DoubleRightOwnedScale;
import org.team2399.robot.commands.doubleAutoGroups.DoubleRightOwnedSwitch;
import org.team2399.robot.commands.doubleAutoGroups.DoubleRightUnownedScale;
import org.team2399.robot.commands.doubleAutoGroups.DoubleRightUnownedSwitch;
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
	public static enum NumberCubes{SINGLE, DOUBLE, COMBO}
	private String[] gameDataOptions = {"LL", "LR", "RL", "RR"};
	
	private Map<AutoState, Command> map;
	
	public AutoChooser(DriveTrain dt, Shifter sh, AHRS navx, Lift li, Intake in) {		
		
		map = new HashMap<>();
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.SINGLE, "LL"), new LeftOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.SINGLE, "LR"), new LeftOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.SINGLE, "RL"), new LeftUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.SINGLE, "RR"), new LeftUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.SINGLE, "LL"), new LeftOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.SINGLE, "LR"), new LeftUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.SINGLE, "RL"), new LeftOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.SINGLE, "RR"), new LeftUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "LL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "LR"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "RL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "RR"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "LL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "LR"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "RL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "RR"), new LeftAuto(dt, sh, navx, li, in));
		
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.SINGLE, "LL"), new RightUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.SINGLE, "LR"), new RightUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.SINGLE, "RL"), new RightOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.SINGLE, "RR"), new RightOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.SINGLE, "LL"), new RightUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.SINGLE, "LR"), new RightOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.SINGLE, "RL"), new RightUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.SINGLE, "RR"), new RightOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "LL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "LR"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "RL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "RR"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "LL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "LR"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "RL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "RR"), new RightAuto(dt, sh, navx, li, in));
		
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.SINGLE, "LL"), new CenterLeftSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.SINGLE, "LR"), new CenterLeftSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.SINGLE, "RL"), new CenterRightSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.SINGLE, "RR"), new CenterRightSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.SINGLE, "LL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.SINGLE, "LR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.SINGLE, "RL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.SINGLE, "RR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "LL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "LR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "RL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "RR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "LL"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "LR"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "RL"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.SINGLE, "RR"), new CenterRightAuto(dt, sh, navx, li, in));
		
		// -------------------------------------------------------------------------------------------------------------------------------------
		
		
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.DOUBLE, "LL"), new DoubleLeftOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.DOUBLE, "LR"), new DoubleLeftOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.DOUBLE, "RL"), new DoubleLeftUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.DOUBLE, "RR"), new DoubleLeftUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.DOUBLE, "LL"), new DoubleLeftOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.DOUBLE, "LR"), new DoubleLeftUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.DOUBLE, "RL"), new DoubleLeftOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.DOUBLE, "RR"), new DoubleLeftUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "LL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "LR"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "RL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "RR"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "LL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "LR"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "RL"), new LeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "RR"), new LeftAuto(dt, sh, navx, li, in));
		
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.DOUBLE, "LL"), new DoubleRightUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.DOUBLE, "LR"), new DoubleRightUnownedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.DOUBLE, "RL"), new DoubleRightOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.DOUBLE, "RR"), new DoubleRightOwnedSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.DOUBLE, "LL"), new DoubleRightUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.DOUBLE, "LR"), new DoubleRightOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.DOUBLE, "RL"), new DoubleRightUnownedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.DOUBLE, "RR"), new DoubleRightOwnedScale(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "LL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "LR"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "RL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "RR"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "LL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "LR"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "RL"), new RightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "RR"), new RightAuto(dt, sh, navx, li, in));
		
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.DOUBLE, "LL"), new CenterLeftSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.DOUBLE, "LR"), new CenterLeftSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.DOUBLE, "RL"), new CenterRightSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.DOUBLE, "RR"), new CenterRightSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.DOUBLE, "LL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.DOUBLE, "LR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.DOUBLE, "RL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.DOUBLE, "RR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "LL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "LR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "RL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.DOUBLE, "RR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "LL"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "LR"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "RL"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.DOUBLE, "RR"), new CenterRightAuto(dt, sh, navx, li, in));
		
		// ---------------------------------------------------------------------------------------------------------------------------------------
		
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.COMBO, "LL"), new ComboLeftOwnedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.COMBO, "LR"), new ComboLeftOwnedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.COMBO, "RL"), new ComboLeftUnownedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SWITCH, NumberCubes.COMBO, "RR"), new ComboLeftUnownedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.COMBO, "LL"), new ComboLeftOwnedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.COMBO, "LR"), new ComboLeftOwnedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.COMBO, "RL"), new ComboLeftUnownedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.SCALE, NumberCubes.COMBO, "RR"), new ComboLeftUnownedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.COMBO, "LL"), new ComboLeftOwnedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.COMBO, "LR"), new ComboLeftOwnedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.COMBO, "RL"), new ComboLeftUnownedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_LEFT, NumberCubes.COMBO, "RR"), new ComboLeftUnownedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "LL"), new ComboLeftOwnedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "LR"), new ComboLeftOwnedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "RL"), new ComboLeftUnownedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.LEFT, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "RR"), new ComboLeftUnownedUnowned(dt, sh, navx, li, in));
		
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.COMBO, "LL"), new ComboRightUnownedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.COMBO, "LR"), new ComboRightUnownedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.COMBO, "RL"), new ComboRightOwnedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SWITCH, NumberCubes.COMBO, "RR"), new ComboRightOwnedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.COMBO, "LL"), new ComboRightUnownedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.COMBO, "LR"), new ComboRightUnownedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.COMBO, "RL"), new ComboRightOwnedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.SCALE, NumberCubes.COMBO, "RR"), new ComboRightOwnedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.COMBO, "LL"), new ComboRightUnownedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.COMBO, "LR"), new ComboRightUnownedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.COMBO, "RL"), new ComboRightOwnedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_LEFT, NumberCubes.COMBO, "RR"), new ComboRightOwnedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "LL"), new ComboRightUnownedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "LR"), new ComboRightUnownedOwned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "RL"), new ComboRightOwnedUnowned(dt, sh, navx, li, in));
		map.put(new AutoState(Position.RIGHT, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "RR"), new ComboRightOwnedOwned(dt, sh, navx, li, in));
		
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.COMBO, "LL"), new CenterLeftSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.COMBO, "LR"), new CenterLeftSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.COMBO, "RL"), new CenterRightSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SWITCH, NumberCubes.COMBO, "RR"), new CenterRightSwitch(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.COMBO, "LL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.COMBO, "LR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.COMBO, "RL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.SCALE, NumberCubes.COMBO, "RR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.COMBO, "LL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.COMBO, "LR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.COMBO, "RL"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.COMBO, "RR"), new CenterLeftAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "LL"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "LR"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "RL"), new CenterRightAuto(dt, sh, navx, li, in));
		map.put(new AutoState(Position.CENTER, Scoring.AUTO_RIGHT, NumberCubes.COMBO, "RR"), new CenterRightAuto(dt, sh, navx, li, in));
	}
	
	public static class AutoState {
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((gameData == null) ? 0 : gameData.hashCode());
			result = prime * result + ((num == null) ? 0 : num.hashCode());
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
			if (num != other.num)
				return false;
			if (pos != other.pos)
				return false;
			if (score != other.score)
				return false;
			return true;
		}
		public final Position pos;
		public final Scoring score;
		public final NumberCubes num;
		public final String gameData;
		public AutoState (Position pos, Scoring score, NumberCubes num, String gameData) {
			this.pos = pos;
			this.score = score;
			this.num = num;
			this.gameData = gameData;
		}
		
	}
	
	public Command makeAutoCommand (Position p, Scoring s, NumberCubes n) {		
		String gameData = DriverStation.getInstance().getGameSpecificMessage().substring(0,2);
		return makeAutoCommand(p, s, n, gameData);
	}
	
	public Command makeAutoCommand (Position p, Scoring s, NumberCubes n, String gameData) {		
		AutoState state = new AutoState(p, s, n, gameData);
		
		return map.getOrDefault(state, new DoNothing());
	}
	
	public void test() {
		if(makeAutoCommand(Position.CENTER, Scoring.AUTO_LEFT, NumberCubes.SINGLE, "WW") instanceof DoNothing) {
			System.out.println("Default works.");
		}
		
		for(Position p : Position.values()) {
			for(Scoring s : Scoring.values()) {
				for(NumberCubes n : NumberCubes.values()) {
					for(String gameData : gameDataOptions) {
						makeAutoCommand(p, s, n, gameData);
						System.out.println("Position " + p.name() + ", Strategy " + s.name() + ", Number " + n.name() + ", Game Data " + gameData + " works.");
					}
				}
			}
		}
	}
}
