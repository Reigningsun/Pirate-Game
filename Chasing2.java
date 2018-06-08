package ccGame2;

import java.awt.Point;

public class Chasing2 implements State2{

	Pirate2 pirate2;
	
	public Chasing2 (Pirate2 pirate2){
		this.pirate2 = pirate2;
	}
	
	@Override
	public void move() {																// === Move as quickly as possible to the players location =======================================
		Point bestMove = pirate2.getBestMove(pirate2.playerPos);
		pirate2.piratePos.setLocation(bestMove.getX(), bestMove.getY());
	}

	@Override
	public void lookForPlayer() {														// === Resets time since last seen and gives a small window of time for the player to escape =====
		if (pirate2.canSeePlayer() && pirate2.timeSincePlayerLastSeen > 0){
			pirate2.timeSincePlayerLastSeen = 0;
		} else {
			pirate2.timeSincePlayerLastSeen += 1;
		}
		
	}

	@Override
	public void relax() {
		State2 defending = new Defending2(pirate2); 
		pirate2.setState(defending);
		OceanExplorer2.root.getChildren().remove(pirate2.getMyImage());
		pirate2.setImg(pirate2.defendingImg);
	}
}
