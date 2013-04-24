package mancala;

import java.util.HashMap;
import java.util.Map;

import model.Model;
import utility.IO;
import utility.MockIO;
import view.input.IMancalaInput;
import view.input.InputWithLoadSave;
import view.model_view.IOModelView;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 * This class acts as a controller in the MVC design pattern applied to the game.
 */
public class Mancala {
	
	private static final String gamePropertiesExtension = ".rules";
	private static final String asciiPropertiesExtension = ".ascii";
	private static String gameProperties;
	private static String boardProperties;
	
	public static void main(String[] args) {
		checkArgsForPropFiles(args);
		new Mancala().play(new MockIO());
	}

	public void play(IO io) {

		Model m = new Model(gameProperties);
		IOModelView view = new IOModelView(m, io);	
		if (boardProperties != null) view.setProperties(boardProperties);
		
		m.addObserver(view);
		
//		IMancalaInput p1 = new IOInput(io, m.HOUSES_PER_PLAYER, "Player 1");
//		IMancalaInput p2 = new IOInput(io, m.HOUSES_PER_PLAYER, "Player 2");

		IMancalaInput p1 = new InputWithLoadSave("Player 1");
		IMancalaInput p2 = new InputWithLoadSave("Player 2");
//		IMancalaInput p2 = new KalahBot("Bert", m.HOUSES_PER_PLAYER);
		
		Map<Integer, IMancalaInput> intToUser = new HashMap<Integer, IMancalaInput>(2);
		intToUser.put(1, p1);
		intToUser.put(2, p2);
		
		//game loop
		m.startGame();
		while (!m.isGameOver())  {
			IMancalaInput player = intToUser.get(m.currentPlayer()); 
			player.getAction().executeOn(m);
		}
	}

	private static void checkArgsForPropFiles(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				if (arg.contains(gamePropertiesExtension) && gameProperties  == null) {
					gameProperties = arg; 
					continue; //arg is a gameProperty, so don't need to check arg again.
				}
				if (arg.contains(asciiPropertiesExtension) && boardProperties == null) {
					boardProperties = arg;
				}
			}
		}
	}
}