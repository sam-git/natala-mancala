package view.input;

import view.input_strategy.IInputStrategy;


/**
 * Interface that  must be implemented by any class acting as the input to the Mancala game.
 */
public interface IMancalaInput {
	/**
	 * called by the controller to prompt the player for input.
	 * should return MancalaInput.cancelResult if the player quits on their turn.
	 * Otherwise should return an int to represent the current players house choice.
	 * @return
	 */
	IInputStrategy getAction();
	void setPlayerNumber(int playerNumber);
}
