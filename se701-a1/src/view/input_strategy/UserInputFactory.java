package view.input_strategy;

import model.Model;

public class UserInputFactory {
	
	public static IUserInputStrategy move(final int house) {
		return new IUserInputStrategy() {
			public void executeOn(Model m) {
				m.move(house);
			}
		};
	}
	public static IUserInputStrategy undo() {
		return new IUserInputStrategy() {
			public void executeOn(Model m) {
				m.undo();
			}
		};
	}
	public static IUserInputStrategy redo() {
		return new IUserInputStrategy() {
			public void executeOn(Model m) {
				m.redo();
			}
		};
	}
	public static IUserInputStrategy quit() {
		return new IUserInputStrategy() {
			public void executeOn(Model m) {
				m.quit();
			}
		};
	}
}
