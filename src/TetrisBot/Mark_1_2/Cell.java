package TetrisBot.Mark_1_2;

public class Cell {
	
	Cell right;
	Cell left;
	Cell down;
	Cell up;
	public int x;
	public int y;
	boolean occ;
	boolean fake;
	
	
	public Cell(){
		x = 0;
		y = 0;
		right = null;
		left = null;
		down = null;
		up = null;
		occ = false;
		fake = false;
	}

}
