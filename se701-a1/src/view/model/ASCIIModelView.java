package view.model;

import java.util.Map;
import java.util.Properties;

import mancala.PropsLoader;
import model.GameModel;

//put text output in a new place together???
//look at dependency injection of IO //have an output interface???

public class ASCIIModelView extends AbstractModelView {

	public static final String asciiPropsFolder = "asciiProperties/";
	private final ASCIIBoardPrinter board;
	private final Properties props;

	public ASCIIModelView(GameModel model) {
		this.props = createDefaultProperties();
		this.board = new ASCIIBoardPrinter(model, props);
	}

	public void setProperties(String propsFileName) {
		String newPropertiesFile = asciiPropsFolder + propsFileName;
		PropsLoader.insertCustomProps(this.props, newPropertiesFile);
		this.board.setConstants(props);
	}

	private Properties createDefaultProperties() {
		Properties props = new Properties();
		props.setProperty("1ShortName", "P1");
		props.setProperty("2ShortName", "P2");
		props.setProperty("hFence", "-");
		props.setProperty("vFence", "|");
		props.setProperty("cnrFence", "+");
		props.setProperty("houseFormat", "%2d[%2d] ");
		
		props.setProperty("houseEmptyPrompt", "House is empty. Move again.");
		props.setProperty("invalidHouseFormat", "%d is not a valid house.");
		props.setProperty("gameOver", "Game over");
		props.setProperty("scoreFormat", "	player %d:%s");
		props.setProperty("winnerFormat", "Player %d wins!");
		props.setProperty("tie", "A tie!");
		props.setProperty("undo", "Previous move undone.");
		return props;
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

	// *****************************************************
	// MancalaView Overridden Functions
	//
	@Override
	public void emptyHousePrompt() {
		println(props.getProperty("houseEmptyPrompt"));
		printBoard();
	}

	@Override
	public void gameQuit(int quittingPlayer) {
		printGameOverBoard();
	}

	@Override
	public void gameEnded(Map<Integer, Integer> playerToScore) {
		printGameOverBoard();
		printScores(playerToScore);
	}

	@Override
	public void moveEnded() {
		printBoard();
	}
	
	@Override
	public void gameStarted() {
		printBoard();
	}
	
	@Override
	public void invalidHousePrompt(int house) {
		println(String.format(props.getProperty("invalidHouseFormat"), house));
	}
	
	@Override
	public void moveUndone() {
		println(props.getProperty("undo"));
		printBoard();
	}

	// *****************************************************

	/**
	 * Print the final board of the game once it is over. Included the text
	 * "Game Over" before the board.
	 */
	private void printGameOverBoard() {
		println(props.getProperty("gameOver"));
		printBoard();
	}

	/**
	 * Print the score and the winner at the end of a completed game
	 */
	private void printScores(Map<Integer, Integer> playerToScore) {
		int highestScore = Integer.MIN_VALUE;
		int winner = -1;
		boolean tie = false;
		for (int i = 1; i <= playerToScore.size(); i++) {
			int score = playerToScore.get(i);
			int player = i;
			println(String.format(props.getProperty("scoreFormat"), player,
					score));
			if (score > highestScore) {
				winner = player;
				highestScore = score;
				tie = false;
			} else if (score == highestScore) {
				tie = true;
			}
		}
		if (tie) {
			println(props.getProperty("tie"));
		} else {
			println(String.format(props.getProperty("winnerFormat"), winner));
		}
	}

	private void printStringArray(String[] str) {
		for (int i = 0; i < str.length; i++) {
			println(str[i]);
		}
	}
}
