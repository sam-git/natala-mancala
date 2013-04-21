package view.input;

import java.util.Scanner;

public class InputWithLoadSave implements IMancalaInput {

	private static final String quit = "q";
	private static final String load = "l";
	private static final String save = "s";
	private static final String undo = "u";
	private static final String redo = "r";
//	private final int HOUSES_PER_PLAYER;
	private Scanner scan;
	
	public InputWithLoadSave(int housesPerPlayer) {
//		this.HOUSES_PER_PLAYER = housesPerPlayer;
		this.scan = new Scanner(System.in);
	}

	/**
	 * prompt the user to make a move, and returns an integer from 1 to 6 if the
	 * user made a valid move, prompts the user again if the move was invalid,
	 * or returns MancalaView.cancelResult if the user pressed the quit key..
	 */
	@Override
	public int promptPlayer(String name) {
		System.out.println(quit + " to quit, " + undo + " to undo, " + save + " to save, " + load + " to load.");
		System.out.print(name + "'s turn - Specify house number: ");
		int result;
		
		while (true) {
			String input = scan.next();
			if (input.equals(quit)) {
				result = cancelResult;
				break;
			} else if (input.equals(load)) {	
				result = loadResult;
				break;
			} else if (input.equals(save)) {	
				result = saveResult;
				break;
			} else if (input.equals(undo)) {	
				result = undoResult;
				break;
			} else if (input.equals(redo)) {	
				result = redoResult;
				break;
			} else {
				try {
					result = Integer.valueOf(input);
					break;
				} catch(NumberFormatException e) {
					System.out.println("%%Message: `" + input + "' is not a valid response.");
				}
			}
		}
		return result;
	}

}
