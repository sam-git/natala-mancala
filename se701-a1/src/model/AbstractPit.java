package model;

public abstract class AbstractPit {
	
	private int seedCount;
	private AbstractPit nextPit;
	private Player owner;

	public AbstractPit(Player owner, int startingSeeds) {
		this.owner = owner;
		this.seedCount = startingSeeds;
	}
	
	public abstract void addOneSeedFromCollection(SeedCollection s);
	
//	protected void addSeedCollection(SeedCollection s){
//		this.seedCount += s.getSeedCount();
//	}
	
//	protected SeedCollection takeAllSeeds() {
//		SeedCollection s = new SeedCollection(seedCount, owner);
//		seedCount = 0;
//		return s;
//	}
	
	protected Player getOwner() {
		return owner;
	}
	
	protected void addOneSeed() {
		seedCount++;
	}
	
	protected AbstractPit getNextPit() {
		return nextPit;
	}

	protected void setNextPit(AbstractPit nextPit) {
		this.nextPit = nextPit;
	}

	protected int getSeedCount() {
		return seedCount;
	}
	
	protected void setSeedCount(int seeds) {
		this.seedCount = seeds;
	}
}
