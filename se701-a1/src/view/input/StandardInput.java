package view.input;

import java.util.Scanner;

import view.input_strategy.IUserInputStrategy;
import view.input_strategy.UserInputFactory;

public class StandardInput implements IMancalaInput {

	private static final String quit = "q";
	private static final String load = "l";
	private static final String save = "s";
	private static final String undo = "u";
	private static final String redo = "r";
//	private final int HOUSES_PER_PLAYER;
	private Scanner scan;
	
	public StandardInput(int housesPerPlayer) {
//		this.HOUSES_PER_PLAYER = housesPerPlayer;
		this.scan = new Scanner(System.in);
	}

	/**
	 * prompt the user to make a move, and returns an integer from 1 to 6 if the
	 * user made a valid move, prompts the user again if the move was invalid,
	 * or returns MancalaView.cancelResult if the user pressed the quit key..
	 */
	@Override
	public IUserInputStrategy promptPlayer(String name) {
		System.out.println(quit + " to quit, " + undo + " to undo, " + save + " to save, " + load + " to load.");
		System.out.print(name + "'s turn - Specify house number: ");
		
		while (true) {
			String input = scan.next();
			if (input.equals(quit)) {
				return UserInputFactory.quit();
//			} else if (input.equals(load)) {	
//				result = UserInputFactory.load();
//			} else if (input.equals(save)) {	
//				result = UserInputFactory.save();
			} else if (input.equals(undo)) {	
				return  UserInputFactory.undo();
			} else if (input.equals(redo)) {	
				return UserInputFactory.redo();
			} else {
				try {
					int house = Integer.valueOf(input);
					return UserInputFactory.move(house);
				} catch(NumberFormatException e) {
					System.out.println("%%Message: `" + input + "' is not a valid response.");
				}
			}
		}
	}

}
