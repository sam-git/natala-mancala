package view.event_strategy;

import view.AbstractView;

public class EventStrategyFactory {

	public static IEventStrategy gameEndedStrategy() {
		return new IEventStrategy() {
			public void execute(AbstractView view) {
				view.gameEnded();
			}
		};
	}
	
	public static IEventStrategy moveEndedStrategy() {
		return new IEventStrategy() {
			public void execute(AbstractView view) {
				view.moveEnded();
			}
		};
	}
	
	public static IEventStrategy gameQuitStrategy() {
		return new IEventStrategy() {
			public void execute(AbstractView view) {
				view.gameQuit();
			}
		};
	}
}
