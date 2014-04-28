package view.ai;

import view.input.IMancalaInput;
import view.input_strategy.IInputStrategy;
import view.model_view.AbstractModelView;

public abstract class AbstractBot extends AbstractModelView implements IMancalaInput {
	
	protected int playerNumber;
	private int sleepBeforeMove = 300;
	private int thinkTicks = 3;
	private int thinkTickDuration = 100;

	@Override
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	@Override
	public abstract IInputStrategy getAction();

	public void pauseForOtherPlayerToSeeMove() {
		try {
			Thread.sleep(sleepBeforeMove);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pretendToThink() {
		for (int i = 0; i < thinkTicks; i++) {
			try {
				Thread.sleep(thinkTickDuration);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(".");
		}
	}
}
