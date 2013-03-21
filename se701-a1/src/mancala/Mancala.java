package mancala;

import utility.IO;
import utility.MockIO;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 * This class acts as a controller in the MVC design pattern applied to the game.
 */
public class Mancala {
	public static void main(String[] args) {
		new Mancala().play(new MockIO());
	}
	
	public void play(IO io) {
		
		//set up MVC components
		MancalaModel model = new MancalaModel();
		AbstractView asciiView = new MancalaASCIIView(model, io); //ASCIIView prints board on construction
		model.addObserver(asciiView); //adds view as observer to model;
		IMancalaInput input = (IMancalaInput) asciiView; //use ASCIIView as input source
		
		//game loop
		int house;
		while (!model.isGameOver())  {
			//get player input and check if they cancelled game.
			if ((house = input.promptPlayer()) == IMancalaInput.cancelResult) {
				model.quit(); //notifies all observers game was quit
				break;
			}

			if (model.isHouseEmpty(house)) {
				input.emptyHousePrompt();
				continue;
			}
			
			model.move(house); //changes game state. notifies all observers
		}
	}
}