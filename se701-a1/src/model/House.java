package model;

public class House extends AbstractPit  {
	
	private House oppositeHouse;
	
	public House(Player owner, int startingSeedCount) {
		super(owner, startingSeedCount);
	}
	
	public void addOneSeedFromCollection(SeedCollection s) {
		if (s.isLastSeed() && this.isHouseEmpty() && this.getOwner() == s.getOwner()) {
			
			Store store = this.getOwner().getStore();
			store.addSeedCollection(s);
			
			SeedCollection stolenSeeds = this.getOppositeHouse().removeAllSeeds();
			store.addSeedCollection(stolenSeeds);
		} else {
			s.removeOneSeed();
			this.addOneSeed();
		}
	}
	
	protected SeedCollection removeAllSeeds() {
		SeedCollection s = new SeedCollection(this.getSeedCount(), this.getOwner());
		this.setSeedCount(0);
		return s;
	}

	public boolean isHouseEmpty() {
		return this.getSeedCount() == 0;
	}
	
	public void setOppositeHouse(House oppositeHouse) {
		this.oppositeHouse = oppositeHouse;
	}

	private House getOppositeHouse() {
		return oppositeHouse;
	}
	
	public static class Builder {
		private final Player owner;
		private final int startingSeedCount;
		private House previousHouse;
		
		public Builder(Player owner, int startingSeedCount) {
			this.owner = owner;
			this.previousHouse = null;
			this.startingSeedCount = startingSeedCount;
		}
		
		public House buildHouse() {
			House house = new House(owner, startingSeedCount);
			if (previousHouse != null) {
				previousHouse.setNextPit(house);
			}
			previousHouse = house;
			return house;
		}
	}
}
