package model.abstractions;


public class Store extends AbstractPit {
	Player player;

	public Store(Player owner, int startingSeeds) {
		super(owner, startingSeeds);
	}

	@Override
	public void addOneSeedFromCollection(SeedCollection s) {
		if (s.getOwner() == this.getOwner()) {
			this.addOneSeed();
			s.removeOneSeed();
		}
	}
	
	protected void addSeedCollection(SeedCollection s){
		this.setSeedCount(this.getSeedCount() + s.removeAllSeeds());
	}
}
