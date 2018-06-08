package ccGame2;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// TODO add flare functionality: Hard pirate should be able to fire a flare at  the player that puts all pirates in chasing mode. The other pirates would get the flare 
// location as their last seen player position and would move towards it whether the player is actually there or not unless they see the player


public class Pirate2 implements Observer, Character2{
	
	// ========================= Variables and Constants ===================================================================================================================
	protected Point piratePos;																// === Pirates position =========================================================
	protected ImageView myImage;
	protected ImageView idleImg      = new ImageView(new Image("ccGame2/pirateShip.png", OceanExplorer2.scale, OceanExplorer2.scale, true, true));      	// =================
	protected ImageView defendingImg = new ImageView(new Image("ccGame2/pirateDefending.png", OceanExplorer2.scale, OceanExplorer2.scale, true, true)); 	// =================
	protected ImageView chasingImg   = new ImageView(new Image("ccGame2/pirateChasing.png", OceanExplorer2.scale, OceanExplorer2.scale, true, true));   	// =================
	private boolean[][] grid;
	private Random rand = new Random(); 
	protected Point playerPos;																// === Players position ============================================================
	protected int timeSincePlayerLastSeen;															// === Keeps track of turns since the player was last seen =========================
	protected Point lastSeenPlayerPos;															// === Keeps track of the last place the player was seen for use with flare ========
	protected State2 idle;
	protected State2 defending;
	protected State2 chasing;
	protected State2 state;
	protected Difficulty diff;
	public boolean isTreasure = false;
	// =====================================================================================================================================================================
	
	
	
	
	// ======================== Constructor ================================================================================================================================
	public Pirate2() {
		this.grid = OceanMap2.getInstance().getMap();
		this.piratePos = new Point(rand.nextInt(grid.length -2) +2, rand.nextInt(grid.length -2) +2);
		this.myImage = new ImageView(new Image("ccGame2/pirateShip.png", OceanExplorer2.scale, OceanExplorer2.scale, true, true));
		idle = new Idle2(this);
		defending = new Defending2(this);
		chasing = new Chasing2(this);
		this.state = idle;
		this.playerPos = new Point(0,0);
		this.lastSeenPlayerPos = new Point(0,0);
	}
	// =====================================================================================================================================================================
	
	
	
	
	// === State based actions =============================================================================================================================================
	public void move(){
		state.move();
	}
	
	
	
	public void lookForPlayer() {
		state.lookForPlayer();
	}
	
	
	
	public void relax() {
		state.relax();
	}
	
	
	
	public void setState (State2 state){
		this.state = state;
	}
	
	
	
	public void takeActions(){
		state.lookForPlayer();
		if (timeSincePlayerLastSeen >= 2){
			state.relax();
		}
		state.move();
	}
		
	// =====================================================================================================================================================================
	
	

	
	// ======================== Chase Behavior =============================================================================================================================
	public Point getBestMove (Point target){									// === Looks at adjacent tiles for the one closest to the player ===================							
		double closest = Double.POSITIVE_INFINITY;
		Point bestMove = new Point();
		int pX = piratePos.x;
		int pY = piratePos.y;
		for (int x = pX - 1; x < pX + 2; x++){	
			for (int y = pY - 1; y < pY + 2; y++){
				double dist = getDistance(x, y, target);					
					
				if (moveIsLegal(x, y) && dist < closest){						// === In theory this should be preventing the selection of island tiles ===========
					closest = dist;									// === while updating best move with the closest available tile ====================
					bestMove.setLocation(x, y);
				}
			}
		}
		return bestMove;
	}
	
	
	public double getDistance(int x, int y, Point target){								// === Calculates the distance between a move and the target's position =============
		int xDist = x - target.x;
		int yDist = y - target.y;
		return Math.sqrt( (xDist)*(xDist) + (yDist)*(yDist) );
	}
	
 
	
	public Point getRandMove(){											// === Chooses a legal random move ==================================================
		Point randMove = new Point();
		int pX = piratePos.x;
		int pY = piratePos.y;
		boolean legalMoveNotFound = true;
		int baseNum = -1;
		
		while (legalMoveNotFound){
			int x = pX + baseNum + rand.nextInt(3);
			int y = pY + baseNum + rand.nextInt(3);
			
			if (moveIsLegal(x, y)){										// === In theory this should be preventing the selection of island tiles =============
				randMove.setLocation(x, y);
				legalMoveNotFound = false;
				return randMove;
			}
		}
		return randMove;
	}
	
	
	public Point getClosestMoveToPlayer(int viewPointX, int viewPointY){
		double closest = Double.POSITIVE_INFINITY;
		Point bestMove = new Point();
		int pX = viewPointX;
		int pY = viewPointY;
		for (int x = pX - 1; x < pX + 2; x++){	
			for (int y = pY - 1; y < pY + 2; y++){
				double dist = getDistance(x, y, playerPos);					
					
				if (dist < closest){													
					closest = dist;									// === while updating best move with the closest available tile ======================
					bestMove.setLocation(x, y);
				}
			}
		}
		return bestMove;
	}
	
	
	public boolean canCatchPlayerNextMove(double x, double y){
		Point closest = getClosestMoveToPlayer(piratePos.x, piratePos.y);
		return closest.getX() == playerPos.getX() && closest.getY() == playerPos.getY();
	}
	// =======================================================================================================================================================================
	
	
	
	
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
	
	
	public boolean isIsland (int x, int y){										// === Verifies that this space is not occupied by an island =========================
		return grid[x][y];
	}
	
	
	public boolean legalX(int x){											// === Verifies this X is within bounds of the grid ==================================
		if (x >= 0 && x <= grid.length - 1){ 
			return true;
		}
		return false;
	}
	
	
	public boolean legalY(int y){											// === Verifies this Y is within bounds of the grid ==================================
		if (y >= 0 && y <= grid[0].length - 1){
			return true;
		}
		return false;
	}
	
	// =======================================================================================================================================================================

	
	
	
	// ======================= Vision ========================================================================================================================================	
	public boolean canSeePlayer(){											// === Determines if the Pirate is close enough and has clear LOS to see the player ==
		if (inViewRange() && !viewBlocked()){
			lastSeenPlayerPos.setLocation(playerPos.x, playerPos.y); 
			return true;
		}
		return false;
	}
	
	public boolean inViewRange(){											// === Determines if the pirate is close enough to see the player ====================
		if (getDistance(piratePos.x, piratePos.y, playerPos) <= sightRange()){						
			return true;
		}
		return false;
	}
	
	
	public boolean viewBlocked(){											// === Determines if an island exists between the player and the viewer ==============
		Point tileViewed = getClosestMoveToPlayer(piratePos.x, piratePos.y);
		for (int i = 0; i < sightRange(); i++){									// === Searches the tiles between the player and the pirate out to a limit of the pirates sightRange
			if (isIsland(tileViewed.x, tileViewed.y)){							// === Returns true if it finds and island in the tile that is being viewed ==========
				return true;
			} else {											// === Updates the tile to be viewed for the next iteration ==========================
				tileViewed = getClosestMoveToPlayer(tileViewed.x, tileViewed.y);
			}
		}
		return false;												// === Did not find an island ========================================================
	}
	
	
	public int sightRange(){
		return diff.getSightRange();
	}
	// =======================================================================================================================================================================
	
	
	
		
	// ============ Getters ==================================================================================================================================================
	
	public ImageView getMyImage() {
		return this.myImage;
	}
	



	@Override
	public Point getLoc() {
		return this.piratePos;
	}




	@Override
	public void setLoc(int x, int y) {
		this.piratePos.setLocation(x, y);
	}


	public State2 getState(){
		return this.state;
	}
	
	
	public void setDiff(Difficulty diff){
		this.diff = diff;
	}

	
	@Override
	public void update(Observable playerShip, Object arg) {
		if (playerShip instanceof PlayerShip2){
			playerPos = ((PlayerShip2) playerShip).getLoc();
			
			if (getDistance(piratePos.x, piratePos.y, playerPos) < 2){
				((PlayerShip2) playerShip).caught = true;
			}
		}
		
		takeActions();		
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
