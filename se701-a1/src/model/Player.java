package model;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import mancala.PropsLoader;

public class Player  {
	
	private final String name;
	private final String shortName;
	private Store store;
	private Map<Integer, House> intToHouse;
	
	public Player(Properties props, int playerNumber) {
		this.name = props.getProperty(playerNumber + "Name");
		this.shortName = props.getProperty(playerNumber + "ShortName");
		this.intToHouse = new HashMap<Integer, House>();
		this.store = new Store(this, PropsLoader.getInt(props, "startingSeedsPerStore"));
		
		this.createHouses(props); //links all houses and store
	}
	
	private void createHouses(Properties props){
		int startingSeedsPerHouse = PropsLoader.getInt(props, "startingSeedsPerHouse");
		int housesPerPlayer = PropsLoader.getInt(props, "housesPerPlayer");
		
		House.Builder hb = new House.Builder(this, startingSeedsPerHouse);
		for (int i = 0; i < housesPerPlayer; i++) {
			intToHouse.put(intToHouse.size()+1, hb.buildHouse());
		}
		//join last pit to store
		 intToHouse.get(intToHouse.size()).setNextPit(this.store);
	}

	public boolean move(int house) {
		House startingHouse = intToHouse.get(house);
		SeedCollection seedCollection = startingHouse.removeAllSeeds();
		AbstractPit currentPit = startingHouse;
		while (!seedCollection.isEmpty()) {
			 currentPit = currentPit.getNextPit();
			 currentPit.addOneSeedFromCollection(seedCollection);
		}
		return (this.store == currentPit); //did player end on own store?
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

	public String getShortName() {
		return shortName;
	}

	public int getScore() {
		return getTotalSeedsInHouses() + this.store.getSeedCount();
	}
	
	public int getTotalSeedsInHouses() {
		int seeds = 0;
		for (House house : intToHouse.values()) {
			seeds += house.getSeedCount();	
		}
		return seeds;
	}
	
//	public static void join(Player p1, Player p2) {
//		int housesPerPlayer = p1.intToHouse.size();
//		
//		p1.store.setNextPit(p2.intToHouse.get(1));
//		p2.store.setNextPit(p1.intToHouse.get(1));
//		
//		//set Opposite Houses
//		for (Map.Entry<Integer, House> entry : p1.intToHouse.entrySet()) {
//		    int p1HouseInt = entry.getKey();
//		    House p1House = entry.getValue();
//		    House p2House = p2.intToHouse.get(housesPerPlayer + 1 - p1HouseInt);
//		    p1House.setOppositeHouse(p2House);
//		    p2House.setOppositeHouse(p1House);
//		}
//	}
	
	public static void joinPlayers(Collection<Player> players) {
		assert (players.size() > 1);
		Iterator<Player> iterator = players.iterator();
		Player firstPlayer = iterator.next();
		Player currentPlayer = firstPlayer;
		while (iterator.hasNext()) {
			Player nextPlayer = iterator.next();
			currentPlayer.store.setNextPit(nextPlayer.intToHouse.get(1));
			joinOppositeHouses(currentPlayer, nextPlayer);
			currentPlayer = nextPlayer;
		}
		currentPlayer.store.setNextPit(firstPlayer.intToHouse.get(1));
	}

	private static void joinOppositeHouses(Player p1, Player p2) {
		int housesPerPlayer = p1.intToHouse.size();
		for (Map.Entry<Integer, House> entry : p1.intToHouse.entrySet()) {
		    int p1HouseInt = entry.getKey();
		    House p1House = entry.getValue();
		    House p2House = p2.intToHouse.get(housesPerPlayer + 1 - p1HouseInt);
		    p1House.setOppositeHouse(p2House);
		    p2House.setOppositeHouse(p1House);
		}
	}

	public Store getStore() {
		return store;
	}
}