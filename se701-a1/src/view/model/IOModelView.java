package view.model;

import model.GameModel;
import utility.IO;

/**
 * Concrete class acting as a View and as Input for the Mancala game.
 * 
 * @author Sam
 * 
 */
public class IOModelView extends AbstractModelView {

	private final IO io;

	public IOModelView(GameModel model, IO io) {
		super(model);
		this.io = io;
		printBoard(); //ASCII views print board first
	}
	
//*****************************************************
// MancalaView Overridden Functions
//
	
	@Override
	public void emptyHousePrompt() {
		io.println("House is empty. Move again.");
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

//*****************************************************
// Private Functions
//

	/**
	 * Print an ASCII representation of the current board to io.
	 */
	private void printBoard() {		
		printStringArrayToIO(super.toStringArray());
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
		String[] lines = new String[3];
		
		int player1 = super.getFinalScore(1);
		int player2 = super.getFinalScore(2);
		
		lines[0] = ("	player 1:" + player1);
		lines[1] = ("	player 2:" + player2);
		
		if (player1 > player2) {
			lines[2] = ("Player 1 wins!");
		} else if (player2 > player1) {
			lines[2] = ("Player 2 wins!");
		} else {
			lines[2] = ("A tie!");
		}	
		printStringArrayToIO(lines);
	}
	
	private void printStringArrayToIO(String[] str) {
		for (int i = 0; i < str.length; i++) {
			io.println(str[i]);
		}
	}
}
