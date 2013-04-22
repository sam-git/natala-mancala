package view.model_view;

import java.util.Properties;

import model.Model;

public class ASCIIBoardPrinter {

	private final Model m;
	private final int HOUSES_PER_PLAYER;
	private final boolean PLAY_CLOCKWISE;

	private String boardTopAndBottom;
	private String boardMiddle;
	private String HOUSE_FORMAT;
	private String V_FENCE;
	private String H_FENCE ;
	private String CNR_FENCE;
	private String LEFT_NAME;
	private String RIGHT_NAME;
	private String LEFT_STORE_FORMAT;
	private String RIGHT_STORE_FORMAT;

	public ASCIIBoardPrinter(Model m, Properties props) {
		this.m = m;		
		this.HOUSES_PER_PLAYER = m.getHousesPerPlayer();
		this.PLAY_CLOCKWISE = m.isPlayClockwise();
		
		this.setConstants(props);
		this.prepareBoard();
	}

	public String[] toStringArray() {
		String[] lines = new String[5];
		lines[0] = this.boardTopAndBottom;
		lines[2] = this.boardMiddle;
		lines[4] = this.boardTopAndBottom;
 		int houseOwner, storeOwner; 
		if (PLAY_CLOCKWISE) {
			houseOwner = 2;
			storeOwner = 1;
			lines[1] = getLeftToRight(houseOwner, storeOwner);
			houseOwner = 1;
			storeOwner = 2;
			lines[3] = getRightToLeft(houseOwner, storeOwner);
		} else {
			houseOwner = 2;
			storeOwner = 1;
			lines[1] = getRightToLeft(houseOwner, storeOwner);
			houseOwner = 1;
			storeOwner = 2;
			lines[3] = getLeftToRight(houseOwner, storeOwner);			
		}
		return lines;
	}

	public void setConstants(Properties props) {
		if(PLAY_CLOCKWISE) {
			this.LEFT_NAME = String.format(" %s ", props.getProperty("1ShortName"));
			this.RIGHT_NAME = String.format(" %s ", props.getProperty("2ShortName"));
		} else {
			this.LEFT_NAME = String.format(" %s ", props.getProperty("2ShortName"));
			this.RIGHT_NAME = String.format(" %s ", props.getProperty("1ShortName"));
		}
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
		int leftNameLength = LEFT_NAME.length();
		this.LEFT_STORE_FORMAT = " %" + (leftNameLength - 2) + "d ";

		int rightNameLength = RIGHT_NAME.length();
		this.RIGHT_STORE_FORMAT = " %" + (rightNameLength - 2) + "d ";
	}

	private void prepareTopAndBottom() {
		StringBuffer sb = new StringBuffer(CNR_FENCE);
		for (int i = 0; i < LEFT_NAME.length(); i++) {
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
		for (int i = 0; i < RIGHT_NAME.length(); i++) {
			sb.append(H_FENCE);
		}
		sb.append(CNR_FENCE);
		boardTopAndBottom = sb.toString();
	}

	private void prepareMiddleLine() {
		String leftGap = V_FENCE + LEFT_NAME.replaceAll(".", " ") + V_FENCE;
		String rightGap = V_FENCE + RIGHT_NAME.replaceAll(".", " ") + V_FENCE;
		boardMiddle = leftGap
				+ boardTopAndBottom.subSequence(leftGap.length(),
						boardTopAndBottom.length() - rightGap.length())
				+ rightGap;
	}

	private String getRightToLeft(int player, int storeOwner) {
		StringBuffer sb = new StringBuffer(V_FENCE + LEFT_NAME + V_FENCE);
		for (int i = HOUSES_PER_PLAYER; i > 0; i--) {
			sb.append(String.format(HOUSE_FORMAT, i, m.getSeedCount(player, i)));
			sb.append(V_FENCE);
		}
		sb.append(String.format(RIGHT_STORE_FORMAT, m.getStoreSeedCount(storeOwner)));
		sb.append(V_FENCE);
		return sb.toString();
	}

	private String getLeftToRight(int player, int storeOwner) {
		StringBuffer sb = new StringBuffer(V_FENCE);
		sb.append(String.format(LEFT_STORE_FORMAT, m.getStoreSeedCount(storeOwner)));
		sb.append(V_FENCE);
		for (int i = 1; i <= HOUSES_PER_PLAYER; i++) {
			sb.append(String.format(HOUSE_FORMAT, i, m.getSeedCount(player, i)));
			sb.append(V_FENCE);
		}
		sb.append(RIGHT_NAME + V_FENCE);
		return sb.toString();
	}
}
