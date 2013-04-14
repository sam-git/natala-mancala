package gameType;

import board.GameModel;

public class KalahBoard implements GameRules {
	
	private final static int STARTING_SEEDS_PER_HOUSE = 4;
	private final static int HOUSES_PER_PLAYER = 6;
	private final static int STARTING_SEEDS_PER_STORE = 0;
	private final static int STARTING_PLAYER = 1;
	
	private final String playerOneName = "Player 1";
	private final String playerTwoName = "Player 2";
	
	@Override
	public String[] toStringArray(GameModel m) {
		String[] lines = new String[5];
		lines[0] = "+----+-------+-------+-------+-------+-------+-------+----+";
		lines[1] = String
				.format("| P2 | 6[%2d] | 5[%2d] | 4[%2d] | 3[%2d] | 2[%2d] | 1[%2d] | %2d |",
						m.getSeedCount(2, 6), m.getSeedCount(2, 5),
						m.getSeedCount(2, 4), m.getSeedCount(2, 3),
						m.getSeedCount(2, 2), m.getSeedCount(2, 1),
						m.getStoreSeedCount(1));
		lines[2] = "|    |-------+-------+-------+-------+-------+-------|    |";
		lines[3] = String
				.format("| %2d | 1[%2d] | 2[%2d] | 3[%2d] | 4[%2d] | 5[%2d] | 6[%2d] | P1 |",
						m.getStoreSeedCount(2), m.getSeedCount(1, 1),
						m.getSeedCount(1, 2), m.getSeedCount(1, 3),
						m.getSeedCount(1, 4), m.getSeedCount(1, 5),
						m.getSeedCount(1, 6));
		lines[4] = "+----+-------+-------+-------+-------+-------+-------+----+";
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
