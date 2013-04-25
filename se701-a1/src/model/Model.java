package model;

import java.util.Observable;
import java.util.Properties;

import mancala.PropsLoader;
import model.ModelHistory.GameMemento;
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
	
	public int HOUSES_PER_PLAYER;
	public boolean PLAY_CLOCKWISE;
	private ModelLogic gameLogic;
	private Properties props;

	public Model(String gameRules) {
		Properties props = ModelInitialiser.createProperties(gameRules);
		this.props = props;
		createGameFromProperties(props);
	}
	
	private Model(Properties props, GameMemento memento) {
		createGameFromProperties(props);
		gameLogic.restoreFromMemento(memento);
	}

	private void createGameFromProperties(Properties props) {
		this.gameLogic = new ModelLogic(props);
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

	public int currentPlayer() {
		return gameLogic.getCurrentPlayer();
	}
	
	public int getSeedCount(int player, int house) {
		return gameLogic.getSeedCount(player, house);
	}

	public int getStoreSeedCount(int player) {
		return gameLogic.getStoreSeedCount(player);
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
		boolean success = gameLogic.undo();
		setChanged();
		notifyObservers(EventStrategyFactory.undoMoveStrategy(success));
	}
	
	public void redo() {
		IEventStrategy event = gameLogic.redo();
		setChanged();
		notifyObservers(event);
	}
	
	public Model getCopy() {
		Model copy = new Model(this.props, gameLogic.getMemento());
		return copy;
	}
}