package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import mancala.PropsLoader;
import model.ModelHistory.GameMemento;
import model.abstractions.Player;
import model.abstractions.Player.PlayerMemento;
import model.event_strategy.CompositeEventStrategy;
import model.event_strategy.EventStrategyFactory;
import model.event_strategy.IEventStrategy;

public class ModelLogic {
	private final ModelHistory modelHistory;

	private Map<Integer, Player> intToPlayer;
	private int currentPlayer;
	private boolean isGameOver;
	
	public ModelLogic(Properties props) {
		this.modelHistory = new ModelHistory();

		this.isGameOver = false;
		this.currentPlayer = PropsLoader.getInt(props, "startingPlayer");
		this.intToPlayer = ModelInitialiser.createNewGame(props);
	}

	/**
	 * Makes a move for the current player. Updates houses and sets the current
	 * player at the end of the move. Notifies Observers with an EventStrategy.
	 * 
	 * @param house
	 * @return
	 */
	public IEventStrategy move(int house) {
		if (isHouseEmpty(house)) {
			return EventStrategyFactory.houseEmptyStrategy(currentPlayer);
		}
		modelHistory.clearRedos();
		return acceptableMove(house);
	}

	
	private IEventStrategy acceptableMove(int house) {
		modelHistory.saveStateForUndo(house, currentPlayer, intToPlayer);
		CompositeEventStrategy events = new CompositeEventStrategy();	
		events.add(EventStrategyFactory.moveStartedStrategy(currentPlayer, house));
		
		boolean moveEndedOnOwnStore = intToPlayer.get(currentPlayer).move(house);
	
		if (!moveEndedOnOwnStore) {
			switchPlayer();
		}

		if (isGameOver = hasGameEnded()) {
			events.add(EventStrategyFactory.gameEndedStrategy(getFinalScores()));
		} else {
			events.add(EventStrategyFactory.moveEndedStrategy());
		}

		return events;
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

	public IEventStrategy redo() {
		int house = modelHistory.popRedoMove();
		if (house == -1) {
			return EventStrategyFactory.moveRedoneStrategy(false);
		} else {
			CompositeEventStrategy events = new CompositeEventStrategy();
			events.add(EventStrategyFactory.moveRedoneStrategy(true));
			events.add(acceptableMove(house));
			return events;
		}
	}

	public boolean undo() {
		return modelHistory.undo(this);
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver() {
		this.isGameOver = true;
	}
	
	void restoreFromMemento(GameMemento memento){
		int currentPlayer = memento.getCurrentPlayer();
		
		int numberOfPlayers = memento.getPlayers().length;
		Map<Integer, Player>intToPlayer = new HashMap<Integer, Player>(numberOfPlayers);

		for (PlayerMemento p : memento.getPlayers()) {
			int playerNumber = p.getNumber();
			int houses[] = p.getHouses();
			int storeSeeds = p.getStoreSeedCount();
			
			Player player = new Player(houses, storeSeeds);
			intToPlayer.put(playerNumber, player);	
		}
		Player.joinPlayers(intToPlayer.values());
		restore(currentPlayer, intToPlayer);
	}
	
	private void restore(int currentPlayer, Map<Integer, Player> intToPlayer){
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

	public GameMemento getMemento() {
		return new GameMemento(currentPlayer, intToPlayer);
	}
}
