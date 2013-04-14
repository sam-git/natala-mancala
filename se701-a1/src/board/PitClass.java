package board;

public abstract class PitClass {
	
	private int seedCount;
	private PitClass nextPit;
	private Player owner;

	public PitClass(Player owner, int startingSeeds) {
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
	
	public PitClass getNextPit() {
		return nextPit;
	}

	protected void setNextPit(PitClass nextPit) {
		this.nextPit = nextPit;
	}

	public int getSeedCount() {
		return seedCount;
	}
}
