package mancala;

import utility.MockIO;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 */
public class Mancala {
	public static void main(String[] args) {
		new Mancala().play(new MockIO());
	}
	
	private static final int cancelresult = -1;
	private MockIO io;
	private int current_player;
	private int[] houses = {0,4,4,4,4,4,4,0,4,4,4,4,4,4};
	//private static final int[][] opposites = {{1,13}, {2,12}, {3,11}, {4,10}, {5,9}, {6, 8}, {8,6}, {9,5}, {10, 4}, {11,3}, {12,2}, {13,3}};
	private static final int[] opposites = {-1, 13, 12, 11, 10, 9, 8, -1, 6, 5, 4, 3, 2, 1};
	
	
	public void play(MockIO io) {
		
//		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
//		io.println("| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |");
//		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
//		io.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
//		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
//		io.println("Player 1's turn - Specify house number or 'q' to quit: ");
		
		this.io = io;
		
		this.current_player = 1;
		printBoard();
		int response = promptPlayer();
		
		while (response != cancelresult) {
			int startingHouseIndex = translateUsersChoiceToArrayIndex(response);
			
			//check if is house is empty
			if (houses[startingHouseIndex] == 0) {
				io.println("House is empty. Move again.");
				printBoard();
				response = promptPlayer();
				continue;				
			}
			
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
			
			if(isGameOver()) {
				printGameOverBoard();
				printScores();
				return; //exit game
			} else {
				printBoard();
				response = promptPlayer();
			}
		}
		printGameOverBoard();
	}
	
	/*
	 * print the score and the winner at the end of a completed game
	 */
	private void printScores() {
		int player1 = sumHouses(1,7);
		int player2 = sumHouses(8,13) + this.houses[0];
		io.println("	player 1:" + player1);
		io.println("	player 2:" + player2);
		if (player1>player2) {
			io.println("Player 1 wins!");
		} else if (player2>player1){
			io.println("Player 2 wins!");			
		} else {
			io.println("A tie!");
		}
	}

	/*
	 * returns the array index of the house opposite currentHouse
	 */
	private int getOppositHouse(int currentHouse) {
		return opposites[currentHouse];
	}

	/*
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

/*
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


/*
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

/*
 * return true if the arg currentHouse is an array index for 
 * a house belonging to the player whose turn it currently is
 */
	private boolean houseIsPlayersStore(int currentHouse) {
		boolean answer = false;
		if (current_player == 1 & currentHouse == 7) {
			answer = true;
		} else if (current_player == 2 & currentHouse == 0) {
			answer = true;
		}
		return answer;
	}

/*
 * Print the final board of the game once it is over.
 * Included the text "Game Over" before the board.
 */
	private void printGameOverBoard() {
		// TODO Auto-generated method stub
		io.println("Game over");
		printBoard();
		
	}

/*
 * prompt the user to make a move, and returns an integer from 0 to 6 
 * if the user made a valid move, or -1 for an invalid move
 */
	private int promptPlayer() {
		String prompt = "Player " + this.current_player + "'s turn - Specify house number or 'q' to quit: ";
		return io.readInteger(prompt, 0, 6, cancelresult, "q");
	}

/*
 * Return the index of houses[] that corresponds to the users specified house
 * Used at the start of the turn to selected the array index
 */
	private int translateUsersChoiceToArrayIndex(int userInputtedInt) {
		if (current_player == 1) {
			return userInputtedInt;
		} else {
			return userInputtedInt + 7;
		}
	}

/*
 * Print an ASCII representation of the current board to io
 */
	private void printBoard() {
//		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
//		io.println("| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |");
//		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
//		io.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
//		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
//		io.println("Player 1's turn - Specify house number or 'q' to quit: ");
		
//		StringBuffer sb = new StringBuffer();
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		String line2 = String.format("| P2 | 6[%2d] | 5[%2d] | 4[%2d] | 3[%2d] | 2[%2d] | 1[%2d] | %2d |", 
					this.houses[13], this.houses[12], this.houses[11], this.houses[10], this.houses[9], this.houses[8], this.houses[7]);
		io.println(line2);
		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		String line4 = String.format("| %2d | 1[%2d] | 2[%2d] | 3[%2d] | 4[%2d] | 5[%2d] | 6[%2d] | P1 |", 
				this.houses[0], this.houses[1], this.houses[2], this.houses[3], this.houses[4], this.houses[5], this.houses[6]);
		io.println(line4);
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		
//		io.print(sb.toString());
	}
}
