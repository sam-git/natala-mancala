package viewStrategies;

import mancala.MancalaView;

public class GameQuitStrategy implements ViewStrategy{

	@Override
	public void accept(MancalaView view) {
		view.gameQuit();
	}

}
