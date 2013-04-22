package model.event_strategy;

import java.util.ArrayList;
import java.util.List;

import view.model_view.AbstractModelView;

public class CompositeEventStrategy implements IEventStrategy {

	private List<IEventStrategy> childEvents = new ArrayList<IEventStrategy>();
	
	@Override
	public void execute(AbstractModelView view) {
		for (IEventStrategy event : childEvents) {
			event.execute(view);
		}
	}
	public void add(IEventStrategy event) {
		childEvents.add(event);
	}
}
