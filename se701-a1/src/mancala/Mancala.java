package mancala;

import model.GameModel;
import utility.IO;
import utility.MockIO;
import view.input.IMancalaInput;
import view.input.IOInput;
import view.model.IOModelView;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 * This class acts as a controller in the MVC design pattern applied to the game.
 */
public class Mancala {
	
	private static String gameProperties = "Kalah.properties"; //global variable to not break test Cases
	
	public static void main(String[] args) {
		if (args.length > 0)
			gameProperties = args[0];
		new Mancala().play(new MockIO());
	}
	
	public void play(IO io) {

		GameModel model = new GameModel(gameProperties);
		
		IOModelView view = new IOModelView(model, io);
		model.addObserver(view); //the view observes the model
		
		IMancalaInput input = new IOInput(io, model.getHousesPerPlayer()); //use ASCIIView as input source
		
		//game loop
		int house;
		while (!model.isGameOver())  {
			//get player input and check if they cancelled game.
			house = input.promptPlayer(model.getCurrentPlayerName());
			if (house == IMancalaInput.cancelResult) {
				model.quit(); //notifies all observers game was quit
			} else {
				model.move(house); //changes game state. notifies all observers
			}
		}
	}
}