package view.model;

import model.GameModel;

//put text output in a new place together???
//look at dependency injection of IO //have an output interface???

public class ASCIIModelView extends AbstractModelView {

	private final ASCIIBoardPrinter board;

	public ASCIIModelView(GameModel model) {
		this.board = new ASCIIBoardPrinter(model);
	}

	public void println(String s) {
		System.out.println("ERROR");
	}
	
	/**
	 * Print an ASCII representation of the current board to io.
	 */
	public void printBoard() {
		printStringArray(board.toStringArray());
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
	public void gameEnded(int... scores) {
		printGameOverBoard();
		printScores(scores);
	}

	@Override
	public void moveEnded() {
		printBoard();
	}
	//*****************************************************

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
	private void printScores(int scores[]) {
		String[] lines = new String[3];

		int player1 = scores[0];
		int player2 = scores[1];

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
