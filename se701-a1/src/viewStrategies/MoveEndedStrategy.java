package viewStrategies;

import mancala.AbstractView;

public class MoveEndedStrategy implements IViewStrategy{

	@Override
	public void execute(AbstractView view) {
		view.moveEnded();		
	}

}
