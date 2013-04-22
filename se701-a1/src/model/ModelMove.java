package model;

import java.util.HashMap;
import java.util.Map;

import model.abstractions.Player;
import model.event_strategy.EventStrategyFactory;
import model.event_strategy.IEventStrategy;

public class ModelMove {
	
	private final GameModel model;
	
	public ModelMove(GameModel model) {
		this.model = model;
	}
	
	/**
	 * Makes a move for the current player. Updates houses and sets the current
	 * player at the end of the move. Notifies Observers with an EventStrategy.
	 * 
	 * @param house
	 * @return
	 */
	public IEventStrategy move(int house) {
		assert (!model.isGameOver());
		
		if (isHouseOutOfRange(house)) {
			return EventStrategyFactory.invalidHouseStrategy(house);
		}
		else if (isHouseEmpty(house)) {
			return EventStrategyFactory.houseEmptyStrategy();
		}
		
		model.undoRedo.clearRedos();
		
		IEventStrategy event = acceptableMove(house);
		return event;
	}

	
	IEventStrategy acceptableMove(int house) {
		model.undoRedo.saveUndoMemento(house);
		
		boolean moveEndedOnOwnStore = model.intToPlayer.get(model.current_player).move(house);
	
		if (!moveEndedOnOwnStore) {
			switchPlayer();
		}

		if (model.isGameOver = hasGameEnded()) {
			return EventStrategyFactory.gameEndedStrategy(getFinalScores());
		} else {
			return EventStrategyFactory.moveEndedStrategy();
		}
	}


	private boolean isHouseOutOfRange(int house) {
		return (house < 1 || house > model.getHousesPerPlayer());
	}

	private boolean isHouseEmpty(int house) {
		return model.intToPlayer.get(model.current_player).getSeedCount(house) == 0;
	}

	private void switchPlayer() {
		model.current_player = (model.current_player % model.intToPlayer.size()) + 1;
	}

	/**
	 * return true if the game over conditions are met
	 */
	private boolean hasGameEnded() {
		boolean answer = false;
		for (Player p : model.intToPlayer.values()) {
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
		assert (model.isGameOver());
	
		Map<Integer, Integer> playerToScore = new HashMap<Integer, Integer>(model.intToPlayer.size());
		for (Map.Entry<Integer, Player> intAndPlayer : model.intToPlayer.entrySet()) {
			playerToScore.put(intAndPlayer.getKey(), intAndPlayer.getValue().getScore());
		}
		return playerToScore;
	}

}
