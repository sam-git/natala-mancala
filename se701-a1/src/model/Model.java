package model;

import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import mancala.PropsLoader;
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
public class Model extends Observable {
	
	private final int HOUSES_PER_PLAYER;
	private final boolean PLAY_CLOCKWISE;
	private final Map<Integer, String> intToName;
	private final ModelLogic gameLogic;

	public Model(String gameRules) {
		Properties props = ModelInitialiser.createProperties(gameRules);
		this.gameLogic = new ModelLogic(props);
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
		return gameLogic.isGameOver();
	}

	public String getCurrentPlayerName() {
		return intToName.get(gameLogic.getCurrentPlayer());
	}
	
	public int getSeedCount(int player, int house) {
		return gameLogic.getSeedCount(player, house);
	}

	public int getStoreSeedCount(int player) {
		return gameLogic.getStoreSeedCount(player);
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
		IEventStrategy strategy;
		if (isHouseOutOfRange(house)) {
			strategy = (EventStrategyFactory.invalidHouseStrategy(house));
		} else {
			strategy = gameLogic.move(house);
		}
		setChanged();
		notifyObservers(strategy);
	}
	
	private boolean isHouseOutOfRange(int house) {
		return (house < 1 || house > HOUSES_PER_PLAYER);
	}

	/**
	 * notify observers that the game was quit before ending
	 */
	public void quit() {
		gameLogic.setGameOver();
		setChanged();
		notifyObservers(EventStrategyFactory.gameQuitStrategy(gameLogic.getCurrentPlayer()));
	}
	
	public void undo() {
		gameLogic.undo();
		setChanged();
		notifyObservers(EventStrategyFactory.undoMoveStrategy());
	}
	
	public void redo() {
		gameLogic.redo();
	}
}