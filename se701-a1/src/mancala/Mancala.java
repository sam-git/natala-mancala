package mancala;

import java.util.Stack;

import model.GameModel;
import utility.IO;
import utility.MockIO;
import view.input.IMancalaInput;
import view.input.IOInput;
import view.input.InputWithLoadSave;
import view.input_strategy.IUserInputStrategy;
import view.model.IOModelView;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 * This class acts as a controller in the MVC design pattern applied to the game.
 */
public class Mancala {
	
	private static final String gamePropertiesExtension = ".rules";
	private static final String asciiPropertiesExtension = ".ascii";
	private static String gameProperties;
	private static String boardProperties;
	
	public static void main(String[] args) {
		checkArgsForPropFiles(args);
		new Mancala().play(new MockIO());
	}

	public void play(IO io) {

		GameModel model = new GameModel(gameProperties);
		IOModelView view = new IOModelView(model, io);	
		if (boardProperties != null) view.setProperties(boardProperties);
		
		model.addObserver(view);
		
		IMancalaInput input = new IOInput(io, model.getHousesPerPlayer());
//		IMancalaInput input = new InputWithLoadSave(model.getHousesPerPlayer());
		
		Stack<GameModel.GameMemento> savedStates = new Stack<GameModel.GameMemento>();
		
		//game loop
		model.startGame();
		while (!model.isGameOver())  {
			IUserInputStrategy userInput = input.promptPlayer(model.getCurrentPlayerName());
			userInput.execute(model);
		}
	}

	private static void checkArgsForPropFiles(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				if (arg.contains(gamePropertiesExtension) && gameProperties  == null) {
					gameProperties = arg; //arg is a gameProperty, so don't need to check again.
					continue;
				}
				if (arg.contains(asciiPropertiesExtension) && boardProperties == null) {
					boardProperties = arg;
				}
			}
		}
	}
}