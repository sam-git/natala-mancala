package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import mancala.PropsLoader;
import model.abstractions.Player;

public class ModelInitialiser {
	
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
	
	public static Map<Integer, Player> createNewGame(Properties props) {
		int numberOfPlayers = PropsLoader.getInt(props, "numberOfPlayers");
		int startingSeedsPerHouse = PropsLoader.getInt(props, "startingSeedsPerHouse");
		int storeSeeds = PropsLoader.getInt(props, "startingSeedsPerStore");
		int housesPerPlayer = PropsLoader.getInt(props, "housesPerPlayer");
		
		int[] houses = new int[housesPerPlayer];
		setStartingSeedsInHouses(houses, startingSeedsPerHouse);
		
		Map<Integer, Player> intToPlayer = new HashMap<Integer, Player>(numberOfPlayers);	
		
		for (int playerNumber = 1; playerNumber <= numberOfPlayers; playerNumber++) {
			String name = props.getProperty(playerNumber + "Name");
			Player player = new Player(houses, storeSeeds, name);
			intToPlayer.put(playerNumber, player);	
		}
		Player.joinPlayers(intToPlayer.values());
		
		return intToPlayer;
	}

	private static int[] setStartingSeedsInHouses(int[] houses, int startingSeeds) {
		for (int i = 0; i < houses.length; i++) {
			houses[i] = startingSeeds;
		}
		return houses;
	}
	
	public static Map<Integer, String> createNameMap(Properties props) {
		int numberOfPlayers = PropsLoader.getInt(props, "numberOfPlayers");
		Map<Integer, String> intToNames = new HashMap<Integer, String>(numberOfPlayers);
		
		for (int playerNumber = 1; playerNumber <= numberOfPlayers; playerNumber++) {
			String name = props.getProperty(playerNumber + "Name");
			intToNames.put(playerNumber, name);
		}
		return intToNames;
	}
}
