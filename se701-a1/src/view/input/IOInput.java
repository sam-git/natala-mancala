package view.input;

import utility.IO;
import view.input_strategy.IUserInputStrategy;
import view.input_strategy.UserInputFactory;

public class IOInput implements IMancalaInput {
	
	/**
	 * int returned by promptPlayer() when the player chooses the quit option.
	 * Should be used by the controller to determine if the player quit instead of made a move.
	 */
	public static final int cancelResult = -1;

	private final IO io;
	private final int HOUSES_PER_PLAYER;
	
	public IOInput(IO io, int housesPerPlayer) {
		this.io = io;
		this.HOUSES_PER_PLAYER = housesPerPlayer;
	}

	/**
	 * prompt the user to make a move, and returns an integer from 1 to 6 if the
	 * user made a valid move, prompts the user again if the move was invalid,
	 * or returns MancalaView.cancelResult if the user pressed the quit key..
	 */
	@Override
	public IUserInputStrategy promptPlayer(String name) {
		String prompt = name
				+ "'s turn - Specify house number or 'q' to quit: ";
		int input = io.readInteger(prompt, 1, this.HOUSES_PER_PLAYER,
				cancelResult, "q");
		if (input == cancelResult) {
			return UserInputFactory.quit();
		} else {
			return UserInputFactory.move(input);
		}
	}
}
