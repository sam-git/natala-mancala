package mancala;

public interface MancalaInput {
	/**
	 * int returned by promptPlayer() when the player chooses the quit option.
	 * Should be used by the controller to determine if the player quit instead of made a move.
	 */
	public static final int cancelResult = -1;
	/**
	 * called by the controller to prompt the player for input.
	 * @return
	 */
	int promptPlayer();
	
	/**
	 * called by the controller if a player specified an empty house. 
	 */
	void emptyHousePrompt();
	
	/**
	 * called by the controller if a player quits.
	 */
	void gameQuit();
}
