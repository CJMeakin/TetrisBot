package TetrisBot.Mark_1_2;

public class Board {
	public static Cell cell = new Cell();
	public static Cell marker = new Cell();
	public static Cell first = new Cell();
	public static void createBoard(){	
		first = new Cell();
		marker = new Cell();
		Cell temp = new Cell();
		
		for(int row  = 0 ; row < 20 ; row++){
			for(int column = 0 ; column < 10 ; column++){
				Cell cell = new Cell();
				
				if(row == 0 && column == 0){
					marker = cell;
					first = cell;
					temp = cell;
					cell.x = column;
					cell.y = row;
				}
				else{
					if(row == 0){
						cell.left = temp;
						temp.right = cell;
						if(column<10)
						temp = temp.right;
						cell.x = column;
						cell.y = row;
					}else if(column == 0){						
						temp = marker;
						cell.up = temp;
						marker = cell;
						temp.down = cell;
						temp = cell;
						cell.x = column;
						cell.y = row;
					}else{						
						cell.left = temp;
						temp.right = cell;
						cell.up = temp.up.right;
						cell.up.down = cell;
						temp = temp.right;
						cell.x = column;
						cell.y = row;
					}
				}
			}			
		}
		
	}
	
}