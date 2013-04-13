package board;

public abstract class PitClass {
	
	private int seedCount;
	private PitClass nextPit;
	private Player owner;

	public PitClass(Player owner) {
		super();
		this.owner = owner;
	}
	
	protected void setStartingSeedCount(int seeds) {
		this.seedCount = seeds;
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
