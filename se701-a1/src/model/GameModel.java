package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import mancala.PropsLoader;
import model.event_strategy.EventStrategyFactory;

/**
 * The model for the Mancala game. Encapsulates all the game state and game
 * logic. Is Observable and notifies all observers of change in state by passing
 * them Strategy objects.
 */
public class GameModel extends Observable {

	private final Properties props;
	public static final String gamePropsFolder = "gameProperties/";
	public static final String defaultPropsFile = "Default.properties";

	private final int HOUSES_PER_PLAYER;
	private int current_player;
	private boolean isGameOver;
	private Map<Integer, Player> intToPlayer;

	public GameModel(String gameRules) {
		this.props = loadRules(gameRules);
		this.HOUSES_PER_PLAYER = PropsLoader.getInt(props, "housesPerPlayer");

		this.current_player = PropsLoader.getInt(props, "startingPlayer");
		this.isGameOver = false;
		
		int numberOfPlayers = PropsLoader.getInt(props, "numberOfPlayers");
		this.intToPlayer = new HashMap<Integer, Player>(numberOfPlayers);
		
		for (int i = 1; i <= numberOfPlayers; i++) {
			Player player = new Player(props, i);
			this.intToPlayer.put(i, player);
		}

		Player.joinPlayers(intToPlayer.values());

	}

	private static Properties loadRules(String gameRulesFile) {
		String defaultPropsLoc = gamePropsFolder + defaultPropsFile;
		String gamePropsLoc = gamePropsFolder + gameRulesFile;
		Properties props = PropsLoader.loadProps(defaultPropsLoc, gamePropsLoc);
		return props;
	}

	/**
	 * Makes a move for the current player. Updates houses and sets the current
	 * player at the end of the move. Notifies Observers with an EventStrategy.
	 * 
	 * @param house
	 * @return
	 */
	public void move(int house) {
		assert (!this.isGameOver);
		setChanged();
	
		if (isHouseEmpty(house)) {
			notifyObservers(EventStrategyFactory.houseEmptyStrategy());
			return;
		}
	
		// make move
		boolean moveEndedOnOwnStore = intToPlayer.get(current_player).move(
				house);
	
		if (!moveEndedOnOwnStore) {
			switchPlayer();
		}
	
		// notify observers of end of move or end of game
		if (this.isGameOver = hasGameEnded()) {
			notifyObservers(EventStrategyFactory
					.gameEndedStrategy(getFinalScores()));
		} else {
			notifyObservers(EventStrategyFactory.moveEndedStrategy());
		}
	}

	/**
	 * return true if one or more seeds are in the current players selected
	 * house.
	 * 
	 * @param house
	 * @return
	 */
	private boolean isHouseEmpty(int house) {
		return intToPlayer.get(current_player).getSeedCount(house) == 0;
	}

	private void switchPlayer() {
		this.current_player = (this.current_player % intToPlayer.size()) + 1;
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
		assert (this.isGameOver);
	
		Map<Integer, Integer> playerToScore = new HashMap<Integer, Integer>(intToPlayer.size());
		for (Map.Entry<Integer, Player> intAndPlayer : intToPlayer.entrySet()) {
			playerToScore.put(intAndPlayer.getKey(), intAndPlayer.getValue().getScore());
		}
		return playerToScore;
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
	
	public String getPlayerShortName(int player) {
		return intToPlayer.get(player).getShortName();
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

	/**
	 * notify observers that the game was quit before ending
	 */
	public void quit() {
		this.isGameOver = true;
		setChanged();
		notifyObservers(EventStrategyFactory.gameQuitStrategy(this.current_player));
	}
}