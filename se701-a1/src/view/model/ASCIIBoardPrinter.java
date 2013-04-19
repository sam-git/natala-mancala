package view.model;

import model.GameModel;

public class ASCIIBoardPrinter {

	private final GameModel m;
	private final int HOUSES_PER_PLAYER;

	private String boardTopAndBottom;
	private String boardMiddle;
	private final String HOUSE_FORMAT = "%2d[%2d] ";
	private final String STORE_FORMAT = " %2d ";
	private final String STORE_NAME_FORMAT = " %2s ";
	private final String FENCE = "|";
	
	public ASCIIBoardPrinter(GameModel m) {
		this.m = m;
		this.HOUSES_PER_PLAYER = m.getHousesPerPlayer();
		this.prepareBoard();
	}
	
	private void prepareBoard() {
		StringBuffer sb = new StringBuffer();
		sb.append("+----");
		for (int i = 0; i < HOUSES_PER_PLAYER; i++) {
			sb.append("+-------");
		}
		sb.append("+----+");
		boardTopAndBottom = sb.toString();

		String gap = FENCE + String.format(STORE_NAME_FORMAT, "") + FENCE;
		boardMiddle = gap
				+ sb.subSequence(gap.length(), sb.length() - gap.length())
				+ gap;
	}
	
	public String[] toStringArray() {
		String[] lines = new String[5];
		lines[0] = this.boardTopAndBottom;
		lines[1] = getPlayerTwoSide();
		lines[2] = this.boardMiddle;
		lines[3] = getPlayerOneSide();
		lines[4] = this.boardTopAndBottom;
		return lines;
	}

	private String getPlayerTwoSide() {
		StringBuffer sb = new StringBuffer(FENCE);
		sb.append(String.format(STORE_NAME_FORMAT + FENCE, "P2"));
		for (int i = HOUSES_PER_PLAYER; i > 0; i--) {
			sb.append(String.format(HOUSE_FORMAT + FENCE, i,
					m.getSeedCount(2, i)));
		}
		sb.append(String.format(STORE_FORMAT + FENCE, m.getStoreSeedCount(1)));
		return sb.toString();
	}

	private String getPlayerOneSide() {
		StringBuffer sb = new StringBuffer(FENCE);
		sb.append(String.format(STORE_FORMAT + FENCE, m.getStoreSeedCount(2)));
		for (int i = 1; i <= HOUSES_PER_PLAYER; i++) {
			sb.append(String.format(HOUSE_FORMAT + FENCE, i,
					m.getSeedCount(1, i)));
		}
		sb.append(String.format(STORE_NAME_FORMAT + FENCE, "P1"));
		return sb.toString();
	}
	
	
}
