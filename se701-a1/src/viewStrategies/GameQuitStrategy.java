package viewStrategies;

import mancala.AbstractView;

public class GameQuitStrategy implements IViewStrategy{

	@Override
	public void execute(AbstractView view) {
		view.gameQuit();
	}

}
