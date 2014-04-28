package view.ai;

import view.input_strategy.IInputStrategy;
import view.input_strategy.UserInputFactory;

public class ImitatorBot extends AbstractBot {
	private final String name;
	
	private int nextMove = 1;
	public ImitatorBot(String name) {
		this.name = name;
	}

	@Override
	public IInputStrategy getAction() {
		System.out.print(name + " is thinking.");
		pretendToThink();
		System.out.println(name + " says " + nextMove);
		pauseForOtherPlayerToSeeMove();
		return UserInputFactory.move(nextMove++);
	}

	@Override
	public void moveStarted(int player, int house) {
		if (this.playerNumber != player) {
			this.nextMove = house;
		}
	}
	
	@Override
	public void invalidHousePrompt(int house) {
		this.nextMove = 1;
	}
	
	@Override
	public void emptyHousePrompt(int player) {
	}

}
