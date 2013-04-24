package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import model.abstractions.Player;
import model.abstractions.Player.PlayerMemento;

public class ModelUndoRedo {
	private final Stack<GameMemento> mementosForUndo;
	private final Stack<Integer> intsForRedo;
	
	public ModelUndoRedo() {
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
		return intsForRedo.pop();
	}

	public void undo(ModelLogic modelController) {
		GameMemento newState = mementosForUndo.pop();
		restoreFromMemento(newState, modelController);
		intsForRedo.add(newState.getNextMove());
	}
	
	public void restoreFromMemento(GameMemento memento, ModelLogic model){
		int currentPlayer = memento.currentPlayer;
		
		int numberOfPlayers = memento.getPlayers().length;
		Map<Integer, Player>intToPlayer = new HashMap<Integer, Player>(numberOfPlayers);

		for (PlayerMemento p : memento.getPlayers()) {
			int playerNumber = p.getNumber();
			int houses[] = p.getHouses();
			int storeSeeds = p.getStoreSeedCount();
			
			Player player = new Player(houses, storeSeeds);
			intToPlayer.put(playerNumber, player);	
		}
		Player.joinPlayers(intToPlayer.values());
		model.restore(currentPlayer, intToPlayer);
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
