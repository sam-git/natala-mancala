package model;

import java.util.Properties;

import mancala.PropsLoader;

public class ModelProperties {
	
	public static final String gamePropsFolder = "gameProperties/";
	
	public static Properties createProperties(String gameRules) {
		Properties props = setDefaultProperties();	
		if (gameRules != null) {
			String customPropsLoc = gamePropsFolder + gameRules;
			PropsLoader.insertCustomProps(props, customPropsLoc);
		}
		return props;
	}

	private static Properties setDefaultProperties() {
		Properties props = new Properties();
		props.setProperty("startingSeedsPerHouse", "4");
		props.setProperty("housesPerPlayer", "6");
		props.setProperty("startingSeedsPerStore", "0");
		props.setProperty("startingPlayer", "1");
		props.setProperty("numberOfPlayers", "2");
		props.setProperty("playClockwise", "false");
		props.setProperty("1Name", "Player 1");
		props.setProperty("2Name", "Player 2");
		return props;
	}
	

}
