package view.ai;

import view.input.IMancalaInput;
import view.input_strategy.IInputStrategy;
import view.input_strategy.UserInputFactory;
import view.model_view.AbstractModelView;

public class ImitatorBot extends AbstractModelView implements IMancalaInput{
	private final String name;
	
	private int nextMove = 1;
	private int playerNumber;

	public ImitatorBot(String name) {
		this.name = name;
	}

	@Override
	public IInputStrategy getAction() {
		System.out.println(name + " is thinking.");
		pretendToThink();
		System.out.println(name + " says " + nextMove);
		pauseForOtherPlayerToSeeMove();
		return UserInputFactory.move(nextMove);
	}

	private void pauseForOtherPlayerToSeeMove() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void pretendToThink() {
		for (int i = 1; i < 4; i++) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(".");
		}
	}

	@Override
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
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
		this.nextMove++;
	}

}
