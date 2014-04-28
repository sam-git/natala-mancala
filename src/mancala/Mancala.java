package mancala;

import java.util.HashMap;
import java.util.Map;

import model.Model;
import utility.IO;
import utility.MockIO;
import view.ai.AbstractBot;
import view.ai.DumbBot;
import view.ai.ImitatorBot;
import view.ai.SmartBot;
import view.input.IMancalaInput;
import view.input.IOInput;
import view.input.StandardInput;
import view.model_view.AbstractModelView;
import view.model_view.GUIModelView;
import view.model_view.GUIModelView2;
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

		final Model m = new Model(gameProperties);
		final IOModelView asciiView = new IOModelView(m, io);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	AbstractModelView guiView = new GUIModelView(asciiView.getBoardPrinter());
            	m.addObserver(guiView);
            }
        });
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	AbstractModelView guiView = new GUIModelView2(asciiView.getBoardPrinter());
            	m.addObserver(guiView);
            }
        });
		
		System.out.println("here2");
		if (boardProperties != null) asciiView.setProperties(boardProperties);		
		m.addObserver(asciiView);
		
		setPlayers(io, m);
		
		//game loop
		m.startGame();
		while (!m.isGameOver())  {
			IMancalaInput player = intToUser.get(m.currentPlayer()); 
			player.getAction().executeOn(m);
		}
	}

	private void setPlayers(IO io, Model m) {
		IMancalaInput p1; 
		IMancalaInput p2; 
		
//		p1 = new IOInput(io, m.HOUSES_PER_PLAYER);
//		p2 = new IOInput(io, m.HOUSES_PER_PLAYER);

		p1 = new StandardInput("Sam");
		p2 = new StandardInput("Ewan");
		
//		p1 = new DumbBot("Sam", m.HOUSES_PER_PLAYER);
//		p2 = new DumbBot("Bob", m.HOUSES_PER_PLAYER);
		
//		p1 = new ImitatorBot("Mike");
//		m.addObserver((ImitatorBot)p1);
//		p2 = new ImitatorBot("Mike");
//		m.addObserver((AbstractBot)p2);
		
//		p1 = new SmartBot("Super Bot 1", m);
//		m.addObserver((AbstractBot)p1);
//		p2 = new SmartBot("Super Bot 2", m);
//		m.addObserver((AbstractBot)p2);
		
		setPlayer(1, p1);
		setPlayer(2, p2);
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