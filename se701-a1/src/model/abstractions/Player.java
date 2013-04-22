package model.abstractions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Player  {
	
	private String name;
	private Store store;
	private Map<Integer, House> intToHouse;
	
	public Player(int[] houses, int storeSeedCount, String name) {
		this.name = name;
		this.store = new Store(this, storeSeedCount);
		
		this.intToHouse = new HashMap<Integer, House>();
		this.createHouses(houses); //links all houses and store
	}
	
	private void createHouses(int[] houses){
		House.Builder hb = new House.Builder(this);
		for (int i = 0; i < houses.length; i++) {
			intToHouse.put(intToHouse.size()+1, hb.buildHouse(houses[i]));
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

	private static void joinOppositeHouses(Player playerOnSide1, Player playerOnSide2) {
		int housesPerPlayer = playerOnSide1.intToHouse.size();
		for (Map.Entry<Integer, House> entry : playerOnSide1.intToHouse.entrySet()) {
		    int p1HouseInt = entry.getKey();
		    House p1House = entry.getValue();
		    House p2House = playerOnSide2.intToHouse.get(housesPerPlayer + 1 - p1HouseInt);
		    p1House.setOppositeHouse(p2House);
		    p2House.setOppositeHouse(p1House);
		}
	}

	public Store getStore() {
		return store;
	}
	
	public PlayerMemento saveToMemento() {
		int[] houses = new int[intToHouse.size()];
		for (int i = 0; i < intToHouse.size(); i++) {
			houses[i] = intToHouse.get(i+1).getSeedCount();
		}
		return new PlayerMemento(houses, this.store.getSeedCount(), this.name);
	}
	
	public static class PlayerMemento {
        private final int houses[];
        private final int store;
        private final String name;
        private int number;
 
        public PlayerMemento(int houses[], int storeSeedCount, String name) {
            this.houses = houses;
            this.store = storeSeedCount;
			this.name = name;
        }
        
        public void setPlayerNumber(int number) {
        	this.number = number;
        }
 
        public int[] getHouses() {
            return houses;
        }
        public int getStoreSeedCount(){
        	return store;
        }
        public int getNumber(){
        	return number;
        }

		public String getName() {
			return name;
		}
    }
}