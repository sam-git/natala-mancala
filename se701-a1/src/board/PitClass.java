package board;

public abstract class PitClass {
	
	private int seedCount;
	private PitClass nextPit;
	private Player owner;

	public PitClass(Player owner, int startingSeeds) {
		this.owner = owner;
		this.seedCount = startingSeeds;
	}
	
	protected PitClass getNextPit() {
		return nextPit;
	}

	protected void setNextPit(PitClass nextPit) {
		this.nextPit = nextPit;
	}

	public int getSeedCount() {
		return seedCount;
	}
}
