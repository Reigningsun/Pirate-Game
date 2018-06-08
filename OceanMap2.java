package ccGame2;

import java.util.Random;




public class OceanMap2 {

	// ========================= Variables and Constants ===================================================================================================================
	private static OceanMap2 theOceanMap;
	private boolean[][] map = new boolean [OceanExplorer2.dimension][OceanExplorer2.dimension];
	int numOfIslands;
	// =====================================================================================================================================================================
	
	
	
	
	// ======================== Constructor ================================================================================================================================	
	private OceanMap2() {}
	
	// =====================================================================================================================================================================
	
	
	
	
	// ======================== Functionality ==============================================================================================================================
	public void placeIslands(){
		Random r = new Random();
		for (int i = 0 ; i < numOfIslands ; i++) {																	// Randomly place a specified # of island tiles on the grid 
			int rX = 2 + r.nextInt(OceanExplorer2.dimension -2), rY = 2 + r.nextInt(OceanExplorer2.dimension -2);	// Randomly selects a legal coordinate pair of the grid ====
																													// It assures no island will be immediately around player ==
			if (map[rX][rY]) {																						// If the grid tile already contains an island try again ===
				i--;
			}
			else {																									// Designate tile as an island
				map[rX][rY] = true;
			}	
		}
	}
	
	// =====================================================================================================================================================================

	
	
		
	// ====================== Getter =======================================================================================================================================
	public static OceanMap2 getInstance(){
		if (theOceanMap == null){
			theOceanMap = new OceanMap2();
		}
		return theOceanMap; 
	}
	
	
	public boolean[][] getMap () {
		return this.map;
	}
	
	
	public void setNumOfIsles(int numIsles){
		this.numOfIslands = numIsles;
	}
	
	
	public int getNumOfIsles(){
		return numOfIslands;
	}
}
