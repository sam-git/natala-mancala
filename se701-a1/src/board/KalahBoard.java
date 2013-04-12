package board;

public class KalahBoard extends AbstractBoard {

	public KalahBoard () {
		super(new BoardModelBuilder().
				housesPerPlayer(6).
				rowsPerPlayer(1).
				startingSeedsPerHouse(4).
				build());
	}
	
	@Override
	public String[] toStringArray() {
		String[] lines = new String[5];
		lines[0] = "+----+-------+-------+-------+-------+-------+-------+----+";
		lines[1] = String
				.format("| P2 | 6[%2d] | 5[%2d] | 4[%2d] | 3[%2d] | 2[%2d] | 1[%2d] | %2d |",
						getSeedCount(2, 6), getSeedCount(2, 5),
						getSeedCount(2, 4), getSeedCount(2, 3),
						getSeedCount(2, 2), getSeedCount(2, 1),
						getSeedCount(1, 0));
		lines[2] = "|    |-------+-------+-------+-------+-------+-------|    |";
		lines[3] = String
				.format("| %2d | 1[%2d] | 2[%2d] | 3[%2d] | 4[%2d] | 5[%2d] | 6[%2d] | P1 |",
						getSeedCount(2, 0), getSeedCount(1, 1),
						getSeedCount(1, 2), getSeedCount(1, 3),
						getSeedCount(1, 4), getSeedCount(1, 5),
						getSeedCount(1, 6));
		lines[4] = "+----+-------+-------+-------+-------+-------+-------+----+";
		return lines;
	}
}
