package view.input;

import java.util.Scanner;

import view.input_strategy.IInputStrategy;
import view.input_strategy.UserInputFactory;

public class StandardInput implements IMancalaInput {

	private static final String quit = "q";
	private static final String load = "l";
	private static final String save = "s";
	private static final String undo = "u";
	private static final String redo = "r";
	private final String name;
	private Scanner scan;
	private int playerNumber;
	

	public StandardInput(String name) {
		this.scan = new Scanner(System.in);
		this.name = name;
	}

	/**
	 * prompt the user to make a move, and returns an integer from 1 to 6 if the
	 * user made a valid move, prompts the user again if the move was invalid,
	 * or returns MancalaView.cancelResult if the user pressed the quit key..
	 */
	@Override
	public IInputStrategy getAction() {
//		System.out.println(quit + " to quit, " + undo + " to undo, " + save + " to save, " + load + " to load.");
		System.out.println(quit + " to quit, " + undo + " to undo, " + redo + " to redo.");
		System.out.print("P" + playerNumber + " :" + name + "'s turn - Specify house number: ");
		
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

	@Override
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;		
	}
}
