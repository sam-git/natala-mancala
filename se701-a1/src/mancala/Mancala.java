package mancala;

import java.util.LinkedList;
import java.util.List;

import utility.MockIO;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 */
public class Mancala {
	public static void main(String[] args) {
		new Mancala().play(new MockIO());
	}
	
	static final int cancelresult = -1;
	private MockIO io;
	private int current_player;
	private int[] houses = {0,4,4,4,4,4,4,0,4,4,4,4,4,4};
	
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
			int startingHouse = translateHouseToArrayIndex(response);
			int seeds = houses[startingHouse];
			houses[startingHouse] = 0;
			
			int currentHouse = (startingHouse)%14;
			for (int i = 0; i < seeds; i++) {
				currentHouse = (currentHouse + 1) % 14;
				houses[currentHouse]++;
			}
			//check for land on store and switch player
			if (!houseIsPlayersStore(currentHouse)) { 
				this.current_player = ((this.current_player) % 2) + 1;
			//check for land on own empty house
			} else if (houseIsPlayersStore(currentHouse)){
				if (this.current_player == 1) {
					//
				}
			}
			
			
			if(gameIsOver()) {
				break;
			} else {
				printBoard();
				response = promptPlayer();
			}
		}
		gameOver();
	}
	
	
	
	private boolean gameIsOver() {
		boolean answer = false;
		if (houses[1] + houses[2] + houses[3] + houses[4] + houses[5] + houses[6] == 0 ) {
			answer = true;
		} else if (houses[8] + houses[9] + houses[10] + houses[11] + houses[12] + houses[13] == 0 ) {
			answer = true;
		}
		return answer;
	}



	private boolean houseIsPlayersStore(int currentHouse) {
		boolean answer = false;
		if (current_player == 1 & currentHouse == 7) {
			answer = true;
		} else if (current_player == 2 & currentHouse == 0) {
			answer = true;
		}
		return answer;
	}



	private void gameOver() {
		// TODO Auto-generated method stub
		io.println("Game over");
		printBoard();
		
	}



	private int promptPlayer() {
		String prompt = "Player " + this.current_player + "'s turn - Specify house number or 'q' to quit: ";
		return io.readInteger(prompt, 0, 6, cancelresult, "q");
	}



	private int translateHouseToArrayIndex(int response) {
		if (current_player == 1) {
			return response;
		} else {
			return response + 7;
		}
	}



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
