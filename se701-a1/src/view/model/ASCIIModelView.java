package view.model;

import model.GameModel;

//make a board printer class
//put text output in a new place together???
//look at dependency injection of IO //have an output interface???

public class ASCIIModelView extends AbstractModelView {

	private final GameModel m;
	private final int HOUSES_PER_PLAYER;

	private String boardTopAndBottom;
	private String boardMiddle;
	private final String HOUSE_FORMAT = "%2d[%2d] ";
	private final String STORE_FORMAT = " %2d ";
	private final String STORE_NAME_FORMAT = " %2s ";
	private final String FENCE = "|";

	public ASCIIModelView(GameModel model) {
		this.m = model;
		this.HOUSES_PER_PLAYER = model.getHousesPerPlayer();
		this.prepareBoard();
		this.printBoard(); //ASCII views print board at start of game
	}

	public void println(String s) {
		System.out.println("ERROR");
	}

	//*****************************************************
	// MancalaView Overridden Functions
	//
	@Override
	public void emptyHousePrompt() {
		println("House is empty. Move again.");
		printBoard();
	}

	@Override
	public void gameQuit() {
		printGameOverBoard();
	}

	@Override
	public void gameEnded() {
		printGameOverBoard();
		printScores();
	}

	@Override
	public void moveEnded() {
		printBoard();
	}

	private String[] toStringArray() {
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

	private int getFinalScore(int player) {
		return m.getFinalScore(player);
	}

	/**
	 * Print an ASCII representation of the current board to io.
	 */
	public void printBoard() {
		printStringArray(toStringArray());
	}

	/**
	 * Print the final board of the game once it is over. Included the text
	 * "Game Over" before the board.
	 */
	private void printGameOverBoard() {
		println("Game over");
		printBoard();
	}

	/**
	 * Print the score and the winner at the end of a completed game
	 */
	private void printScores() {
		String[] lines = new String[3];

		int player1 = getFinalScore(1);
		int player2 = getFinalScore(2);

		lines[0] = ("	player 1:" + player1);
		lines[1] = ("	player 2:" + player2);

		if (player1 > player2) {
			lines[2] = ("Player 1 wins!");
		} else if (player2 > player1) {
			lines[2] = ("Player 2 wins!");
		} else {
			lines[2] = ("A tie!");
		}
		printStringArray(lines);
	}

	private void printStringArray(String[] str) {
		for (int i = 0; i < str.length; i++) {
			println(str[i]);
		}
	}
}
