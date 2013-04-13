package board;

import java.util.HashMap;
import java.util.Map;

public class Player {
	private static int housesCount = Integer.MIN_VALUE;
	public static void setHousesCount(int housesCount) {
		if (Player.housesCount != Integer.MIN_VALUE) {
			throw new RuntimeException("Player.housesCount is already set.");
		} else if (housesCount < 1) {
			throw new RuntimeException("Player.housesCount must be > 0.");
		}
        Player.housesCount = housesCount;
    }
	public static int getHousesCount() {
		if (Player.housesCount == Integer.MIN_VALUE) {
			throw new RuntimeException("Player.housesCount has not been set.");
		}
		return Player.housesCount;
	}
	
	private String name;
	private Store store;
	private Map<Integer, House> intToHouse;
	
	public Player(String name) {
		this.name = name;
		this.intToHouse = new HashMap<Integer, House>();
		this.store = new Store(this);
		
		this.createHouses();
		this.getLastHouse().setNextPit(this.store);
	}
	
	public int getSeedCount(int house) {
		return intToHouse.get(house).getSeedCount();
	}
	
	public String getName() {
		return name;
	}

	public int getScore() {
		int score = 0;
		for (House house : intToHouse.values()) {
			score += house.getSeedCount();	
		}
		score += this.store.getSeedCount();
		return score;
	}
	
	public static void join(Player p1, Player p2) {
		p1.store.setNextPit(p2.intToHouse.get(1));
		p2.store.setNextPit(p1.intToHouse.get(1));
		
		for (Map.Entry<Integer, House> entry : p1.intToHouse.entrySet()) {
		    int p1HouseInt = entry.getKey();
		    House p1House = entry.getValue();
		    House p2House = p2.intToHouse.get(Player.getHousesCount() + 1 - p1HouseInt);
		    p1House.setOppositeHouse(p2House);
		    p2House.setOppositeHouse(p1House);
		}
	}

///////////////////////////////////////////////////////////////
	
	private void createHouses(){
		House.Builder hb = new House.Builder(this);
		for (int i = 0; i < Player.getHousesCount(); i++) {
			addHouseToMap(hb.buildHouse());
		}
	}
	
	private void addHouseToMap(House house) {
		intToHouse.put(intToHouse.size()+1, house);
	}
	
	private PitClass getLastHouse(){
		return intToHouse.get(Player.getHousesCount());
	}
}