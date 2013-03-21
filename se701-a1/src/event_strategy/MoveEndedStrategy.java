package event_strategy;

import mancala.AbstractView;

public class MoveEndedStrategy implements IEventStrategy{

	@Override
	public void execute(AbstractView view) {
		view.moveEnded();		
	}

}
