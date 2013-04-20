package view.model;

import java.util.Map;
import java.util.Properties;

import mancala.PropsLoader;
import model.GameModel;

//put text output in a new place together???
//look at dependency injection of IO //have an output interface???

public class ASCIIModelView extends AbstractModelView {

	public static final String asciiPropsFolder = "asciiProperties/";
	public static final String customPropsFile = "Custom.properties";

	private final Properties props;
	private final ASCIIBoardPrinter board;
	private final String defaultHouseEmptyPrompt = "House is empty. Move again.";
	private final String defaultGameOver = "Game over";
	private final String defaultScoreformat = "	player %d:%s";
	private final String defaultWinnerformat = "Player %d wins!";

	public ASCIIModelView(GameModel model) {
		String customPropsLoc = asciiPropsFolder + customPropsFile;
		this.props = PropsLoader.loadPropsFile(customPropsLoc);
		this.board = new ASCIIBoardPrinter(model, props);
	}

	public void println(String s) {
		System.out.println(s);
	}
	
	/**
	 * Print an ASCII representation of the current board.
	 */
	public void printBoard() {
		printStringArray(board.toStringArray());
	}

	//*****************************************************
	// MancalaView Overridden Functions
	//
	@Override
	public void emptyHousePrompt() {
		println(props.getProperty("houseEmptyPrompt", defaultHouseEmptyPrompt));
		printBoard();
	}

	@Override
	public void gameQuit(int quittingPlayer) {
		printGameOverBoard();
	}

	@Override
	public void gameEnded(Map<Integer, Integer>playerToScore) {
		printGameOverBoard();
		printScores(playerToScore);
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
		println(props.getProperty("gameOver", defaultGameOver));
		printBoard();
	}

	/**
	 * Print the score and the winner at the end of a completed game
	 */
	private void printScores(Map<Integer, Integer>playerToScore) {
		int highestScore = -1;
		int winner = -1;
		boolean tie = false;
		for (int i = 1; i <= playerToScore.size(); i++) {
			int score = playerToScore.get(i);
			int player = i;
			if (score > highestScore) {
				winner = player;
				highestScore = score;
				tie = false;
			} else if (score == highestScore) {
				tie = true;
			}
			println(String.format(defaultScoreformat, player, score));
		}
		if (tie) {
			println("A tie!");
		} else {
			println(String.format(defaultWinnerformat, winner));
		}
	}

	private void printStringArray(String[] str) {
		for (int i = 0; i < str.length; i++) {
			println(str[i]);
		}
	}
}
