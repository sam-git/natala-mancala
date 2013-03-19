package mancala;

import utility.MockIO;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 */
public class Mancala {
	public static void main(String[] args) {
		new Mancala().play(new MockIO());
	}
	
	public void play(MockIO io) {
		
		MancalaModel model = new MancalaModel();
		MancalaView view = new MancalaASCIIView(model, io);
		model.addObserver(view);
		
		view.updateBoard();
		int house;		
		while (!model.isGameOver() && (house = view.promptPlayer()) != MancalaView.cancelResult) {

			if (model.isHouseEmpty(house)) {
				view.emptyHousePrompt();
				continue;
			}
			
			model.move(house); //notifies observers
		}
		
		//game was quit is isGameOver is false.
		if(!model.isGameOver()) {
			view.gameQuit();
		}
	}
}
