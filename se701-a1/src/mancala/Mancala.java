package mancala;

import model.GameModel;
import rules.CrazyRules;
import rules.IGameRules;
import rules.KalahRules;
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
	public static void main(String[] args) {
		new Mancala().play(new MockIO());
	}
	
	public void play(IO io) {

		IGameRules rules = new KalahRules(); //make rules a properties file
//		IGameRules rules = new CrazyRules();
		GameModel model = new GameModel(rules);
		
		IOModelView view = new IOModelView(model, io);
		model.addObserver(view); //the view observes the model
		
		IMancalaInput input = new IOInput(io, rules.getHousesPerPlayer()); //use ASCIIView as input source
		
		//game loop
		int house;
		while (!model.isGameOver())  {
			//get player input and check if they cancelled game.
			if ((house = input.promptPlayer(model.getCurrentPlayerName())) 
					== IMancalaInput.cancelResult) {
				model.quit(); //notifies all observers game was quit
			} else {
				model.move(house); //changes game state. notifies all observers
			}
		}
	}
}