package mancala;

import utility.IO;
import utility.MockIO;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 */
public class Mancala {
	public static void main(String[] args) {
		new Mancala().play(new MockIO());
	}
	
	public void play(IO io) {
		
		MancalaModel model = new MancalaModel();
		MancalaView asciiView = new MancalaASCIIView(model, io); //prints board
		MancalaInput input = (MancalaInput)asciiView; //use ASCIIView as input source
			
		int house;
		while (!model.isGameOver())  {
			if ((house = input.promptPlayer()) == MancalaInput.cancelResult) {
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