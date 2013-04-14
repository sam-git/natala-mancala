package model;

public class House extends AbstractPit {
	
	private House oppositeHouse;
	
	public House(Player owner, int startingSeedCount) {
		super(owner, startingSeedCount);
	}
	
	public void deposit(SeedCollection s) {
		if (s.isLastSeed() && this.isHouseEmpty() && this.getOwner() == s.getOwner()) {
			
			AbstractPit store = this.getOwner().getStore();
			s.decrement();
			store.increment();
			
			SeedCollection steal = this.getOppositeHouse().takeAllSeeds();
			store.addSeedCollection(steal);
		} else {
			s.decrement();
			this.increment();
		}
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
