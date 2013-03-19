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
		MancalaView view = new MancalaView(model, io);
		model.addObserver(view);
		
		boolean isGameOver = false;		
		
		view.printBoard();
		int response = view.promptPlayer();
		
		while (response != MancalaView.cancelResult && !isGameOver) {
			
			if (model.getCount(model.getCurrentPlayer(), response) == 0) {
				response = view.moveAgain();
				continue;
			}
			
			isGameOver = model.currentPlayerMove(response); //notifies observers
			
			if(!isGameOver){
				response = view.promptPlayer();
			}
		}
		
		//game was quit is isGameOver is false.
		if(!isGameOver) {
			view.gameQuit();
		}
	}
}
