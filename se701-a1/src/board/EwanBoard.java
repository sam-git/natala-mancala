package board;

import java.util.Observer;

public class EwanBoard implements IBoard {
	private final static int STORE = 0;
	private final BoardState state;

	public EwanBoard () {
		this.state = new BoardState();
	}
	
	@Override
	public void addObserver(Observer o) {
		state.addObserver(o);
	}
	
	@Override
	public String[] toStringArray() {
		String[] lines = new String[5];
		lines[0] = "+----+-------+-------+-------+-------+-------+-------+----+";
		lines[1] = String
				.format("| P2 | 6[%2d] | 5[%2d] | 4[%2d] | 3[%2d] | 2[%2d] | 1[%2d] | %2d |",
						state.getSeedCount(2, 6), state.getSeedCount(2, 5),
						state.getSeedCount(2, 4), state.getSeedCount(2, 3),
						state.getSeedCount(2, 2), state.getSeedCount(2, 1),
						state.getSeedCount(1, STORE));
		lines[3] = "|    |-------+-------+-------+-------+-------+-------|    |";
		lines[4] = String
				.format("| %2d | 1[%2d] | 2[%2d] | 3[%2d] | 4[%2d] | 5[%2d] | 6[%2d] | P1 |",
						state.getSeedCount(2, STORE), state.getSeedCount(1, 1),
						state.getSeedCount(1, 2), state.getSeedCount(1, 3),
						state.getSeedCount(1, 4), state.getSeedCount(1, 5),
						state.getSeedCount(1, 6));
		lines[5] = "+----+-------+-------+-------+-------+-------+-------+----+";
		return lines;
	}

	@Override
	public int getCurrentPlayer() {
		return state.getCurrentPlayer();
	}

	@Override
	public int getScore(int player) {
		return state.getScore(player);
	}

	@Override
	public void move(int house) {
		state.move(house);
		
	}

	@Override
	public boolean isHouseEmpty(int house) {
		return state.isHouseEmpty(house);
	}

	@Override
	public boolean isGameOver() {
		return state.isGameOver();
	}

	@Override
	public void quit() {
		state.quit();
	}


}
