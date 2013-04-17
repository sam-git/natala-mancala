package view.input;

import utility.IO;

public class IOInput implements IMancalaInput {

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
	public int promptPlayer(String name) {
		String prompt = name
				+ "'s turn - Specify house number or 'q' to quit: ";
		return io.readInteger(prompt, 1, this.HOUSES_PER_PLAYER,
				IMancalaInput.cancelResult, "q");
	}

}
