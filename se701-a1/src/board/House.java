package board;

public class House extends PitClass {
	public static int startingSeedCount;
	
	private House oppositeHouse;
	
	public House(Player owner) {
		super(owner);
		super.setStartingSeedCount(startingSeedCount);
	}
	
	public void setOppositeHouse(House oppositeHouse) {
		this.oppositeHouse = oppositeHouse;
	}

	
	public static class Builder {
		private final Player owner;
		private House previousHouse;
		
		public Builder(Player owner) {
			this.owner = owner;
			this.previousHouse = null;
		}
		
		public House buildHouse() {
			House house = new House(owner);
			if (previousHouse != null) {
				previousHouse.setNextPit(house);
			}
			return house;
		}
	}	
}
