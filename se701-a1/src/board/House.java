package board;

public class House extends PitClass {
	
	private House oppositeHouse;
	
	public House(Player owner, int startingSeedCount) {
		super(owner, startingSeedCount);
	}
	
	public void setOppositeHouse(House oppositeHouse) {
		this.oppositeHouse = oppositeHouse;
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
			return house;
		}
	}	
}
