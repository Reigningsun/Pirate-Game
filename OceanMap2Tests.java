package ccGame2;

import static org.junit.Assert.*;
import org.junit.Test;

public class OceanMap2Tests {

	@Test
	public void mapUnique() {
		OceanMap2 map;
		OceanMap2 secondMap;
		map = OceanMap2.getInstance();
		assertTrue(map != null);
		secondMap = OceanMap2.getInstance();
		assertTrue(map == secondMap);
	}

	
	
	@Test
	public void placesCorrectNumOfIsles(){
		OceanMap2 ocean = OceanMap2.getInstance();
		boolean[][] map = ocean.getMap();
		int numOfIslands = ocean.numOfIslands;
		
		
		ocean.placeIslands();
		
		int islesCounted = 0;
		
		for (int i = 0; i < map.length - 1; i++){
			for (int j = 0; j < map.length - 1; j++){
				if (map[i][j] == true){
					islesCounted++;
				}
			}
		}
		
		assertTrue(islesCounted == numOfIslands);
	}
	
	
	
	@Test
	public void noIslesInPlayerStartArea(){
		OceanMap2 ocean = OceanMap2.getInstance();
		boolean[][] map = ocean.getMap();
		int islesCounted = 0;
		
		ocean.placeIslands();
		
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if (map[i][j] == true){
					islesCounted++;
				}
			}
		}
		
		assertTrue(islesCounted == 0);
	}
}
