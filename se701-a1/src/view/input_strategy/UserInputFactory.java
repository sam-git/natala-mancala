package view.input_strategy;

import model.GameModel;

public class UserInputFactory {
	
	public static IUserInputStrategy move(final int house) {
		return new IUserInputStrategy() {
			public void execute(GameModel m) {
				m.move(house);
			}
		};
	}
	public static IUserInputStrategy undo() {
		return new IUserInputStrategy() {
			public void execute(GameModel m) {
				m.undo();
			}
		};
	}
	public static IUserInputStrategy redo() {
		return new IUserInputStrategy() {
			public void execute(GameModel m) {
				m.redo();
			}
		};
	}
	public static IUserInputStrategy quit() {
		return new IUserInputStrategy() {
			public void execute(GameModel m) {
				m.quit();
			}
		};
	}
}
