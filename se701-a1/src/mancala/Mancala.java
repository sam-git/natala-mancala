package mancala;

import utility.IO;
import utility.MockIO;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 * Acts as the controller in a MVC architecture.
 */
public class Mancala {
	public static void main(String[] args) {
		new Mancala().play(new MockIO());
	}
	
	public void play(IO io) {
		
		MancalaModel model = new MancalaModel();
		AbstractView asciiView = new MancalaASCIIView(model, io); //ASCIIView prints board on construction
		model.addObserver(asciiView); //adds view as observer to model;
		IMancalaInput input = (IMancalaInput) asciiView; //use ASCIIView as input source
			
		int house;
		while (!model.isGameOver())  {
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