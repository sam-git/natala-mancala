package mancala;

import java.util.Observer;

public abstract class MancalaView implements Observer {
	public static final int cancelResult = -1;

	abstract int promptPlayer();

	abstract void gameQuit();

	abstract void gameEnded();

	abstract void updateBoard();

	abstract void emptyHousePrompt();
}
