package model;


import java.util.HashMap;
import java.util.Map;

import rules.IGameRules;

public class Player {
	private String name;
	private Store store;
	private Map<Integer, House> intToHouse;
	
	public Player(IGameRules rules, String name) {
		this.name = name;
		this.intToHouse = new HashMap<Integer, House>();
		this.store = new Store(this, rules.getStartingSeedsPerStore());
		
		this.createHouses(rules); //links all houses and store
	}
	
	public boolean move(int house) {
		House h = intToHouse.get(house);
		SeedCollection s = h.removeAllSeeds();
		AbstractPit pit = h;
		while (!s.isEmpty()) {
			 pit = pit.getNextPit();
			 pit.addOneSeedFromCollection(s);
		}
		return (this.store == pit); //endedOnOwnStore?
	}
	
	public int getSeedCount(int house) {
		return intToHouse.get(house).getSeedCount();
	}
	
	public int getStoreSeedCount() {
		return store.getSeedCount();
	}
	
	public String getName() {
		return name;
	}

	public int getScore() {
		int score = 0;
		score += getTotalSeedsInHouses();
		score += this.store.getSeedCount();
		return score;
	}
	
	public int getTotalSeedsInHouses() {
		int seeds = 0;
		for (House house : intToHouse.values()) {
			seeds += house.getSeedCount();	
		}
		return seeds;
	}
	
	public static void join(Player p1, Player p2, IGameRules rules) {
		p1.store.setNextPit(p2.intToHouse.get(1));
		p2.store.setNextPit(p1.intToHouse.get(1));
		
		//set Opposite Houses
		for (Map.Entry<Integer, House> entry : p1.intToHouse.entrySet()) {
		    int p1HouseInt = entry.getKey();
		    House p1House = entry.getValue();
		    House p2House = p2.intToHouse.get(rules.getHousesPerPlayer() + 1 - p1HouseInt);
		    p1House.setOppositeHouse(p2House);
		    p2House.setOppositeHouse(p1House);
		}
	}

	public Store getStore() {
		return store;
	}
	
///////////////////////////////////////////////////////////////
	
	private void createHouses(IGameRules rules){		
		House.Builder hb = new House.Builder(this, rules.getStartingSeedsPerHouse());
		for (int i = 0; i < rules.getHousesPerPlayer(); i++) {
			intToHouse.put(intToHouse.size()+1, hb.buildHouse());
		}
		//join last pit to store
		 intToHouse.get(intToHouse.size()).setNextPit(this.store);
	}
}