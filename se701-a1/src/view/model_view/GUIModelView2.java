package view.model_view;

import javax.swing.*;

import mancala.PropsLoader;
import model.Model;

import java.awt.*;
import java.util.Map;


public class GUIModelView2 extends AbstractModelView {
	public static final String asciiPropsFolder = "asciiProperties/";

	JFrame Frame1 = new JFrame("Test Frame");
	JPanel bottomPanel = new JPanel();
	JLabel bottomLabel = new JLabel("bottom");
//	JButton b1 = new JButton("Click me");
//	JTextField topTextField = new JTextField(20);
	JPanel topPanel = new JPanel(new FlowLayout());
	JLabel topLabel = new JLabel("top");
	private ASCIIBoardPrinter board;

	public GUIModelView2() {
		topPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
		topPanel.add(topLabel);
//		topPanel.add(topTextField);
//		bottomPanel.add(b1);
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.green, 3));
		bottomPanel.add(bottomLabel);
		Frame1.add(topPanel);
		Frame1.add(bottomPanel);
		Frame1.setLayout(new FlowLayout());
		Frame1.setSize(400, 150);
		Frame1.setVisible(true);
		Frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}


	public GUIModelView2(ASCIIBoardPrinter asciiBoardPrinter) {
		this();
		this.board = asciiBoardPrinter;
		updateBoard();
	}

	
	public void gameStarted() {
		updateBoard();
	}


	public void moveEnded() {
		updateBoard();
	}
	public void gameQuit(int quittingPlayer) {}
	public void emptyHousePrompt(int player) {}
	public void invalidHousePrompt(int house) {}
	public void gameEnded(Map<Integer, Integer> playerToScore) {}
	public void moveStarted(int player, int house) {}

	public void moveUndone(boolean success) {
		updateBoard();
	};
	public void moveRedone(boolean success) {};
	
	private void updateBoard() {
		String boardStr[] =  board.toStringArray();
		this.topLabel.setText(boardStr[1]);
		this.bottomLabel.setText(boardStr[3]);
	}


	public static void main(String args[]) {
		new GUIModelView2();
	}

}
