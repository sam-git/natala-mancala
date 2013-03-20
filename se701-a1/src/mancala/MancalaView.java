package mancala;

import java.util.Observable;
import java.util.Observer;

import viewStrategies.ViewStrategy;


/**
 * An abstract class that all MancalaViews must subclass.
 * It provides methods that are automatically called during certain events in the model.
 * If a subclass wishes to act upon those views it can override them.
 * 
 * If a view wishes to be notified of new events in the model: 
 * 	a new empty method must be added to this class.
 *  a new strategy object for that method must be created.
 *  all existing views that wish to act upon that event can override the new method locally.
 *  
 * It is up to child classes to keep a reference to the model, 
 * and add itself as on addObserver of the model. 
 * 
 * @author Sam
 *
 */
public abstract class MancalaView implements Observer {
	
	@Override
	/**
	 * Called by model when the game state changes.
	 */
	public void update(Observable arg0, Object strategy) {
		((ViewStrategy)strategy).accept(this);
	}
	
//*****************************************************
// The following methods can be overridden by subclasses if they wish to act upon
//	the events when notified of them by the Model.
	
	public void gameEnded() {}

	public void moveEnded() {}
	
	public void gameQuit() {}
}