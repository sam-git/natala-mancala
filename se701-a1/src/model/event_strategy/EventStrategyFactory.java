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
	
	public static IEventStrategy moveStartedStrategy(final int player, final int house) {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.moveStarted(player, house);
			}
		};
	}
	
	public static IEventStrategy houseEmptyStrategy(final int player) {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.emptyHousePrompt(player);
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
	
	public static IEventStrategy undoMoveStrategy(final boolean success) {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.moveUndone(success);
			}
		};
	}

	public static IEventStrategy moveRedoneStrategy(final boolean success) {
		return new IEventStrategy() {
			public void execute(AbstractModelView view) {
				view.moveRedone(success);
			}
		};
	}

}
