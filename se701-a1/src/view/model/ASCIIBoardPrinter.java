package view.model;

import java.util.Properties;

import model.GameModel;

public class ASCIIBoardPrinter {

	private final GameModel m;
	private final int HOUSES_PER_PLAYER;

	private String boardTopAndBottom;
	private String boardMiddle;
	private String HOUSE_FORMAT;
	private String P2STORE_NAME;
	private String P1STORE_NAME;
	private String P1STORE_FORMAT;
	private String P2STORE_FORMAT;	
	private String V_FENCE;
	private String H_FENCE ;
	private String CNR_FENCE;

	public ASCIIBoardPrinter(GameModel m, Properties props) {
		this.m = m;		
		this.HOUSES_PER_PLAYER = m.getHousesPerPlayer();
		this.setConstants(props);
		this.prepareBoard();
	}

	public String[] toStringArray() {
		String[] lines = new String[5];
		lines[0] = this.boardTopAndBottom;
		lines[1] = getPlayerTwoSide();
		lines[2] = this.boardMiddle;
		lines[3] = getPlayerOneSide();
		lines[4] = this.boardTopAndBottom;
		return lines;
	}

	public void setConstants(Properties props) {
		this.P1STORE_NAME = String.format(" %s ", this.m.getPlayerShortName(1));
		this.P2STORE_NAME = String.format(" %s ", this.m.getPlayerShortName(2));
		this.V_FENCE = props.getProperty("vFence");
		this.H_FENCE = props.getProperty("hFence");
		this.CNR_FENCE = props.getProperty("cnrFence");
		this.HOUSE_FORMAT = props.getProperty("houseFormat");
		this.prepareBoard();
	}

	private void prepareBoard() {
		prepareStoreFormats();
		prepareTopAndBottom();
		prepareMiddleLine();
	}

	private void prepareStoreFormats() {
		int p1NameLength = P1STORE_NAME.length();
		P1STORE_FORMAT = " %" + (p1NameLength - 2) + "d ";

		int p2NameLength = P2STORE_NAME.length();
		P2STORE_FORMAT = " %" + (p2NameLength - 2) + "d ";
	}

	private void prepareTopAndBottom() {
		StringBuffer sb = new StringBuffer(CNR_FENCE);
		for (int i = 0; i < P2STORE_NAME.length(); i++) {
			sb.append(H_FENCE);
		}
		for (int i = 0; i < HOUSES_PER_PLAYER; i++) {
			sb.append(CNR_FENCE);
			String s = String.format(HOUSE_FORMAT, 0, 0);
			for (int j = 0; j < s.length(); j++) {
				sb.append(H_FENCE);
			}
		}
		sb.append(CNR_FENCE);
		for (int i = 0; i < P1STORE_NAME.length(); i++) {
			sb.append(H_FENCE);
		}
		sb.append(CNR_FENCE);
		boardTopAndBottom = sb.toString();
	}

	private void prepareMiddleLine() {
		String leftGap = V_FENCE + P2STORE_NAME.replaceAll(".", " ") + V_FENCE;
		String rightGap = V_FENCE + P1STORE_NAME.replaceAll(".", " ") + V_FENCE;
		boardMiddle = leftGap
				+ boardTopAndBottom.subSequence(leftGap.length(),
						boardTopAndBottom.length() - rightGap.length())
				+ rightGap;
	}

	private String getPlayerTwoSide() {
		StringBuffer sb = new StringBuffer(V_FENCE + P2STORE_NAME + V_FENCE);
		for (int i = HOUSES_PER_PLAYER; i > 0; i--) {
			sb.append(String.format(HOUSE_FORMAT, i, m.getSeedCount(2, i)));
			sb.append(V_FENCE);
		}
		sb.append(String.format(P1STORE_FORMAT, m.getStoreSeedCount(1)));
		sb.append(V_FENCE);
		return sb.toString();
	}

	private String getPlayerOneSide() {
		StringBuffer sb = new StringBuffer(V_FENCE);
		sb.append(String.format(P2STORE_FORMAT, m.getStoreSeedCount(2)));
		sb.append(V_FENCE);
		for (int i = 1; i <= HOUSES_PER_PLAYER; i++) {
			sb.append(String.format(HOUSE_FORMAT, i, m.getSeedCount(1, i)));
			sb.append(V_FENCE);
		}
		sb.append(P1STORE_NAME + V_FENCE);
		return sb.toString();
	}
}
