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
	private final Map<Integer, String> intToName;
	private final ModelController controller;

	public GameModel(String gameRules) {
		Properties props = ModelInitialiser.createProperties(gameRules);
		this.controller = new ModelController(props);
		this.intToName = ModelInitialiser.createNameMap(props);

		this.HOUSES_PER_PLAYER = PropsLoader.getInt(props, "housesPerPlayer");
		this.PLAY_CLOCKWISE = PropsLoader.getBool(props, "playClockwise");
	}

	/**
	 * returns whether or not the game is over
	 * 
	 * @return
	 */
	public boolean isGameOver() {
		return controller.isGameOver();
	}

	public String getCurrentPlayerName() {
		return intToName.get(controller.getCurrentPlayer());
	}
	
	public int getSeedCount(int player, int house) {
		return controller.getSeedCount(player, house);
	}

	public int getStoreSeedCount(int player) {
		return controller.getStoreSeedCount(player);
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
		controller.setGameOver();
		setChanged();
		notifyObservers(EventStrategyFactory.gameQuitStrategy(controller.getCurrentPlayer()));
	}
	
	public void undo() {
		controller.undo();
		setChanged();
		notifyObservers(EventStrategyFactory.undoMoveStrategy());
	}
	
	public void redo() {
		controller.redo();
	}
}