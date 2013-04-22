package model.event_strategy;

import java.util.Map;

import view.model_view.AbstractModelView;

public class EventStrategyFactory {
	
	public static IEventStrategy gameStartStrategy() {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.gameStarted();
			}
		};
	}
	
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
	
	public static IEventStrategy invalidHouseStrategy(final int house) {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.invalidHousePrompt(house);
			}
		};
	}
	
	public static IEventStrategy undoMoveStrategy() {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.moveUndone();
			}
		};
	}

}
