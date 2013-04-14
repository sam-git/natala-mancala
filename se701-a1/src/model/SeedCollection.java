package model;

public class SeedCollection {
	private int seedCount;
	private Player owner;
	
	public SeedCollection(int seeds, Player owner) {
		this.seedCount = seeds;
		this.owner = owner;
	}
	
	public int getSeedCount() {
		return seedCount;
	}
	
	public boolean isLastSeed() {
		return seedCount == 1;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public void decrement() {
		if (seedCount > 0) {
			seedCount--;
		} else {
			throw new RuntimeException("Cannot decrement empty SeedCollection");
		}
	}
	
	public boolean isEmpty() {
		return seedCount==0;
	}
}
