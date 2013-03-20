package viewStrategies;

import mancala.MancalaView;


public class MoveEndedStrategy implements ViewStrategy{

	@Override
	public void accept(MancalaView view) {
		view.moveEnded();		
	}

}
