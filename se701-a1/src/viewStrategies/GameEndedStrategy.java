package viewStrategies;

import mancala.MancalaView;

public class GameEndedStrategy implements ViewStrategy{

	@Override
	public void accept(MancalaView view) {
		view.gameEnded();		
	}

}
