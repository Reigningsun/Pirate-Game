package ccGame2;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import ccGame.OceanExplorer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerShip2 extends Observable implements Character2{
		
	// ========================= Variables and Constants ===================================================================================================================
	private Point p;
	private ImageView myImage;
	private boolean[][] grid;
	List<Observer> observers = new LinkedList<Observer>();
	public boolean caught = false;
	public boolean treasureCollected = false;
	private Difficulty diff;
	public boolean isTreasure = false;
	// =====================================================================================================================================================================
	
	
	
	
	// ======================== Constructor ================================================================================================================================
	public PlayerShip2(OceanMap2 oceanMap) {
		this.grid = oceanMap.getMap();
		p = new Point(0 , 0);
		this.myImage = new ImageView(new Image("ccGame2/ship.png", OceanExplorer2.scale, OceanExplorer2.scale, true, true));	
	}
	// =====================================================================================================================================================================
	
	
	
	 
	// ======================== Functionality ==============================================================================================================================
	
		//	====== Ship Movement ===========================================================================================================================================											
		public void goEast() {
			if (checkRight(p.x, p.y)) {
				p.x++;
				notifyOfChanges();
			}
		}
		
		public void goWest() {
			if (checkLeft(p.x, p.y)) {
				p.x--;
				notifyOfChanges();
			}
		}
		
		public void goNorth() {
			if (checkUp(p.x, p.y)) {
				p.y--;
				notifyOfChanges();
			}
		}
	
		public void goSouth() {
			if (checkDown(p.x, p.y)) {
				p.y++;
				notifyOfChanges();
			}
		}
		
		public void goUpLeft() {
			if (checkUpLeft(p.x, p.y)) {
				p.x--;
				p.y--;
				notifyOfChanges();
			}
		}
		
		public void goUpRight() {
			if (checkUpRight(p.x, p.y)) {
				p.x++;
				p.y--;
				notifyOfChanges();
			}
		}
		
		public void goDownLeft() {
			if (checkDownLeft(p.x, p.y)) {
				p.x--;
				p.y++;
				notifyOfChanges();
			}
		}
		
		public void goDownRight() {
			if (checkDownRight(p.x, p.y)) {
				p.x++;
				p.y++;
				notifyOfChanges();
			}
		}
	//	===============================================================================================================================================================
	
	
	
	//	====== Ensure move is legal ===================================================================================================================================
		private boolean checkLeft(int xPos, int yPos) {					// ==== Is the move on the grid =================================================== 
			if (xPos > 0) {																 
				if (!grid[xPos-1][yPos]) {					// ==== Verifies this tile is not an island before saying the move is legal =======
					return true;
				}
			}
			return false;
		}
	
		private boolean checkRight(int xPos, int yPos) {
			if (xPos < grid.length-1) {						// ==== Is the move on the grid ===================================================
				if (!grid[xPos+1][yPos]) {					// ==== Verifies this tile is not an island before saying the move is legal =======
					return true;
				}
			}
			return false;
		}
	
		private boolean checkUp(int xPos, int yPos) {
			if (yPos > 0) {								// ==== Is the move on the grid ===================================================
				if (!grid[xPos][yPos-1]) {					// ==== Verifies this tile is not an island before saying the move is legal =======
					return true;
				}
			}
			return false;
		}
	
		private boolean checkDown(int xPos, int yPos) {
			if (yPos < grid[0].length-1) {						// ==== Is the move on the grid ===================================================
				if (!grid[xPos][yPos+1]) {					// ==== Verifies this tile is not an island before saying the move is legal =======
					return true;
				}
			}
			return false;
		}
		
		private boolean checkUpLeft(int xPos, int yPos) {
			if (yPos > 0 && xPos > 0) {						// ==== Is the move on the grid ===================================================
				if (!grid[xPos-1][yPos-1]) {					// ==== Verifies this tile is not an island before saying the move is legal =======
					return true;
				}
			}
			return false;
		}
		
		private boolean checkUpRight(int xPos, int yPos) {
			if (yPos > 0 && xPos < grid.length-1) {					// ==== Is the move on the grid ===================================================
				if (!grid[xPos+1][yPos-1]) {					// ==== Verifies this tile is not an island before saying the move is legal =======
					return true;
				}
			}
			return false;
		}
		
		private boolean checkDownLeft(int xPos, int yPos) {
			if (yPos < grid[0].length-1 && xPos > 0) {				// ==== Is the move on the grid ===================================================
				if (!grid[xPos-1][yPos+1]) {					// ==== Verifies this tile is not an island before saying the move is legal =======
					return true;
				}
			}
			return false;
		}
		
		private boolean checkDownRight(int xPos, int yPos) {
			if (yPos < grid[0].length-1 && xPos < grid.length-1) {			// ==== Is the move on the grid ===================================================
				if (!grid[xPos+1][yPos+1]) {					// ==== Verifies this tile is not an island before saying the move is legal =======
					return true;
				}
			}
			return false;
		}
	//	===================================================================================================================================================================
	
		private void notifyOfChanges(){
			setChanged();
			notifyObservers();
		}
	// 	===================================================================================================================================================================
 
	
	
		
// ====================== Getters ================================================================================================================================================
	
	public ImageView getMyImage() {
		return this.myImage;
	}

	
	@Override
	public void setImg(ImageView img) {
		myImage = img;
	}
	
	public Point getLoc () {
		return this.p;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void registerObserver(Observer character) {
		observers.add(character);
		
	}

	public void removeObserver(Observer character) {
		if(observers.contains(character))
		observers.remove(character);
	}
	
	@Override
	public void setLoc(int x, int y) {
		this.p.setLocation(x, y);
	}


	@Override
	public void setDiff(Difficulty diff) {
		this.diff = diff;
	}




	@Override
	public boolean isTreasure() {
		return isTreasure;
	}
}
