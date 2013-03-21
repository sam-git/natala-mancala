package event_strategy;

import mancala.AbstractView;

public class GameQuitStrategy implements IEventStrategy{

	@Override
	public void execute(AbstractView view) {
		view.gameQuit();
	}

}
