package mancala;

import model.GameModel;
import rules.CrazyRules;
import rules.IGameRules;
import rules.KalahRules;
import utility.IO;
import utility.MockIO;
import view.ASCIIView;
import view.IMancalaInput;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 * This class acts as a controller in the MVC design pattern applied to the game.
 */
public class Mancala {
	public static void main(String[] args) {
		new Mancala().play(new MockIO());
	}
	
	public void play(IO io) {

		IGameRules rules = new KalahRules();
		GameModel model = new GameModel(rules);
		
		ASCIIView asciiView = new ASCIIView(model, io);
		model.addObserver(asciiView); //the view observes the model
		IMancalaInput input = asciiView; //use ASCIIView as input source
		
		//game loop
		int house;
		while (!model.isGameOver())  {
			//get player input and check if they cancelled game.
			if ((house = input.promptPlayer()) == IMancalaInput.cancelResult) {
				model.quit(); //notifies all observers game was quit
			} else {
				model.move(house); //changes game state. notifies all observers
			}
		}
	}
}