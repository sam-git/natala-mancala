package mancala;

import java.util.Observer;

/**
 * MancalaView defines a minimum set of methods that are needed by the Mancala controller
 * to directly manipulate the view.
 * 
 * All other methods required should be implemented by the concrete class and called from the 
 * Observer update() method when the Mancala model changes. 
 * 
 * @author Sam
 *
 */
public abstract class MancalaView implements Observer {
	
	/**
	 * int returned by promptPlayer() when the player chooses the quit option.
	 * Should be used by the controller to determine if the player quit instead of made a move.
	 */
	public static final int cancelResult = -1;
	
	/**
	 * Used by child classes to access the model. 
	 */
	protected MancalaModel model;
	
	/**
	 * Associates a model with the view and adds the view as an observer of the model.
	 * @param model
	 */
	public MancalaView(MancalaModel model) {
		this.model = model;
		model.addObserver(this);
	}

	/**
	 * called by the controller to prompt the player for input.
	 * @return
	 */
	abstract int promptPlayer();
	
	/**
	 * called by the controller if a player specified an empty house. 
	 */
	abstract void emptyHousePrompt();
	
	/**
	 * called by the controller if a player quits.
	 */
	abstract void gameQuit();
	
	public abstract void gameEnded();

	public abstract void updateBoard();
}
