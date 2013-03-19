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
		
		MancalaModel model = MancalaModel.getInstance();
		MancalaView view = new MancalaView(model, io);	
		boolean isGameOver = false;		
		
		view.printBoard();
		int response = view.promptPlayer();
		
		while (response != MancalaView.cancelResult && !isGameOver) {
			
			if (model.getCount(model.getCurrentPlayer(), response) == 0) {
				response = view.moveAgain();
				continue;
			}
			
			isGameOver = model.currentPlayerMove(response);			
			
			if(!isGameOver){
				view.printBoard();
				response = view.promptPlayer();
			}
		}
		
		if(isGameOver) {
			view.gameEnded();
		} else {
			view.gameQuit();
		}
		
		model.reset();
	}
}
