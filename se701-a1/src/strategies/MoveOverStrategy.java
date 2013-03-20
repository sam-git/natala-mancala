package strategies;

import mancala.MancalaView;

public class MoveOverStrategy implements Strategy{

	@Override
	public void accept(MancalaView view) {
		view.moveEnded();		
	}

}
