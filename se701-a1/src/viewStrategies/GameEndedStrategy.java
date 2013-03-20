package viewStrategies;

import mancala.AbstractView;

public class GameEndedStrategy implements IViewStrategy{

	@Override
	public void execute(AbstractView view) {
		view.gameEnded();		
	}

}
