package mancala;

public interface IMancalaModel {

	//methods to change game state
	public void move(int house);
	public void quit();
	
	//methods to query game state
	public boolean isGameOver();
	public int getCurrentPlayer();
	public boolean isHouseEmpty(int house);
	public int getSeedCount(int player, int house);
	public int getScore(int player);
}
