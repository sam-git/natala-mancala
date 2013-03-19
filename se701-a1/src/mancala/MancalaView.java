/**
 * 
 */
package mancala;

import utility.IO;

/**
 * @author Sam
 * 
 */
public class MancalaView {

	private MancalaModel model;
	private IO io;

	public static final int cancelResult = -1;

	public MancalaView(MancalaModel model, IO io) {
		super();
		this.model = model;
		this.io = io;
	}

	/*
	 * Print an ASCII representation of the current board to io
	 */
	public void printBoard() {

		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		String line2 = String
				.format("| P2 | 6[%2d] | 5[%2d] | 4[%2d] | 3[%2d] | 2[%2d] | 1[%2d] | %2d |",
						model.getCount(2, 6), model.getCount(2, 5),
						model.getCount(2, 4), model.getCount(2, 3),
						model.getCount(2, 2), model.getCount(2, 1),
						model.getCount(1, 0));
		io.println(line2);
		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		String line4 = String
				.format("| %2d | 1[%2d] | 2[%2d] | 3[%2d] | 4[%2d] | 5[%2d] | 6[%2d] | P1 |",
						model.getCount(2, 0), model.getCount(1, 1),
						model.getCount(1, 2), model.getCount(1, 3),
						model.getCount(1, 4), model.getCount(1, 5),
						model.getCount(1, 6));
		io.println(line4);
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");

	}
	

	/*
	 * prompt the user to make a move, and returns an integer from 0 to 6 if the
	 * user made a valid move, or -1 for an invalid move
	 */
	public int promptPlayer() {
		String prompt = "Player " + model.getCurrentPlayer()
				+ "'s turn - Specify house number or 'q' to quit: ";
		return io.readInteger(prompt, 0, 6, cancelResult, "q");
	}
	
	public int moveAgain() {
		io.println("House is empty. Move again.");
		printBoard();
		return promptPlayer();
	}
	
	/*
	 * Print the final board of the game once it is over.
	 * Included the text "Game Over" before the board.
	 */
	private void printGameOverBoard() {
		io.println("Game over");
		printBoard();	
	}
	
	/*
	 * print the score and the winner at the end of a completed game
	 */
	private void printScores() {
		int player1 = model.getScore(1);
		int player2 = model.getScore(2);
		io.println("	player 1:" + player1);
		io.println("	player 2:" + player2);
		if (player1>player2) {
			io.println("Player 1 wins!");
		} else if (player2>player1){
			io.println("Player 2 wins!");			
		} else {
			io.println("A tie!");
		}
	}
	
	public void gameEnded() {
		printGameOverBoard();
		printScores();
	}

	public void gameQuit() {
		printGameOverBoard();		
	}
}
