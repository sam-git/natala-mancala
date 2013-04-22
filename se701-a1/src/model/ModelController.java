package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import mancala.PropsLoader;
import model.abstractions.Player;
import model.event_strategy.CompositeEventStrategy;
import model.event_strategy.EventStrategyFactory;
import model.event_strategy.IEventStrategy;

public class ModelController {
	
	private final int HOUSES_PER_PLAYER;
	private final ModelUndoRedo undoRedo;

	private Map<Integer, Player> intToPlayer;
	private int currentPlayer;
	private boolean isGameOver;
	
	public ModelController(Properties props) {
		this.isGameOver = false;
		this.undoRedo = new ModelUndoRedo();
		this.currentPlayer = PropsLoader.getInt(props, "startingPlayer");
		this.HOUSES_PER_PLAYER = PropsLoader.getInt(props, "housesPerPlayer");
		intToPlayer = ModelInitialiser.createNewGame(props);
	}

	/**
	 * Makes a move for the current player. Updates houses and sets the current
	 * player at the end of the move. Notifies Observers with an EventStrategy.
	 * 
	 * @param house
	 * @return
	 */
	public IEventStrategy move(int house) {
		assert (!this.isGameOver());
		
		if (isHouseOutOfRange(house)) {
			return EventStrategyFactory.invalidHouseStrategy(house);
		}
		else if (isHouseEmpty(house)) {
			return EventStrategyFactory.houseEmptyStrategy();
		}
		undoRedo.clearRedos();
		IEventStrategy event = acceptableMove(house);
		return event;
	}

	
	private IEventStrategy acceptableMove(int house) {
		undoRedo.saveState(house, currentPlayer, intToPlayer);
		CompositeEventStrategy events = new CompositeEventStrategy();
		
		boolean moveEndedOnOwnStore = intToPlayer.get(currentPlayer).move(house);
	
		if (!moveEndedOnOwnStore) {
			switchPlayer();
		}
		events.add(EventStrategyFactory.moveEndedStrategy());

		if (isGameOver = hasGameEnded()) {
			events.add(EventStrategyFactory.gameEndedStrategy(getFinalScores()));
		}
		return events;
	}

	private boolean isHouseOutOfRange(int house) {
		return (house < 1 || house > HOUSES_PER_PLAYER);
	}

	private boolean isHouseEmpty(int house) {
		return intToPlayer.get(currentPlayer).getSeedCount(house) == 0;
	}

	private void switchPlayer() {
		currentPlayer = (currentPlayer % intToPlayer.size()) + 1;
	}

	/**
	 * return true if the game over conditions are met
	 */
	private boolean hasGameEnded() {
		boolean answer = false;
		for (Player p : intToPlayer.values()) {
			if (p.getTotalSeedsInHouses() == 0) {
				answer = true;
				break;
			}
		}
		return answer;
	}

	/**
	 * returns the scores of all player at the end of a game.
	 */
	private Map<Integer, Integer> getFinalScores() {
		assert (this.isGameOver());
		Map<Integer, Integer> playerToScore = new HashMap<Integer, Integer>(intToPlayer.size());
		for (Map.Entry<Integer, Player> intAndPlayer : intToPlayer.entrySet()) {
			playerToScore.put(intAndPlayer.getKey(), intAndPlayer.getValue().getScore());
		}
		return playerToScore;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver() {
		this.isGameOver = true;
	}
	
	public void undo() {
		undoRedo.undo(this);
	}
	
	public void redo() {
		int house = undoRedo.popRedoMove();
		acceptableMove(house);
	}
	
	public void restore(int currentPlayer, Map<Integer, Player> intToPlayer){
		this.currentPlayer = currentPlayer;
		this.intToPlayer = intToPlayer;
	}
	
	public int getStoreSeedCount(int player) {
		return intToPlayer.get(player).getStoreSeedCount();
	}
	
	public int getSeedCount(int player, int house) {
		return intToPlayer.get(player).getSeedCount(house);
	}
	
	public int getCurrentPlayer() {
		return this.currentPlayer;
	}
}