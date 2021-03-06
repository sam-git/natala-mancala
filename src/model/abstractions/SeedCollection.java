package model.abstractions;


public class SeedCollection {
	private int seedCount;
	private Player owner;
	
	public SeedCollection(int seeds, Player owner) {
		if (seeds < 0) {
			throw new RuntimeException("Must be zero or more seeds to create a SeedCollection!");
		}
		this.seedCount = seeds;
		this.owner = owner;
	}
	
	public boolean isLastSeed() {
		return seedCount == 1;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public int removeAllSeeds() {
		int removedSeeds = this.seedCount;
		this.seedCount = 0;
		return removedSeeds;
	}
	
	public void removeOneSeed() {
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
