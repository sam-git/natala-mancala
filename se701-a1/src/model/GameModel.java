package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import mancala.PropsLoader;
import model.abstractions.Player;
import model.event_strategy.EventStrategyFactory;
import model.event_strategy.IEventStrategy;

/**
 * The model for the Mancala game. Encapsulates all the game state and game
 * logic. Is Observable and notifies all observers of change in state by passing
 * them Strategy objects.
 * 
 * Creates a new game by creating the player objects and giving them info about their pits
 * Can be queried if game is over by controller
 * can be queried by controller for current player name
 * can be queried by views for seed counts of pits for players
 * can be queried by views for housesPerPlayer and isPlayClockwise for view setup.
 * can have actions quit, undo, redo, move called on it by userInputStrategy objects
 * can save itself to a mementoObject for undo, save.
 */
public class GameModel extends Observable {
	
	private final int HOUSES_PER_PLAYER;
	private final boolean PLAY_CLOCKWISE;

	final ModelUndoRedo undoRedo;
	final ModelController controller;

	Map<Integer, Player> intToPlayer;
	int current_player;
	boolean isGameOver;


	public GameModel(String gameRules) {
		Properties props = ModelProperties.createProperties(gameRules);		
		this.undoRedo = new ModelUndoRedo(this);
		this.controller = new ModelController(this);

		this.HOUSES_PER_PLAYER = PropsLoader.getInt(props, "housesPerPlayer");
		this.PLAY_CLOCKWISE = PropsLoader.getBool(props, "playClockwise");
		this.current_player = PropsLoader.getInt(props, "startingPlayer");
		this.isGameOver = false;
		
		createNewGame(props);
	}


	private void createNewGame(Properties props) {
		int numberOfPlayers = PropsLoader.getInt(props, "numberOfPlayers");
		this.intToPlayer = new HashMap<Integer, Player>(numberOfPlayers);
		
		int[] houses = new int[PropsLoader.getInt(props, "housesPerPlayer")];
		int startingSeedsPerHouse = PropsLoader.getInt(props, "startingSeedsPerHouse");
		int storeSeeds = PropsLoader.getInt(props, "startingSeedsPerStore");
		
		for (int i = 0; i < houses.length; i++) {
			houses[i] = startingSeedsPerHouse;
		}
		for (int playerNumber = 1; playerNumber <= numberOfPlayers; playerNumber++) {
			String name = props.getProperty(playerNumber + "Name");
			createPlayer(playerNumber, houses, storeSeeds, name);
		}
		Player.joinPlayers(intToPlayer.values());
		
	}

	void createPlayer(int playerNumber, int[] houses, int storeSeeds, String name) {
		Player player = new Player(houses, storeSeeds, name);
		this.intToPlayer.put(playerNumber, player);	
	}

	/**
	 * returns whether or not the game is over
	 * 
	 * @return
	 */
	public boolean isGameOver() {
		return this.isGameOver;
	}

	public String getCurrentPlayerName() {
		return intToPlayer.get(current_player).getName();
	}

	/**
	 * returns the number of seeds in a players house.
	 * 
	 * @param player
	 * @param house
	 * @return
	 */
	public int getSeedCount(int player, int house) {
		return intToPlayer.get(player).getSeedCount(house);
	}

	public int getStoreSeedCount(int player) {
		return intToPlayer.get(player).getStoreSeedCount();
	}

	public int getHousesPerPlayer() {
		return this.HOUSES_PER_PLAYER;
	}

	public boolean isPlayClockwise() {
		return this.PLAY_CLOCKWISE;
	}
	
	public void startGame() {
		setChanged();
		notifyObservers(EventStrategyFactory.gameStartStrategy());
	}


	public void move(int house) {
		IEventStrategy strategy = controller.move(house);
		setChanged();
		notifyObservers(strategy);
	}

	/**
	 * notify observers that the game was quit before ending
	 */
	public void quit() {
		this.isGameOver = true;
		setChanged();
		notifyObservers(EventStrategyFactory.gameQuitStrategy(this.current_player));
	}
	
	public void undo() {
		undoRedo.undo();
		setChanged();
		notifyObservers(EventStrategyFactory.undoMoveStrategy());
	}
	
	public void redo() {
		int house = undoRedo.redo();
		controller.acceptableMove(house);
	}
}