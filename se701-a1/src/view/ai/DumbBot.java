package view.ai;

import view.input.IMancalaInput;
import view.input_strategy.IInputStrategy;
import view.input_strategy.UserInputFactory;

public class DumbBot implements IMancalaInput{
	private final String name;
	private final int HOUSES_PER_PLAYER;
	
	private int house;
	private int playerNumber;

	public DumbBot(String name, int housesPerPlayer) {
		this.name = name;
		this.HOUSES_PER_PLAYER = housesPerPlayer;
	}

	@Override
	public IInputStrategy getAction() {
		System.out.println(name + " is thinking.");
		pretendToThink();
		house = (house++ % HOUSES_PER_PLAYER) + 1;
		System.out.println(name + " says " + house);
		pauseForOtherPlayerToSeeMove();
		return UserInputFactory.move(house);
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

}
