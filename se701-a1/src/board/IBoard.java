package board;

import java.util.Observer;

public interface IBoard {
	
	String[] toStringArray();
	int getCurrentPlayer();
	int getScore(int player);

	void addObserver(Observer o);
	
	boolean isHouseEmpty(int house);
	boolean isGameOver();
	void move(int house);
	void quit();
}