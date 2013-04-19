package model;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import model.event_strategy.EventStrategyFactory;


/**
 * The model for the Mancala game. Encapsulates all the game state and game logic.
 * Is Observable and notifies all observers of change in state by passing them Strategy objects.
 */
public class GameModel extends Observable {
	
	public static final String gamePropsFolder = "gameProperties/";
	
	private final int HOUSES_PER_PLAYER;
	private int current_player;
	private boolean isGameOver;
	private Map<Integer, Player> intToPlayer;
	private final Properties props = new Properties();
	
	public GameModel(String gameRules) {
		try {
			loadRules(gameRules);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.current_player = Integer.valueOf(props.getProperty("startingPlayer"));// rules.getStartingPlayer();
		this.isGameOver = false;
		this.intToPlayer = new HashMap<Integer, Player>();
		
		Player p1 = new Player(props, props.getProperty("playerOneName"));
		Player p2 = new Player(props, props.getProperty("playerTwoName"));
		Player.join(p1,p2);
		
		this.intToPlayer.put(1, p1);
		this.intToPlayer.put(2, p2);
		
		this.HOUSES_PER_PLAYER = Integer.valueOf(props.getProperty("housesPerPlayer"));
	}
	
	private void loadRules(String gameRules) throws IOException{
		// create and load default properties
		FileInputStream in = new FileInputStream(gamePropsFolder + "Default.properties");
		props.load(in);
		in.close();
		// now load properties for this game
		in = new FileInputStream(gamePropsFolder + gameRules);
		props.load(in);
		in.close();
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
	private boolean isHouseEmpty(int house) {
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
		return this.HOUSES_PER_PLAYER;
	}

	/**
	 * returns the score of a player at the end of a game.
	 */
	public int[] getFinalScores() {
		if (!isGameOver()) {
			throw new RuntimeException("Can't get final score until game is over!");
		}
		int[] scores = new int[intToPlayer.size()];
		for (Map.Entry<Integer, Player> entry : intToPlayer.entrySet()) {
			scores[entry.getKey()-1] = entry.getValue().getScore();
		}
		return scores;
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
			notifyObservers(EventStrategyFactory.gameEndedStrategy(getFinalScores()));
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