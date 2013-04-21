package view.input;


/**
 * Interface that  must be implemented by any class acting as the input to the Mancala game.
 */
public interface IMancalaInput {
	/**
	 * int returned by promptPlayer() when the player chooses the quit option.
	 * Should be used by the controller to determine if the player quit instead of made a move.
	 */
	public static final int cancelResult = -1;
	public static final int undoResult = -9837298;
	public static final int redoResult = 4324932;
	public static final int loadResult = 123423142;
	public static final int saveResult = 83732298;
	/**
	 * called by the controller to prompt the player for input.
	 * should return MancalaInput.cancelResult if the player quits on their turn.
	 * Otherwise should return an int to represent the current players house choice.
	 * @return
	 */
	int promptPlayer(String name);
}
