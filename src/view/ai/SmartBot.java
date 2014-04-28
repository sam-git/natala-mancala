package view.ai;

import model.Model;
import view.input_strategy.IInputStrategy;
import view.input_strategy.UserInputFactory;

public class SmartBot extends AbstractBot {

	private Model model;
	private Object name;
	
	public SmartBot(String name, Model m) {
		this.model = m;
		this.name = name;

	}

	@Override
	public IInputStrategy getAction() {
		int house = getBestMove();
		System.out.print(name + " is thinking.");
		pretendToThink();
		System.out.println(name + " says " + house);
		pauseForOtherPlayerToSeeMove();
		
		return UserInputFactory.move(house);
	}

	private int getBestMove() {
		
		int[] outcomes = new int[model.HOUSES_PER_PLAYER];
		
		for(int i = 1; i <= outcomes.length; i++) {
//			System.out.println("Testing store " + i);
			Model model = this.model.getCopy();
			model.move(i);
			outcomes[i-1] = model.getStoreSeedCount(playerNumber);
//			System.out.println("Store value would be " + outcomes[i-1] );
		}
		
		int bestMove = 0;
		int bestScore = Integer.MIN_VALUE;
		for(int i = 1; i <= outcomes.length; i++) {
			if (outcomes[i-1] > bestScore) {
				bestMove = i;
				bestScore = outcomes[i-1];
			}
		}
		return bestMove;	
	}
}
