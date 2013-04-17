package view.model;

import java.util.Observable;
import java.util.Observer;

import model.GameModel;
import model.event_strategy.IEventStrategy;

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
 * It is up to the programmer to add the view as on addObserver of the model. 
 * 
 * @author Sam
 *
 */
public abstract class AbstractModelView implements Observer {
	
	private final GameModel m;
	private final int HOUSES_PER_PLAYER;
	
	private String boardTopAndBottom;
	private String boardMiddle;
	private final String HOUSE_FORMAT = "%2d[%2d] ";
	private final String STORE_FORMAT = " %2d ";
	private final String STORE_NAME_FORMAT = " %2s ";
	private final String FENCE = "|";
	
	public AbstractModelView(GameModel model) {
		this.m = model;
		this.HOUSES_PER_PLAYER = model.getHousesPerPlayer();
		this.prepareBoard();
	}
	
	@Override
	/**
	 * Called by model when the game state changes.
	 * All views execute the given event strategy as necessary
	 */
	public final void update(Observable arg0, Object strategy) {
		((IEventStrategy)strategy).execute(this); //'this' is a concrete child class
	}
	
//*****************************************************
// The following methods can be overridden by subclasses if they wish to act upon
//	the events when notified of them by the Model.
	
	public void gameEnded() {}
	public void moveEnded() {}
	public void gameQuit() {}
	public void emptyHousePrompt() {}
	
	public String[] toStringArray() {
		String[] lines = new String[5];
		lines[0] = this.boardTopAndBottom;
		lines[1] = getPlayerTwoSide();
		lines[2] = this.boardMiddle;
		lines[3] = getPlayerOneSide();
		lines[4] = this.boardTopAndBottom;
		return lines;
	}
	
	private String getPlayerTwoSide() {
		StringBuffer sb = new StringBuffer(FENCE);
		sb.append(String.format(STORE_NAME_FORMAT + FENCE, "P2"));
		for (int i = HOUSES_PER_PLAYER; i > 0; i--) {
			sb.append(String.format(HOUSE_FORMAT + FENCE, i, m.getSeedCount(2, i)));
		}
		sb.append(String.format(STORE_FORMAT + FENCE, m.getStoreSeedCount(1)));		
		return sb.toString();
	}
	
	private String getPlayerOneSide() {
		StringBuffer sb = new StringBuffer(FENCE);
		sb.append(String.format(STORE_FORMAT + FENCE, m.getStoreSeedCount(2)));
		for (int i = 1; i <= HOUSES_PER_PLAYER; i++) {
			sb.append(String.format(HOUSE_FORMAT + FENCE, i, m.getSeedCount(1, i)));
		}
		sb.append(String.format(STORE_NAME_FORMAT + FENCE, "P1"));		
		return sb.toString();
	}
	
	private void prepareBoard(){
		StringBuffer sb = new StringBuffer();
		sb.append("+----");
		for (int i = 0; i < HOUSES_PER_PLAYER; i++) {
			sb.append("+-------");
		}
		sb.append("+----+");
		boardTopAndBottom = sb.toString();
		
		String gap = FENCE + String.format(STORE_NAME_FORMAT, "") + FENCE;
		boardMiddle = gap + sb.subSequence(gap.length(), sb.length()-gap.length()) + gap;
	}
	
	public int getFinalScore(int player) {
		return m.getFinalScore(player);
	}
}