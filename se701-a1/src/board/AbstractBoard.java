package board;

import java.util.Observer;

public abstract class AbstractBoard {
	
	private final BoardModel model;
	
	public AbstractBoard(BoardModel model) {
		this.model = model;
	}

//*****************************************************
// Method just for subclasses
//	
	protected int getSeedCount(int player, int house) {
		return model.getSeedCount(player, house);
	}
	
//*****************************************************
// View Methods
//
	public void addObserver(Observer o) {
		model.addObserver(o);
	}
	
	public abstract String[] toStringArray();
	
	public int getCurrentPlayer() {
		return model.getCurrentPlayer();
	}
	public int getScore(int player) {
		return model.getScore(player);
	}
	public int getHousesPerPlayer() {
		return model.HOUSES_PER_PLAYER;
	}
	
//*****************************************************
// Controller Methods
//
	public void move(int house) {
		model.move(house);
	}
	public void quit() {
		model.quit();
	}
	public boolean isHouseEmpty(int house) {
		return model.isHouseEmpty(house);
	}
	public boolean isGameOver() {
		return model.isGameOver();
	}
}