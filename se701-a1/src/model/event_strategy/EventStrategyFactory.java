package model.event_strategy;

import view.model.AbstractModelView;

public class EventStrategyFactory {
	
	public static IEventStrategy houseEmptyStrategy() {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.emptyHousePrompt();
			}
		};
	}

	public static IEventStrategy gameEndedStrategy() {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.gameEnded();
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
	
	public static IEventStrategy gameQuitStrategy() {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.gameQuit();
			}
		};
	}
}