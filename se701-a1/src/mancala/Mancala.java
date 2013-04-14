package mancala;

import utility.IO;
import utility.MockIO;
import view.ASCIIView;
import view.AbstractView;
import view.IMancalaInput;
import board._AbstractBoard;
import board._KalahBoard;

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
		//model
		_AbstractBoard board = new _KalahBoard();
		ASCIIView asciiView = new ASCIIView(board, io);
		//view
		AbstractView view = asciiView; //use asciiView as view.
		board.addObserver(view); //the view observes the model
		//input
		IMancalaInput input = asciiView; //use ASCIIView as input source
		
		//game loop
		int house;
		while (!board.isGameOver())  {
			//get player input and check if they cancelled game.
			if ((house = input.promptPlayer()) == IMancalaInput.cancelResult) {
				board.quit(); //notifies all observers game was quit
				break;
			}

			if (board.isHouseEmpty(house)) {
				input.emptyHousePrompt();
				continue;
			}
			
			board.move(house); //changes game state. notifies all observers
		}
	}
}