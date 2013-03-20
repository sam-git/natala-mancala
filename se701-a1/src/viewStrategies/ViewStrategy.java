package viewStrategies;

import mancala.MancalaView;

/**
 * Interface that is used by the Model to send concrete objects containing the method to be called in views
 * when the model state changes. 
 * @author Sam
 *
 */
public interface ViewStrategy {
	public void accept(MancalaView view);
}
