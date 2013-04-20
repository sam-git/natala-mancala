package model.event_strategy;

import java.util.Map;

import view.model.AbstractModelView;

public class EventStrategyFactory {
	
	public static IEventStrategy houseEmptyStrategy() {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.emptyHousePrompt();
			}
		};
	}

	public static IEventStrategy gameEndedStrategy(final Map<Integer, Integer>playerToScore) {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.gameEnded(playerToScore);
			}
		};
	}
	
	public static IEventStrategy moveEndedStrategy() {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.moveEnded();
			}
		};
	}
	
	public static IEventStrategy gameQuitStrategy(final int player) {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.gameQuit(player);
			}
		};
	}
}
