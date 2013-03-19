/**
 * 
 */
package mancala;

import java.util.Observable;

import utility.IO;

/**
 * @author Sam
 * 
 */
public class MancalaASCIIView extends MancalaView {

	private IO io;

	public MancalaASCIIView(MancalaModel model, IO io) {		
		super(model);
		this.io = io;
		printBoard();
	}

	@Override
	public void gameQuit() {
		printGameOverBoard();
	}
	
	@Override
	public void emptyHousePrompt() {
		io.println("House is empty. Move again.");
		printBoard();
	}

	/**
	 * prompt the user to make a move, and returns an integer from 1 to 6 if the
	 * user made a valid move, prompts the user again if the move was invalid, 
	 * or returns MancalaView.cancelResult if the user pressed the quit key..
	 */
	@Override
	public int promptPlayer() {
		String prompt = "Player " + model.getCurrentPlayer()
				+ "'s turn - Specify house number or 'q' to quit: ";
		return io.readInteger(prompt, 1, 6, MancalaView.cancelResult, "q");
	}

	@Override
	/**
	 * Called by model when the game state changes.
	 */
	public void update(Observable arg0, Object arg1) {
		if (model.isGameOver()) {
			printGameOverBoard();
			printScores();
		} else {
			printBoard();
		}
	}

	//*****************************************************
	// Private Functions
	//

	/**
	 * Print an ASCII representation of the current board to io.
	 */
	private void printBoard() {

		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		String line2 = String
				.format("| P2 | 6[%2d] | 5[%2d] | 4[%2d] | 3[%2d] | 2[%2d] | 1[%2d] | %2d |",
						model.getHouseCount(2, 6), model.getHouseCount(2, 5),
						model.getHouseCount(2, 4), model.getHouseCount(2, 3),
						model.getHouseCount(2, 2), model.getHouseCount(2, 1),
						model.getHouseCount(1, 0));
		io.println(line2);
		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		String line4 = String
				.format("| %2d | 1[%2d] | 2[%2d] | 3[%2d] | 4[%2d] | 5[%2d] | 6[%2d] | P1 |",
						model.getHouseCount(2, 0), model.getHouseCount(1, 1),
						model.getHouseCount(1, 2), model.getHouseCount(1, 3),
						model.getHouseCount(1, 4), model.getHouseCount(1, 5),
						model.getHouseCount(1, 6));
		io.println(line4);
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");

	}

	/**
	 * Print the final board of the game once it is over. Included the text
	 * "Game Over" before the board.
	 */
	private void printGameOverBoard() {
		io.println("Game over");
		printBoard();
	}

	/**
	 * Print the score and the winner at the end of a completed game
	 */
	private void printScores() {
		int player1 = model.getScore(1);
		int player2 = model.getScore(2);
		io.println("	player 1:" + player1);
		io.println("	player 2:" + player2);
		if (player1 > player2) {
			io.println("Player 1 wins!");
		} else if (player2 > player1) {
			io.println("Player 2 wins!");
		} else {
			io.println("A tie!");
		}
	}
}
