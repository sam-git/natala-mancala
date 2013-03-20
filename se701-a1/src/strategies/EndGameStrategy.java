package strategies;

import mancala.MancalaView;

public class EndGameStrategy implements Strategy{

	@Override
	public void accept(MancalaView view) {
		view.gameEnded();		
	}

}
