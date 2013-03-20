package mancala;

import java.util.Observable;
import java.util.Observer;

import strategies.Strategy;

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
	
	@Override
	/**
	 * Called by model when the game state changes.
	 */
	public void update(Observable arg0, Object arg1) {
		Strategy strategy = (Strategy)arg1;
		strategy.accept(this);
	}
	
	public abstract void gameEnded();

	public abstract void moveEnded();
}
