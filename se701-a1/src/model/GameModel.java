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

	public static final String gamePropsFolder = "gameProperties/";

	private final Map<Integer, Player> intToPlayer;
	private final int HOUSES_PER_PLAYER;


	private int current_player;
	private boolean isGameOver;

	public GameModel(String gameRules) {
		Properties props = createProperties(gameRules);
		
		this.HOUSES_PER_PLAYER = PropsLoader.getInt(props, "housesPerPlayer");
		this.current_player = PropsLoader.getInt(props, "startingPlayer");
		this.isGameOver = false;
		
		int numberOfPlayers = PropsLoader.getInt(props, "numberOfPlayers");
		this.intToPlayer = new HashMap<Integer, Player>(numberOfPlayers);
		createPlayers(props, numberOfPlayers);

		
	}

	private Properties createProperties(String gameRules) {
		Properties props = createDefaultProperties();	
		if (gameRules != null) {
			String customPropsLoc = gamePropsFolder + gameRules;
			PropsLoader.insertCustomProps(props, customPropsLoc);
		}
		return props;
	}

	private Properties createDefaultProperties() {
		Properties props = new Properties();
		props.setProperty("startingSeedsPerHouse", "4");
		props.setProperty("housesPerPlayer", "6");
		props.setProperty("startingSeedsPerStore", "0");
		props.setProperty("startingPlayer", "1");
		props.setProperty("numberOfPlayers", "2");
		props.setProperty("1Name", "Player 1");
		props.setProperty("1ShortName", "P1");
		props.setProperty("2Name", "Player 2");
		props.setProperty("2ShortName", "P2");
		return props;
	}
	
	private void createPlayers(Properties props, int numberOfPlayers) {		
		for (int i = 1; i <= numberOfPlayers; i++) {
			Player player = new Player(props, i);
			this.intToPlayer.put(i, player);
		}
	
		Player.joinPlayers(intToPlayer.values());
	}

	public void startGame() {
		setChanged();
		notifyObservers(EventStrategyFactory.gameStartStrategy());
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
		
		if (isHouseOutOfRange(house)) {
			notifyObservers(EventStrategyFactory.invalidHouseStrategy(house));
			return;
		}
		else if (isHouseEmpty(house)) {
			notifyObservers(EventStrategyFactory.houseEmptyStrategy());
			return;
		}
	
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

	private boolean isHouseOutOfRange(int house) {
		return (house < 1 || house > HOUSES_PER_PLAYER);
	}

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