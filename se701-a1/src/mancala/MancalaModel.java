/**
 * 
 */
package mancala;

/**
 * @author Sam
 *
 */


class MancalaModel {
	private int[] houses = {0,4,4,4,4,4,4,0,4,4,4,4,4,4};
	private int current_player = 1;
	
    // Private constructor prevents instantiation from other classes
    private MancalaModel() { }

    /**
    * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
    * or the first access to SingletonHolder.INSTANCE, not before.
    */
    private static class SingletonHolder { 
            public static final MancalaModel INSTANCE = new MancalaModel();
    }

    public static MancalaModel getInstance() {
            return SingletonHolder.INSTANCE;
    }
    
    public void reset() {
    	houses = new int[] {0,4,4,4,4,4,4,0,4,4,4,4,4,4};
    	current_player = 1;
    }
    
    public int getCurrentPlayer(){
    	return current_player;
    }
    
    public int getCount(int player, int house) {
    	
    	if (house == 0 && player == 1) {
    		return houses[7];
    	} else if (house == 0 && player == 2) {
    		return houses[0];
    	} else {
    		int base = 0;
        	if (player == 2) {
        		base = 7;
        	}
    		return houses[base + house];
    	}
    }
    
    public int getScore(int player) {
    	int sum;
    	if (player == 1) {
    		sum = sumHouses(1,7);
    	} else {
    		sum = sumHouses(8,13) + this.houses[0];
    	}
		return sum;
    }
    
	/**
	 * Return the index of houses[] that corresponds to the users specified house
	 * Used at the start of the turn to selected the array index
	 */
	private int translateUsersChoiceToArrayIndex(int current_player, int userInputtedInt) {
		if (current_player == 1) {
			return userInputtedInt;
		} else {
			return userInputtedInt + 7;
		}
	}
	
	/**
	 * returns true if currentHouse is a house belonging to the current player
	 * does not return true is it is the players store
	 */
	private boolean houseIsPlayersHouse(int currentHouse) {
		boolean answer = false;
		if (this.current_player == 1) {
			if (currentHouse >= 1 && currentHouse <= 6) {
				answer = true;
			}
		} else if (this.current_player == 2) {
			if (currentHouse >= 8 && currentHouse <= 13) {
				answer = true;
			}
		}
		return answer;
	}
	
	/**
	 * return true if the arg currentHouse is an array index for 
	 * a house belonging to the player whose turn it currently is
	 */
	private boolean houseIsPlayersStore(int currentHouse) {
		boolean answer = false;
		if (this.current_player == 1 & currentHouse == 7) {
			answer = true;
		} else if (this.current_player == 2 & currentHouse == 0) {
			answer = true;
		}
		return answer;
	}
	
	/**
	 * returns the array index of the house opposite currentHouse
	 */
	private int getOppositHouse(int currentHouse) {
		final int[] opposites = {-1, 13, 12, 11, 10, 9, 8, -1, 6, 5, 4, 3, 2, 1};
		return opposites[currentHouse];
	}
	
	/**
	 * a helper method to sum a group of adjacent houses in the houses array.
	 * sums all houses between from and to inclusive.
	 */
	private int sumHouses(int from, int to) {
		int sum = 0;
		for (int i = from; i <= to; i++) {
			sum += this.houses[i];
		}
		return sum;
	}
	
	/**
	 * return true if the game over conditions are met
	 */
	private boolean isGameOver() {
		boolean answer = false;
		if (sumHouses(1,6) == 0 ) {
			answer = true;
		} else if (sumHouses(8,13) == 0 ) {
			answer = true;
		}
		return answer;
	}
    
    public boolean currentPlayerMove(int house) {
    	
    	int startingHouseIndex = translateUsersChoiceToArrayIndex(this.current_player, house);
    	
    	int seeds = houses[startingHouseIndex];
		houses[startingHouseIndex] = 0;
		
		int currentHouseIndex = (startingHouseIndex)%14;
		for (int i = 0; i < seeds; i++) {
			currentHouseIndex = (currentHouseIndex + 1) % 14;
			
			//skip if players land opponents base
			if (this.current_player == 1 && currentHouseIndex == 0) {
				i -= 1;
			} else if (this.current_player == 2 && currentHouseIndex == 7) {
				i -= 1;
			} else { //otherwise incremetn
				houses[currentHouseIndex]++;
			}
		}
		  
		//check for land on own empty house
		if (houseIsPlayersHouse(currentHouseIndex) && houses[currentHouseIndex] == 1){
			int oppositeHouse = getOppositHouse(currentHouseIndex);
			if (this.current_player == 1) {
				houses[7] += houses[oppositeHouse] + houses[currentHouseIndex];
			} else {
				houses[0] += houses[oppositeHouse] + houses[currentHouseIndex];
			}
			
			houses[currentHouseIndex] = 0;
			houses[oppositeHouse] = 0;
		}
		
		//check for land on store and switch player
		if (!houseIsPlayersStore(currentHouseIndex)) { 
			this.current_player = ((this.current_player) % 2) + 1;
		}
		
		return isGameOver();
    }
    
    
}

