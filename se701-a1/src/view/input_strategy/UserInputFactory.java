package view.input_strategy;

import model.Model;

public class UserInputFactory {
	
	public static IInputStrategy move(final int house) {
		return new IInputStrategy() {
			public void executeOn(Model m) {
				m.move(house);
			}
		};
	}
	public static IInputStrategy undo() {
		return new IInputStrategy() {
			public void executeOn(Model m) {
				m.undo();
			}
		};
	}
	public static IInputStrategy redo() {
		return new IInputStrategy() {
			public void executeOn(Model m) {
				m.redo();
			}
		};
	}
	public static IInputStrategy quit() {
		return new IInputStrategy() {
			public void executeOn(Model m) {
				m.quit();
			}
		};
	}
}
