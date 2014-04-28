
package model.event_strategy;

import view.model_view.AbstractModelView;

/**
 * Interface that is used by the Model to send concrete objects containing the method to be called in views
 * when the model state changes. 
 * @author Sam
 *
 */
public interface IEventStrategy {
	public void execute(AbstractModelView view);
}
