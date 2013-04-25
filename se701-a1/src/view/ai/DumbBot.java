package view.ai;

import view.input_strategy.IInputStrategy;
import view.input_strategy.UserInputFactory;

public class DumbBot extends AbstractBot {
	private final String name;
	private final int HOUSES_PER_PLAYER;
	
	private int house;
	public DumbBot(String name, int housesPerPlayer) {
		this.name = name;
		this.HOUSES_PER_PLAYER = housesPerPlayer;
	}

	@Override
	public IInputStrategy getAction() {
		System.out.print(playerNumber + " : " + name + " is thinking.");
		pretendToThink();
		house = (house++ % HOUSES_PER_PLAYER) + 1;
		System.out.println(name + " says " + house);
		pauseForOtherPlayerToSeeMove();
		return UserInputFactory.move(house);
	}

}
