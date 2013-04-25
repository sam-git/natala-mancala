package mancala;

import java.util.HashMap;
import java.util.Map;

import model.Model;
import utility.IO;
import utility.MockIO;
import view.ai.DumbBot;
import view.ai.ImitatorBot;
import view.input.IMancalaInput;
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
	Map<Integer, IMancalaInput> intToUser = new HashMap<Integer, IMancalaInput>(2);
	
	public static void main(String[] args) {
		checkArgsForPropFiles(args);
		new Mancala().play(new MockIO());
	}

	public void play(IO io) {

		Model m = new Model(gameProperties);
		IOModelView view = new IOModelView(m, io);	
		if (boardProperties != null) view.setProperties(boardProperties);
		
		m.addObserver(view);
		
//		IMancalaInput p1 = new IOInput(io, m.HOUSES_PER_PLAYER);
//		IMancalaInput p2 = new IOInput(io, m.HOUSES_PER_PLAYER);

//		IMancalaInput p1 = new StandardInput("Sam");
//		IMancalaInput p2 = new StandardInput("Ewan");
		
		IMancalaInput p1 = new DumbBot("Alice", m.HOUSES_PER_PLAYER);
//		IMancalaInput p2 = new DumbBot("Bob", m.HOUSES_PER_PLAYER);
		
		ImitatorBot p2 = new ImitatorBot("Clark");
		m.addObserver(p2);
		
		setPlayer(1, p1);
		setPlayer(2, p2);
		
		//game loop
		m.startGame();
		while (!m.isGameOver())  {
			IMancalaInput player = intToUser.get(m.currentPlayer()); 
			player.getAction().executeOn(m);
		}
	}

	private void setPlayer(int i, IMancalaInput player) {
		intToUser.put(i, player);
		player.setPlayerNumber(i);
		
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