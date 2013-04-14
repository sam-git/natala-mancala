package board;

public class Store extends PitClass{
	Player player;

	public Store(Player owner, int startingSeeds) {
		super(owner, startingSeeds);
	}

	@Override
	public void deposit(SeedCollection s) {
		if (s.getOwner() == this.getOwner()) {
			this.increment();
			s.decrement();
		}
	}
	
}
