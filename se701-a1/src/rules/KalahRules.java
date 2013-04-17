package rules;


public class KalahRules implements IGameRules {
	
	private final static int STARTING_SEEDS_PER_HOUSE = 4;
	private final static int HOUSES_PER_PLAYER = 6;
	private final static int STARTING_SEEDS_PER_STORE = 0;
	private final static int STARTING_PLAYER = 1;
	private final static String PLAYER_1_NAME = "Player 1";
	private final static String PLAYER_2_NAME = "Player 2";
	
	@Override
	public int getStartingSeedsPerHouse() {
		return STARTING_SEEDS_PER_HOUSE;
	}

	public int getStartingSeedsPerStore() {
		return STARTING_SEEDS_PER_STORE;
	}

	public int getHousesPerPlayer() {
		return HOUSES_PER_PLAYER;
	}

	public String getPlayerOneName() {
		return PLAYER_1_NAME;
	}

	public String getPlayerTwoName() {
		return PLAYER_2_NAME;
	}

	public int getStartingPlayer() {
		return STARTING_PLAYER;
	}
}
