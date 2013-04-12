package view;

import utility.IO;
import board.AbstractBoard;

/**
 * Concrete class acting as a View and as Input for the Mancala game.
 * 
 * @author Sam
 * 
 */
public class ASCIIView extends AbstractView implements IMancalaInput{

	private final IO io;
	private final AbstractBoard board;

	public ASCIIView(AbstractBoard board, IO io) {
		this.io = io;
		this.board = board;
		printBoard(); //ASCII views print board first
	}
	
//*****************************************************
// MancalaInput Functions
//
	
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
		String prompt = "Player " + board.getCurrentPlayer()
				+ "'s turn - Specify house number or 'q' to quit: ";
		return io.readInteger(prompt, 
				1, 
				board.getHousesPerPlayer(), 
				IMancalaInput.cancelResult, "q");
	}
	
//*****************************************************
// MancalaView Overridden Functions
//
	
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

//*****************************************************
// Private Functions
//

	/**
	 * Print an ASCII representation of the current board to io.
	 */
	private void printBoard() {		
		String[] lines = board.toStringArray();
		for (int i = 0; i < lines.length; i++) {
			io.println(lines[i]);
		}
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
		int player1 = board.getScore(1);
		int player2 = board.getScore(2);
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
