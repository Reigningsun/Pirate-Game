package ccGame2;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Treasure2 implements Observer, Character2{
	// ========================= Variables and Constants ===================================================================================================================
	private Point Pos;
	private ImageView myImage;
	private boolean[][] grid;
	private Point playerPos;
	private Difficulty diff;
	private boolean isTreasure = true;
	// =====================================================================================================================================================================
	
	
	
	
	// ======================== Constructor ================================================================================================================================
	public Treasure2() {
		this.grid = OceanMap2.getInstance().getMap();
		int scale = grid.length;
		this.Pos = new Point(scale - 2, scale -2);
		this.myImage = new ImageView(new Image("ccGame2/treasure.jpg", OceanExplorer2.scale, OceanExplorer2.scale, true, true));
	
	}
	// =====================================================================================================================================================================




	@Override
	public void update(Observable playerShip, Object object) {
		if (playerShip instanceof PlayerShip2){
			playerPos = ((PlayerShip2) playerShip).getLoc();
			
			double dist = getDistance(Pos.getX(), Pos.getY(), playerPos);
			if (dist <= 1){
				((PlayerShip2) playerShip).treasureCollected = true;
				assert ((PlayerShip2) playerShip).treasureCollected == true;
			}
		}
	}
	
	
	
	public double getDistance(double x, double y, Point playerPos){							// ===== Calculates the distance between a move and the player's position ===========
		double xDist = x - playerPos.x;
		double yDist = y - playerPos.y;
		return Math.sqrt( (xDist)*(xDist) + (yDist)*(yDist) );
	}
	
	public ImageView getMyImage() {
		return this.myImage;
	}
	
	public Point getLoc(){
		return this.Pos;
	}


	@Override
	public void setImg(ImageView img) {
		myImage = img;
	}
	

	@Override
	public void setLoc(int x, int y) {
		this.Pos.setLocation(x, y);
	} 
	
	public void setDiff(Difficulty diff){
		this.diff = diff;
	}




	@Override
	public boolean isTreasure() {
		return isTreasure;
	}
}
