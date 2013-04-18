package model;


import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import model.event_strategy.EventStrategyFactory;

import rules.IGameRules;




/**
 * The model for the Mancala game. Encapsulates all the game state and game logic.
 * Is Observable and notifies all observers of change in state by passing them Strategy objects.
 */
public class GameModel extends Observable {
	
	private final int HOUSES_PER_PLAYER;
	private int current_player;
	private boolean isGameOver;
	private Map<Integer, Player> intToPlayer;
	
	public GameModel(IGameRules rules) {
		this.current_player = rules.getStartingPlayer();
		this.isGameOver = false;
		this.intToPlayer = new HashMap<Integer, Player>();
		
		Player p1 = new Player(rules, rules.getPlayerOneName());
		Player p2 = new Player(rules, rules.getPlayerTwoName());
		Player.join(p1,p2, rules.getHousesPerPlayer());
		
		this.intToPlayer.put(1, p1);
		this.intToPlayer.put(2, p2);
		
		this.HOUSES_PER_PLAYER = rules.getHousesPerPlayer();
	}

	
	/**
	 * returns whether or not the game is over
	 * @return
	 */
	public boolean isGameOver() {
		return this.isGameOver;
	}
	
	public String getCurrentPlayerName() {
		return intToPlayer.get(current_player).getName();
	}

	/**
	 * return true if one or more seeds are in the current players selected house.
	 * @param house
	 * @return
	 */
	public boolean isHouseEmpty(int house) {
		return intToPlayer.get(current_player).getSeedCount(house) == 0;
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
		return  this.HOUSES_PER_PLAYER;
	}

	/**
	 * returns the score of a player at the end of a game.
	 */
	public int getFinalScore(int player) {
		if (!isGameOver()) {
			throw new RuntimeException("Can't get final score until game is over!");
		}
		return intToPlayer.get(player).getScore();
	}

	/**
	 * Makes a move for the current player. Updates houses and sets the current
	 * player at the end of the move. Notifies Observers with a ViewStrategy.
	 * 
	 * @param house
	 * @return
	 */
	public void move(int house) {

		// if game has ended do nothing
		if (this.isGameOver) {
			return;
		} else if (isHouseEmpty(house)) {
			setChanged();
			notifyObservers(EventStrategyFactory.houseEmptyStrategy());
			return;
		}

		boolean endedOnOwnStore = intToPlayer.get(current_player).move(house);

		// check for land on own store and if not switch player
		if (!endedOnOwnStore) {
			this.current_player = (this.current_player % intToPlayer.size()) + 1;
		}
		
		//notify observers of end of move or end of game
		setChanged();
		if (this.isGameOver = hasGameEnded()) {
			notifyObservers(EventStrategyFactory.gameEndedStrategy());
		} else {
			notifyObservers(EventStrategyFactory.moveEndedStrategy());
		}
	}
	
	/**
	 * notify observers that the game was quit before ending
	 */
	public void quit() {
		this.isGameOver = true;
		setChanged();
		notifyObservers(EventStrategyFactory.gameQuitStrategy());
	}
	
	
	
//////////////////////////////

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
}