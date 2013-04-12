package board;

public class BoardModelBuilder {
	//Default Values
	private int housesPerPlayer = 6;
	private int rowsPerPlayer = 1;
	private int startingSeedsPerHouse = 4;
	
	public BoardModelBuilder housesPerPlayer(int x) {
		this.housesPerPlayer = x;
		return this;
	}
	public BoardModelBuilder rowsPerPlayer(int x) {
		this.rowsPerPlayer = x;
		return this;
	}
	public BoardModelBuilder startingSeedsPerHouse(int x) {
		this.startingSeedsPerHouse = x;
		return this;
	}
	public BoardModel build() {
		return new BoardModel(housesPerPlayer, rowsPerPlayer, startingSeedsPerHouse);
	}
}
