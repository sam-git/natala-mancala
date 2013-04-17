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
	/**
	 * called by the controller to prompt the player for input.
	 * should return MancalaInput.cancelResult if the player quits on their turn.
	 * Otherwise should return an int to represent the current players house choice.
	 * @return
	 */
	int promptPlayer(String name);
}
