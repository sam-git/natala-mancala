
package view.event_strategy;

import view.AbstractView;

/**
 * Interface that is used by the Model to send concrete objects containing the method to be called in views
 * when the model state changes. 
 * @author Sam
 *
 */
public interface IEventStrategy {
	public void execute(AbstractView view);
}
