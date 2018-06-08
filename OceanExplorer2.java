package ccGame2;


import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
 
// TODO Try and have a win and lose screen pop up with an image.

//player controller, input handler
public class OceanExplorer2 extends Application { 			
	
	// ========================= Variables and Constants ===================================================================================================================	
	public static int dimension = 15;													// === Change this to alter cells count in each dimension ==========================
	public static int scale = 90; 														// === Change this to alter size of cells ==========================================
	public static AnchorPane root = new AnchorPane();									// === GUI =========================================================================
	PlayerShip2 ship;																	// === Player controllable ship ====================================================
	OceanMap2 oceanMap;																	// === Stores the game map =========================================================
	public static ArrayList <Character2> NPCs = new ArrayList <Character2>();			// === Stores any Non Player Characters ============================================
	ImagePattern water = new ImagePattern (new Image("ccGame2/water.jpg", OceanExplorer2.scale, OceanExplorer2.scale, true, true)); // === Water image file =================
	ImagePattern isle = new ImagePattern (new Image("ccGame2/island.jpg", OceanExplorer2.scale, OceanExplorer2.scale, true, true)); // === Island image file ================
	ImageView win = new ImageView (new Image("ccGame2/youWin.jpg", OceanExplorer2.scale, OceanExplorer2.scale, true, true));
	ImageView lose = new ImageView (new Image("ccGame2/youLose.png", OceanExplorer2.scale, OceanExplorer2.scale, true, true));
	public int numOfIslands;
	public boolean endGame = false;
	// ======================================================================================================================================================================
	
	
	
	
	// ======================== Functionality ===============================================================================================================================
	private void startSailing(Scene scene) {											// ====== Translates player inputs into ship movements ==============================
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				
				checkIfShouldEndGame();
				
				switch(event.getCode()){
				case NUMPAD6:
					ship.goEast();
					break;
				case NUMPAD4:
					ship.goWest();
					break;
				case NUMPAD8:
					ship.goNorth();
					break;
				case NUMPAD2:
					ship.goSouth();
					break;
				case RIGHT:
					ship.goEast();
					break;
				case LEFT:
					ship.goWest();
					break;
				case UP:
					ship.goNorth();
					break;
				case DOWN:
					ship.goSouth();
					break;
				case NUMPAD7:
					ship.goUpLeft();
					break;
				case NUMPAD9:
					ship.goUpRight();
					break;
				case NUMPAD1:
					ship.goDownLeft();
					break;
				case NUMPAD3:
					ship.goDownRight();
					break;
				default:
					break;
				
				}

				updatePlayerImage();
				updateAllNPCImages();
				updateWinLoss();
			}
		});
	}
	
	
	
	// ======================= Updating Visual Components of the game during runtime ======================================================================================
	
	private void updatePlayerImage(){													// === Updates Player Image after the movement has been completed =================
		ship.getMyImage().setX(ship.getLoc().x*OceanExplorer2.scale);			
		ship.getMyImage().setY(ship.getLoc().y*OceanExplorer2.scale);
	}
	
	
	private void updateAllNPCImages(){													// === Updates all pirate ships Images after movements have completed =============
		Iterator<Character2> entities = NPCs.iterator();
		while (entities.hasNext()){
			Character2 entity = (Character2)entities.next();
			updateNPCs(entity);
		}
	}
	
	
	
	
	
	private void updateNPCs (Character2 npc){											// === Helper function for updateAllPirateImages ==================================
		if (!root.getChildren().contains(npc.getMyImage())){
			root.getChildren().add(npc.getMyImage());
		}
		
		npc.getMyImage().setX(npc.getLoc().x*OceanExplorer2.scale);
		npc.getMyImage().setY(npc.getLoc().y*OceanExplorer2.scale);
	}
	
	
	
	public void drawMap() {
		
		oceanMap = OceanMap2.getInstance();												// === Creates a new map ==========================================================
		boolean[][] map = oceanMap.getMap();
		oceanMap.setNumOfIsles(numOfIslands);
		oceanMap.placeIslands();
		for (int i = 0 ; i < OceanExplorer2.dimension ; i++) {							// === Creates an image representing the map data =================================
			for (int j = 0 ; j < OceanExplorer2.dimension ; j++) {				
				Rectangle rect = new Rectangle(i*scale, j*scale, scale, scale);			// === Displays scalable rectangles representing ocean and island tiles ===========
				rect.setStroke(Color.BLACK);											// === Rectangles are bordered with this color ====================================
				if (!map[i][j]) {														// === Map tiles labeled false are ocean tiles and and get that image =============
					rect.setFill(water);
				}
				else {																	// === Map tiles labeled true are island tiles and get that image =================
					rect.setFill(isle);
				}
				
				root.getChildren().add(rect);											// === Adds the tile to our anchor pane ===========================================	
			}
		}
	}
	
	// ====================================================================================================================================================================
	
	public void updateWinLoss(){
		if (ship.caught == true){
			System.out.println("You were caught by the pirates. Try again.");
			endGame = true;
			endTheGame();
		}
		
		if (ship.treasureCollected == true){
			System.out.println("You win!");
			endGame = true;
			endTheGame();
		}
	}

	
	public void endTheGame(){															// === Closes the game after a few seconds ======================================
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	
	
	public void checkIfShouldEndGame(){
		if (endGame == true){
			endTheGame();
		}
	}
	
	
	public void addCharacterofType (String type, int num){								// === Uses the CharacterFactory to generate a number of characters ==============
		for (int i = 0; i < num; i++){
			Character2 newCharacter = CharacterFactory2.createCharacter(type, ship);
			ship.addObserver(newCharacter);
			root.getChildren().add(newCharacter.getMyImage());
			NPCs.add(newCharacter);
			updateNPCs(newCharacter);
		}
	}
	
	
	
	public void setDimension (int num){
		dimension = num;
	}
	
	public void setNumOfIslands (int num){
		numOfIslands = num;
	}
	
	public void setScale (int num){
		scale = num;
	}
	
	public ArrayList <Character2> getNPCs (){
		return NPCs;
	}
	
	public static void main(String[] args) {
		launch(args);

	}

	
	
	//======================================================================================================================================================================
	
	@Override
	public void start(Stage oceanStage) throws Exception {								// === Initializes the GUI and places map elements =================================
		
		setDimension(10);																// === Changes the amount of tiles on the map ======================================
		setScale(90);																	// === Change this if map is too large/small =======================================
		setNumOfIslands(10);															// === Changes the amount of islands generated =====================================
		
		Scene scene = new Scene(root, dimension*scale, dimension*scale);				
		
		
		this.drawMap();																	// === Create the map ==============================================================
		
		
		
		this.ship = new PlayerShip2(oceanMap);											// === Instantiate our player controlled ship ======================================
		root.getChildren().add(ship.getMyImage());										// === Place ship in scene =========================================================
		
		
		
																						// === Options include: easyPirate, mediumPirate, hardPirate, treasure, easyWhirlpool,
																						//     mediumWhirlpool, and hardWhirlpool ==========================================
		addCharacterofType ("mediumPirate", 2);											// === Adds the given number of pirate ships to the map ============================
		addCharacterofType ("Treasure", 1);												// === Should only place 1 on the map as they always appear in the same tile =======
		addCharacterofType ("easyWhirlpool", 2);										// === Adds the given number of whirlpools to the map ==============================
		
		
		oceanStage.setTitle("Pirate Booty!");											// === Establish and display current scene =========================================
		oceanStage.setScene(scene);
		oceanStage.show();
		
		startSailing(scene);															// === Handle player inputs ========================================================
	}

}
