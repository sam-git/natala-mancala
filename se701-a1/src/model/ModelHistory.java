package model;

import java.util.Map;
import java.util.Stack;

import model.abstractions.Player;
import model.abstractions.Player.PlayerMemento;

public class ModelHistory {
	private final Stack<GameMemento> mementosForUndo;
	private final Stack<Integer> intsForRedo;
	
	public ModelHistory() {
		this.mementosForUndo = new Stack<GameMemento>();
		this.intsForRedo = new Stack<Integer>();
	}
	
	public void saveStateForUndo(int house, int currentPlayer, Map<Integer, Player> intToPlayer) {
		GameMemento memento = new GameMemento(currentPlayer, intToPlayer);
		memento.setNextMove(house);
		mementosForUndo.add(memento);
	}
	
	public void clearRedos() {
		intsForRedo.clear();
	}
	
	public int popRedoMove() {
		if (intsForRedo.isEmpty()) {
			return -1;
		} else {
			return intsForRedo.pop();
		}
	}

	public boolean undo(ModelLogic gameLogic) {
		if (mementosForUndo.isEmpty()) {
			return false;
		} else {
			GameMemento newState = mementosForUndo.pop();
			gameLogic.restoreFromMemento(newState);
			intsForRedo.add(newState.getNextMove());
			return true;
		}
	}
	
	public static class GameMemento {
		private final int currentPlayer;
		private final PlayerMemento players[];
		private int nextMove;
		
        public GameMemento(int currentPlayer, Map<Integer, Player> intToPlayer) {
            this.currentPlayer = currentPlayer;
            this.players = new PlayerMemento[intToPlayer.size()];
            
            for (Map.Entry<Integer, Player> entry : intToPlayer.entrySet()) {
            	PlayerMemento playerMemento = entry.getValue().saveToMemento();
            	playerMemento.setPlayerNumber(entry.getKey());
            	players[entry.getKey() - 1] = playerMemento;
    		}
        }
        public PlayerMemento[] getPlayers() { return players; }
		public int getCurrentPlayer() { return currentPlayer; }
		public int getNextMove() { return nextMove; }
		public void setNextMove(int nextMove) { this.nextMove = nextMove; }
    }
}
