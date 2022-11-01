package TetrisBot.Mark_1_2;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class CODE_Main {
	public static Robot bot;
	public static int[] colHeight = new int[10];
	public static Board oBoard = new Board();
	public static double getHval(){
		double Hval = (getAggrHeight()*-0.5100066)+(getCompLines()*0.760666)+(getHoles()*-0.35663)+(getBumpVal()*-0.184483);
		return Hval;
	}
	public static int getBumpVal(){
		int bumpVal = 0;
		
		for(int i = 0 ; i < 9; i ++){
			bumpVal += Math.abs(colHeight[i+1]-colHeight[i]);			
		}		
		return bumpVal;
	}
	public static int getHoles(){
		int Holes = 0 ;
		Cell temp = new Cell();
		Cell copy = new Cell();
		temp = oBoard.marker;
		
		for(int y = 0 ; y < 19 ; y++){
			for(int x = 0 ; x < 10 ; x ++){
				copy = temp;
				if(!temp.occ){										
					for(int i = 0 ; i < 19-y; i++){					
						if(temp.occ){
							Holes++;							
							break;
						}
						temp = temp.up;
					}
					temp = copy;
				}
				temp = temp.right;
			}
			temp = oBoard.marker;
			for(int i = 0 ; i < y+1 ; i++)
				temp = temp.up;
		}
		
		return Holes;
	}
	public static int getAggrHeight(){
		int aggrHeight = 0;
		Cell temp = new Cell();
		temp = oBoard.first;
		
		for(int x = 0 ; x < 10 ; x++){
			for(int y = 0; y < 19 ;  y++){
				if(temp.down.occ){
					aggrHeight += 19 - y;
					colHeight[x] = 19-y;					
					y = 20;
				}
				temp = temp.down;
			}			
			temp = oBoard.first;
			
			for(int i = 0 ; i < x+1 ; i++)
				temp = temp.right;
		}
		
		return aggrHeight;
	}
	public static int getCompLines(){
		Cell temp = new Cell();
		temp = oBoard.first;
		int compLines = 20;
		
		for(int y = 0; y < 20 ;  y++){
			for(int x = 0 ; x < 10 ; x++){
				if(!temp.occ){
					compLines--;
					x = 10;
				}
				temp = temp.right;
			}
			temp = oBoard.first;
			for(int i = 0 ; i < y+1 ; i++)
				temp = temp.down;
		}
		return compLines;
	}
	public static void dispBoard(){
		Cell temp = new Cell();
		temp = oBoard.first;
		
		/*System.out.println("Coords:");
		for(int y = 0 ; y < 20 ; y ++){
			for(int x  = 0 ; x < 10 ; x++){
				System.out.print(temp.x+",");				
				System.out.print(temp.y+" ");
				if(y < 10)
					System.out.print(" ");
				temp = temp.right;
			}
			System.out.println("");
			temp = oBoard.first;
			for(int i = 0 ; i < y+1 ; i++)
			temp = temp.down;
		}*/
		
		temp = oBoard.first;
		System.out.println("oBoard:");
		for(int y = 0 ; y < 20 ; y ++){
			for(int x  = 0 ; x < 10 ; x++){
				
				if(temp.occ)
					System.out.print("\u25A0");
				else
					System.out.print("\u25A1");
				System.out.print(" ");
				temp = temp.right;
			}
			System.out.println("");
			temp = oBoard.first;
			for(int i = 0 ; i < y+1 ; i++)
			temp = temp.down;
		}		
	}
	enum Tet{I,J,L,O,S,T,Z,UNKWN};
	public static Cell a = new Cell();
	public static Cell b = new Cell();
	public static Cell c = new Cell();
	public static Cell d = new Cell();
	public static void orientTet(Tet tet, int ori) {		
			if(tet == Tet.I){
				if(ori == 0){
					b = a.right;
					c = b.right;
					d = c.right;
				}
				else{
					b = a.down;
					c = b.down;
					d = c.down;
				}
			}else if(tet == Tet.J){
				if(ori == 0){
					b = a.down;
					c = b.right;
					d = c.right;
				}else if(ori == 1){
					b = a.right;
					c = a.down;
					d = c.down;
				}else if(ori == 2){
					b = a.right;
					c = b.right;
					d = c.down;
				}else if(ori == 3){
					b = a.right;
					c = b.up;
					d = c.up;
				}			
			}else if(tet == Tet.L){
				if(ori == 0){
					b = a.right;
					c = b.right;
					d = c.up;
				}else if(ori == 1){
					b = a.down;
					c = b.down;
					d = c.right;
				}else if(ori == 2){
					b = a.right;
					c = b.right;
					d = a.down;
				}else if(ori == 3){
					b = a.right;
					c = b.down;
					d = c.down;
				}			
				
			}else if(tet == Tet.O){
				b = a.right;
				c = a.down;
				d = c.right;
			}else if(tet == Tet.S){
				if(ori == 0){
					b = a.right;
					c = b.up;
					d = c.right;
				}else if(ori == 1){
					b = a .down;
					c = b.right;
					d = c.down;				
				}
				
			}else if(tet == Tet.T){
				if(ori == 0){
					b = a.right;
					c = b.up;
					d = b.right;
				}else if(ori == 1){
					b = a.down;
					c = b.right;
					d = b.down;
				}else if(ori == 2){
					b = a.right;
					c = b.right;
					d = b.down;
				}else if(ori == 3){
					b = a.right;
					c = b.up;
					d = b.down;
				}			
						
			}else if(tet == Tet.Z){
				if(ori == 0){
					b = a.right;
					c = b.down;
					d = c.right;
				}else if(ori == 1){
					b = a.right;
					c = b.up;
					d = a.down;
				}		
			}
		
	
		
		
	}
	public static Move[] move = new Move[40];
	public static void findMove(Tet tet) throws AWTException{
		
		for(int i = 0 ; i < 40 ; i++)
			move[i] = new Move();
		Cell start = new Cell();
		Cell mark = new Cell();
		start = oBoard.first.down.down;
		a = start;		
		mark = start;	
		int count = 0;
		int movec = 0;
		for(int rot  = 0 ; rot < ((tet == Tet.I || tet == Tet.S || tet == Tet.Z) ? 2 : ((tet == Tet.J || tet == Tet.L || tet == Tet.T) ? 4 : 1)) ; rot++){
			while(a.x != 9 && b.x != 9 && c.x != 9 && d.x != 9){
				a = mark;
				orientTet(tet,rot);				
				while(a.y != 19 && b.y != 19 && c.y != 19 && d.y != 19 && !a.down.occ && !b.down.occ && !c.down.occ && !d.down.occ){
					a = a.down;
					orientTet(tet,rot);					
				}
				
				a.occ = true;
				b.occ = true;
				c.occ = true;
				d.occ = true;
				move[movec].Hval = getHval();
				move[movec].moves = count;
				move[movec].rot = rot;
				a.occ = false;
				b.occ = false;
				c.occ = false;
				d.occ = false;
	
			
			mark = mark.right;			
			count++;
			movec++;
			}
			
			mark = start;
			a = start;
			orientTet(tet,rot);
			count = 0;
		}
		
		
		Move best = new Move();
		best = move[0];
		
		for(int i = 0; i < 40 ; i++){
			if(move[i].Hval == -100)
				break;
			if(move[i].Hval > best.Hval)
				best = move[i];
		}	
		
		a = start;
		int rot = best.rot;	
		int moves = best.moves;
		orientTet(tet,rot);
		for(int i = 0 ; i < moves ; i++){
			a = a.right;
		}
		orientTet(tet,rot);
		while(a.y != 19 && b.y != 19 && c.y != 19 && d.y != 19 && !a.down.occ && !b.down.occ && !c.down.occ && !d.down.occ){
			a = a.down;
			orientTet(tet,rot);					
		}
		a.occ = true;
		b.occ = true;
		c.occ = true;
		d.occ = true;
		dispBoard();
		//makeMove(best);
		removeLines();
		a = start;
		
	}
	public static void removeLines(){
		Cell cell = new Cell();
		cell = oBoard.marker;
		boolean line = true;
		Cell temp = new Cell();
		temp = cell;
		for(int y = 0 ; y < 19 ; y++){	
			
			for(int x = 0 ; x < 9 ; x++){
				
				if(!cell.occ)
					line = false;
				cell = cell.right;
				
			}
			cell = temp;
			if(line)
				for(int x = 0 ; x < 9 ; x++){					
					cell.occ = cell.up.occ;
					cell = cell.right;
				}
			
			temp = temp.up;
			cell = temp;
			line = true;
		}
		
		
	}
	public static void makeMove(Move best) throws AWTException{
		
		bot.setAutoDelay(110);
		bot.waitForIdle();
	    bot.setAutoWaitForIdle(true);
	    
	    for(int i = 0 ; i < best.rot ; i++){
	    	bot.keyPress(KeyEvent.VK_UP);
	     	bot.keyRelease(KeyEvent.VK_UP);
 	    }  
	    bot.waitForIdle();
	    for(int i = 0 ; i < 5 ; i++){
	    	bot.keyPress(KeyEvent.VK_LEFT);
	    	bot.keyRelease(KeyEvent.VK_LEFT);
	    }
	    bot.waitForIdle();
	     for(int i = 0 ; i < best.moves ; i++){
	    	bot.keyPress(KeyEvent.VK_RIGHT);
	    	bot.keyRelease(KeyEvent.VK_RIGHT);
	    }
	     bot.waitForIdle();
	    bot.keyPress(KeyEvent.VK_SPACE);
    	bot.keyRelease(KeyEvent.VK_SPACE);
    	
	    
	}
	public static Tet idTet() throws AWTException, IOException{		
		Tet tet = Tet.UNKWN;// reset tetriminoe		
		bot = new Robot(); //Initialize bot
		Color bg = Color.decode("#1A1A1A");// set Background colour
		BufferedImage bufferedimage = new BufferedImage (95, 45, BufferedImage.TYPE_INT_RGB); // create palette
		Rectangle rect = new Rectangle(717,350,95,50); //define image location and size
		bot.delay(200); //delay
		
		do{	
				//capture Tetriminoe spawn
				bufferedimage = bot.createScreenCapture(rect);				
				String bytes = "";
				//search 4*2 space
				for(int y = 0 ; y < 2 ; y++){
					for(int x = 0 ; x < 4 ; x++){
						// 0 if the space hasn't a tet piece in it, 1 if it does
						bytes += (bufferedimage.getRGB(12+x*23,12+y*23) == bg.getRGB()) ? "0" : "1";						
					}
				}
				int decByte = Integer.parseInt(bytes,2);// string to binary to decimal
				if(decByte == 15){ 
						tet = Tet.I;				
					}else if(decByte == 142){
						tet = Tet.J;
					}else if(decByte == 46){
						tet = Tet.L;
					}else if(decByte == 102){
						tet = Tet.O;
					}else if(decByte == 108){
						tet = Tet.S;
					}else if(decByte == 78){
						tet = Tet.T;
					}else if(decByte == 198){
						tet = Tet.Z;
					}				
		}while(tet == Tet.UNKWN); // continue looking at spawn until a tetriminoe has spawned (memory heavy? optimize?)
		
		return tet;
	}
	public static void main(String[] args) throws AWTException, IOException {
		
		oBoard.createBoard();
		bot = new Robot();	
		bot.delay(3000);
		int tet = 0;
		Tet rTet = Tet.UNKWN;
		while(true){
			tet = (int) (Math.random()*7)+1;
			
			if(tet == 0){
				rTet = Tet.I;
			}else if(tet == 1){
				rTet = Tet.J;
			}else if(tet == 2){
				rTet = Tet.L;
			}else if(tet == 3){
				rTet = Tet.O;
			}else if(tet == 4){
				rTet = Tet.S;
			}else if(tet == 5){
				rTet = Tet.T;
			}else if(tet == 6){
				rTet = Tet.Z;
			}
			
		findMove(rTet);
 		}
	}

}
