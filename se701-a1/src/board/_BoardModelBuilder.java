package board;

public class _BoardModelBuilder {
	//Default Values
	private int housesPerPlayer = 6;
	private int rowsPerPlayer = 1;
	private int startingSeedsPerHouse = 4;
	
	public _BoardModelBuilder housesPerPlayer(int x) {
		this.housesPerPlayer = x;
		return this;
	}
	public _BoardModelBuilder rowsPerPlayer(int x) {
		this.rowsPerPlayer = x;
		return this;
	}
	public _BoardModelBuilder startingSeedsPerHouse(int x) {
		this.startingSeedsPerHouse = x;
		return this;
	}
	public _BoardModel build() {
		return new _BoardModel(housesPerPlayer, rowsPerPlayer, startingSeedsPerHouse);
	}
}
