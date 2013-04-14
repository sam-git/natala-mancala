package gameType;

import board.GameModel;

public interface GameRules {
	
	public String[] toStringArray(GameModel m);
	public int getHousesPerPlayer();
	public int getStartingSeedsPerHouse();
	public int getStartingSeedsPerStore();
	public String getPlayerOneName();
	public String getPlayerTwoName();
	public int getStartingPlayer();
}