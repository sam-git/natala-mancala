package rules;

import model.GameModel;

public class CrazyRules implements IGameRules {
	
	private final static int STARTING_SEEDS_PER_HOUSE = 99;
	private final static int HOUSES_PER_PLAYER = 3;
	private final static int STARTING_SEEDS_PER_STORE = 11;
	private final static int STARTING_PLAYER = 2;
	
	private final String playerOneName = "Melash 1";
	private final String playerTwoName = "Doomaa TWO";
	
	@Override
	public String[] toStringArray(GameModel m) {
		String[] lines = new String[5];
		lines[0] = "+-----+--------+--------+--------+-----+";
		lines[1] = String
				.format("| P2  | 3[%3d] | 2[%3d] | 1[%3d] | %3d |",
						 m.getSeedCount(2, 3),
						m.getSeedCount(2, 2), m.getSeedCount(2, 1),
						m.getStoreSeedCount(1));
		lines[2] = "|     |--------+--------+--------|     |";
		lines[3] = String
				.format("| %3d | 1[%3d] | 2[%3d] | 3[%3d] | P1  |",
						m.getStoreSeedCount(2), m.getSeedCount(1, 1),
						m.getSeedCount(1, 2), m.getSeedCount(1, 3));
		lines[4] = "+-----+--------+--------+--------+-----+";
		return lines;
	}
	
	@Override
	public int getStartingSeedsPerHouse() {
		return STARTING_SEEDS_PER_HOUSE;
	}

	public int getStartingSeedsPerStore() {
		return STARTING_SEEDS_PER_STORE;
	}

	@Override
	public int getHousesPerPlayer() {
		return HOUSES_PER_PLAYER;
	}

	public String getPlayerOneName() {
		return playerOneName;
	}

	public String getPlayerTwoName() {
		return playerTwoName;
	}

	@Override
	public int getStartingPlayer() {
		return STARTING_PLAYER;
	}
}
