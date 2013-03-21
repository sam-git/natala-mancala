package event_strategy;

import mancala.AbstractView;

public class GameEndedStrategy implements IEventStrategy{

	@Override
	public void execute(AbstractView view) {
		view.gameEnded();		
	}

}
