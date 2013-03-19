package mancala;

public class ModelObservedData {

	private State state;

	public ModelObservedData(State state) {
		this.state = state;
	}

	public void accept(MancalaView visitor) {
		switch (state) {
		case UPDATEDBOARD:
			visitor.updateBoard();
			break;
		case GAMEOVER:
			visitor.gameEnded();
			break;
		default:
			break;
		}
	}
}
