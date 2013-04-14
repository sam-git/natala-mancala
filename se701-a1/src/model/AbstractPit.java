package model;

public abstract class AbstractPit {
	
	private int seedCount;
	private AbstractPit nextPit;
	private Player owner;

	public AbstractPit(Player owner, int startingSeeds) {
		this.owner = owner;
		this.seedCount = startingSeeds;
	}
	
	public abstract void deposit(SeedCollection s);
	
	public Player getOwner() {
		return owner;
	}
	
	protected void addSeedCollection(SeedCollection s){
		this.seedCount += s.getSeedCount();
	}
	
	protected void increment() {
		seedCount++;
	}
	
	protected SeedCollection takeAllSeeds() {
		SeedCollection s = new SeedCollection(seedCount, owner);
		seedCount = 0;
		return s;
	}
	
	public AbstractPit getNextPit() {
		return nextPit;
	}

	protected void setNextPit(AbstractPit nextPit) {
		this.nextPit = nextPit;
	}

	public int getSeedCount() {
		return seedCount;
	}
}
