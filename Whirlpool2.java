package ccGame2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Whirlpool2 implements Observer, Character2{

	private boolean[][] grid;
	private Point Pos;																	// === Whirlpools position =========================================================
	private ImageView myImage;
	private Random rand = new Random(); 
	ArrayList<Character2> contents = new ArrayList<Character2>();						// === Stores any characters trapped in whirlpool ==================================
	ArrayList <Character2> NPCs = new ArrayList <Character2>();   						// === Stores any Non Player Characters ============================================
	private PlayerShip2 player;
	private Point playerPos;															// === Players position ============================================================
	private int chanceToCapture = 60;													// === Change this to increase/decrease chance of trapping characters ==============
	private Difficulty diff;
	public boolean isTreasure = false;
	
	
	public Whirlpool2(PlayerShip2 ship) {
		this.grid = OceanMap2.getInstance().getMap();
		this.Pos = new Point(rand.nextInt(grid.length -2) +2, rand.nextInt(grid.length -2) +2);
		this.myImage = new ImageView(new Image("ccGame2/whirlpool.jpg", OceanExplorer2.scale, OceanExplorer2.scale, true, true));
		this.playerPos = new Point(0,0);
		this.NPCs = OceanExplorer2.NPCs;
		this.player = ship;
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		playerPos = player.getLoc();
		double distToPlayer = getDistance(Pos.x, Pos.y, playerPos);
		
		if (distToPlayer <= sightRange() && !contents.contains(player)){				// === Adds player to contents ====================================================
			contents.add(player); 
		}
		if (distToPlayer > sightRange() && contents.contains(player)){					// === Remove player from contents if it moves out of range =======================
			contents.remove(player);
		}
		
		//
		Point randMove = getRandMove();
		Pos.setLocation(randMove.getX(), randMove.getY());		
		//
		
		Iterator<Character2> npcs = NPCs.iterator();						
		while (npcs.hasNext()){															// === Moves all contents of whirlpool towards it =================================
			Character2 character = (Character2) npcs.next();
			double dist = getDistance(Pos.x, Pos.y, character.getLoc());
			if (!contents.contains(character) && dist <= sightRange()){					// === Grab any npcs in range not already grabbed =================================
				
				if (!character.isTreasure()){
					contents.add(character);
				}
			}
			if (contents.contains(character) && dist > sightRange()){					// === Release any npcs that are now out of range =================================
				contents.remove(character);
			}
		}
		
		Iterator<Character2> theContents = contents.iterator();						
		while (theContents.hasNext()){													// === Attempts to move each of its contents towards it ===========================
			Character2 content = (Character2) theContents.next();
			int chanceRoll = rand.nextInt(100) + 1;

			if (chanceRoll <= chanceToCapture){											// === Moves contents closer if they rolled <= chanceToCapture ====================
				Point closer = moveCloserToWhirlpool(content);
				content.setLoc(closer.x, closer.y);
			}
		}
	}

	
	public Point moveCloserToWhirlpool (Character2 character){							// === Moves the character to the tile that is closest legal move to the whirlpool =
		double closest = Double.POSITIVE_INFINITY;
		Point bestMove = new Point();
		Point characterLoc = character.getLoc();
		int pX = characterLoc.x;
		int pY = characterLoc.y;
		for (int x = pX - 1; x < pX + 2; x++){	
			for (int y = pY - 1; y < pY + 2; y++){
				double dist = getDistance(x, y, Pos);					
					
				if (moveIsLegal(x, y) && dist < closest){								// ===== In theory this should be preventing the selection of island tiles =========
					closest = dist;														// ===== while updating best move with the closest available tile ==================
					bestMove.setLocation(x, y);
				}
			}
		}
		return bestMove;
	}

	
	public double getDistance(int x, int y, Point target){								// ===== Calculates the distance between a move and the target's position ===========
		int xDist = x - target.x;
		int yDist = y - target.y;
		return Math.sqrt( (xDist)*(xDist) + (yDist)*(yDist) );
	}
	
	
	public Point getRandMove(){															// === Chooses a legal random move ==================================================
		Point randMove = new Point();
		int pX = Pos.x;
		int pY = Pos.y;
		boolean legalMoveNotFound = true;
		int baseNum = -1;
		
		while (legalMoveNotFound){
			int x = pX + baseNum + rand.nextInt(3);
			int y = pY + baseNum + rand.nextInt(3);
			
			if (moveIsLegal(x, y)){														// === In theory this should be preventing the selection of island tiles =============
				randMove.setLocation(x, y);
				legalMoveNotFound = false;
				return randMove;
			}
		}
		return randMove;
	}
	// ======================= Validate Move is legal ========================================================================================================================
	public boolean moveIsLegal (int x, int y){
		if (legalX(x) && legalY(y) && !isIsland(x, y) && !isPlayer(x, y)){
			return true;
		}
		return false;
	}
	
	
	public boolean isPlayer (int x, int y){
		if (playerPos.getX() == x && playerPos.getY() == y){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public boolean isIsland (int x, int y){												// === Verifies that this space is not occupied by an island =========================
		return grid[x][y];
	}
	
	
	public boolean legalX(int x){														// === Verifies this X is within bounds of the grid ==================================
		if (x >= 0 && x <= grid.length - 1){ 
			return true;
		}
		return false;
	}
	
	
	public boolean legalY(int y){														// === Verifies this Y is within bounds of the grid ==================================
		if (y >= 0 && y <= grid[0].length - 1){
			return true;
		}
		return false;
	}
	
	// =======================================================================================================================================================================
	
	
	public void addTo(Character2 character){											// Adds new character to contents 
		contents.add(character);														
	}
	
	
	
	
	public void removeFrom(Character2 character){										// Removes the character from this container
		contents.remove(character);
	}
	
	
	public void changeChanceToCapture(int num){
		chanceToCapture = num;
	}
	
	
	@Override
	public ImageView getMyImage() {
		return myImage;
	}

	@Override
	public Point getLoc() {
		return Pos;
	}

	@Override
	public void setLoc(int x, int y) {
		Pos.setLocation(x, y);
	}
	
	public void setDiff(Difficulty diff){
		this.diff = diff;
	}
	
	public int sightRange(){
		return diff.getSightRange();
	}


	@Override
	public void setImg(ImageView img) {
		myImage = img;
	}


	@Override
	public boolean isTreasure() {
		return isTreasure;
	}

}
