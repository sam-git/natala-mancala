package view.model_view;

import model.Model;
import utility.IO;

/**
 * Concrete class acting as a View for the Mancala game.
 * 
 * @author Sam
 * 
 */
public class IOModelView extends ASCIIModelView {

	private final IO io;

	public IOModelView(Model model, IO io) {
		super(model);
		this.io = io; //must come after super call.
	}

	// Manual Override to print to IO.
	public void println(String s) {
		io.println(s);
	}
}
