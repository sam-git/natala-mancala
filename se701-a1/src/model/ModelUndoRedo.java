package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import model.abstractions.Player;
import model.abstractions.Player.PlayerMemento;

public class ModelUndoRedo {
	private final Stack<GameMemento> mementosForUndo;
	private final Stack<Integer> intsForRedo;
	private final GameModel model;
	
	public ModelUndoRedo(GameModel model) {
		this.mementosForUndo = new Stack<GameMemento>();
		this.intsForRedo = new Stack<Integer>();
		this.model = model;
	}
	
	public void saveUndoMemento(int house) {
		GameMemento memento = new GameMemento(model.current_player, model.intToPlayer);
		memento.setNextMove(house);
		mementosForUndo.add(memento);
	}
	
	public void clearRedos() {
		intsForRedo.clear(); //clear redos
	}
	
	public void undo() {
		GameMemento newState = mementosForUndo.pop();
		restoreFromMemento(newState);
		intsForRedo.add(newState.getNextMove());
	}
	
	public int redo() {
		return intsForRedo.pop();
	}
	
	public void restoreFromMemento(GameMemento memento){
		model.current_player = memento.getCurrentPlayer();

		int numberOfPlayers = memento.getPlayers().length;
		model.intToPlayer = new HashMap<Integer, Player>(numberOfPlayers);

		for (PlayerMemento p : memento.getPlayers()) {
			String name = p.getName();
			int playerNumber = p.getNumber();
			int houses[] = p.getHouses();
			int storeSeeds = p.getStoreSeedCount();
			model.createPlayer(playerNumber, houses, storeSeeds, name);
		}

		Player.joinPlayers(model.intToPlayer.values());
	}
	
	
	public static class GameMemento {
		private final int currentPlayer;
		private PlayerMemento players[];
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
 
        public PlayerMemento[] getPlayers() {
            return players;
        }

		public int getCurrentPlayer() {
			return currentPlayer;
		}

		public int getNextMove() {
			return nextMove;
		}

		public void setNextMove(int nextMove) {
			this.nextMove = nextMove;
		}
    }

}
