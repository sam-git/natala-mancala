package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;
import java.util.Stack;

import mancala.PropsLoader;
import model.Player.PlayerMemento;
import model.event_strategy.EventStrategyFactory;

/**
 * The model for the Mancala game. Encapsulates all the game state and game
 * logic. Is Observable and notifies all observers of change in state by passing
 * them Strategy objects.
 */
public class GameModel extends Observable {

	public static final String gamePropsFolder = "gameProperties/";

	private Map<Integer, Player> intToPlayer;
	private final int HOUSES_PER_PLAYER;
	private final boolean PLAY_CLOCKWISE;
	
	private final Stack<GameMemento> mementosForUndo;
	private final Stack<Integer> intsForRedo;


	private int current_player;
	private boolean isGameOver;


	public GameModel(String gameRules) {
		Properties props = createProperties(gameRules);
		
		this.HOUSES_PER_PLAYER = PropsLoader.getInt(props, "housesPerPlayer");
		this.PLAY_CLOCKWISE = PropsLoader.getBool(props, "playClockwise");
		this.current_player = PropsLoader.getInt(props, "startingPlayer");
		this.isGameOver = false;
		
		this.mementosForUndo = new Stack<GameMemento>();
		this.intsForRedo = new Stack<Integer>();
		
		createNewGame(props);
		
	}

	private Properties createProperties(String gameRules) {
		Properties props = setDefaultProperties();	
		if (gameRules != null) {
			String customPropsLoc = gamePropsFolder + gameRules;
			PropsLoader.insertCustomProps(props, customPropsLoc);
		}
		return props;
	}

	private Properties setDefaultProperties() {
		Properties props = new Properties();
		props.setProperty("startingSeedsPerHouse", "4");
		props.setProperty("housesPerPlayer", "6");
		props.setProperty("startingSeedsPerStore", "0");
		props.setProperty("startingPlayer", "1");
		props.setProperty("numberOfPlayers", "2");
		props.setProperty("playClockwise", "false");
		props.setProperty("1Name", "Player 1");
		props.setProperty("2Name", "Player 2");
		return props;
	}
	
	private void createNewGame(Properties props) {
		int numberOfPlayers = PropsLoader.getInt(props, "numberOfPlayers");
		this.intToPlayer = new HashMap<Integer, Player>(numberOfPlayers);
		
		int[] houses = new int[PropsLoader.getInt(props, "housesPerPlayer")];
		int startingSeedsPerHouse = PropsLoader.getInt(props, "startingSeedsPerHouse");
		int storeSeeds = PropsLoader.getInt(props, "startingSeedsPerStore");
		
		for (int i = 0; i < houses.length; i++) {
			houses[i] = startingSeedsPerHouse;
		}
		for (int i = 1; i <= numberOfPlayers; i++) {
			String name = props.getProperty(i + "Name");
			createPlayer(i, houses, storeSeeds, name);
		}
		Player.joinPlayers(intToPlayer.values());
		
	}

	private void createPlayer(int playerNumber, int[] houses, int storeSeeds, String name) {
		Player player = new Player(houses, storeSeeds, name);
		this.intToPlayer.put(playerNumber, player);	
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
		
		if (!isHouseValidMove(house)) {
			return;
		}
		intsForRedo.clear(); //clear redos
		acceptableMove(house);
	}

	private void acceptableMove(int house) {
		setChanged();
		saveUndoMemento(house);
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

	private void saveUndoMemento(int house) {
		GameMemento memento = this.saveToMemento();
		memento.setNextMove(house);
		mementosForUndo.add(memento);
	}

	private boolean isHouseValidMove(int house) {
		boolean validMove = false;
		if (isHouseOutOfRange(house)) {
			setChanged();
			notifyObservers(EventStrategyFactory.invalidHouseStrategy(house));
		}
		else if (isHouseEmpty(house)) {
			setChanged();
			notifyObservers(EventStrategyFactory.houseEmptyStrategy());
		} else {
			validMove = true;
		}
		return validMove;
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

	public boolean isPlayClockwise() {
		return this.PLAY_CLOCKWISE;
	}

	/**
	 * notify observers that the game was quit before ending
	 */
	public void quit() {
		this.isGameOver = true;
		setChanged();
		notifyObservers(EventStrategyFactory.gameQuitStrategy(this.current_player));
	}
	
	public void undo() {
		GameMemento newState = mementosForUndo.pop();
		restoreFromMemento(newState);
		intsForRedo.add(newState.getNextMove());
		setChanged();
		notifyObservers(EventStrategyFactory.undoMoveStrategy());
	}
	
	public void redo() {
		acceptableMove(intsForRedo.pop());
	}
	
	public void restoreFromMemento(GameMemento memento){
		this.current_player = memento.getCurrentPlayer();

		int numberOfPlayers = memento.getPlayers().length;
		this.intToPlayer = new HashMap<Integer, Player>(numberOfPlayers);

		for (PlayerMemento p : memento.getPlayers()) {
			String name = p.getName();
			int playerNumber = p.getNumber();
			int houses[] = p.getHouses();
			int storeSeeds = p.getStoreSeedCount();
			createPlayer(playerNumber, houses, storeSeeds, name);
		}

		Player.joinPlayers(intToPlayer.values());
	}
	
	public GameMemento saveToMemento() {
		return new GameMemento(this.current_player, this.intToPlayer);
	}
	
	public static class GameMemento {
		private final int currentPlayer;
		private PlayerMemento players[];
		private int nextMove;
 
        public GameMemento(int currentPlayer, Map<Integer, Player> intToPlayer) {
            this.currentPlayer = currentPlayer;
            this.players = new PlayerMemento[intToPlayer.size()];
            
            for (Map.Entry<Integer, Player> entry : intToPlayer.entrySet()) {
            	PlayerMemento playerMemento = entry.getValue().saveToMemento();
            	playerMemento.setPlayerNumber(entry.getKey());
            	players[entry.getKey() - 1] = playerMemento;
    		}
        }
 
        public PlayerMemento[] getPlayers() {
            return players;
        }

		public int getCurrentPlayer() {
			return currentPlayer;
		}

		public int getNextMove() {
			return nextMove;
		}

		public void setNextMove(int nextMove) {
			this.nextMove = nextMove;
		}
    }
}