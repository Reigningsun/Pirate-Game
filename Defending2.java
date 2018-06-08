package ccGame2;

import java.awt.Point;

public class Defending2 implements State2 {

	Pirate2 pirate2;
	Point treasurePos = new Point (OceanExplorer2.scale -2, OceanExplorer2.scale -2);
	
	public Defending2 (Pirate2 pirate2){
		this.pirate2 = pirate2;
	}
	
	@Override
	public void move() {
		Point bestMove = pirate2.getBestMove(treasurePos);						// === Move as quickly as possible to be near the treasure =============================================
		pirate2.piratePos.setLocation(bestMove.getX(), bestMove.getY());
		
	}

	@Override
	public void lookForPlayer() {
		if (pirate2.canSeePlayer() && pirate2.timeSincePlayerLastSeen >= 2){				// === Chase the player if it is still close to the pirate after it has moved toward the treasure ======
			pirate2.timeSincePlayerLastSeen = 0;
			State2 chasing = new Chasing2(pirate2);
			pirate2.setState(chasing);
			OceanExplorer2.root.getChildren().remove(pirate2.getMyImage());
			pirate2.setImg(pirate2.chasingImg);
		} else {
			pirate2.timeSincePlayerLastSeen += 1;												
		}
		
	}

	@Override
	public void relax() {
		if (pirate2.timeSincePlayerLastSeen >= 3){
			State2 idle = new Idle2(pirate2);
			pirate2.setState(idle);
			OceanExplorer2.root.getChildren().remove(pirate2.getMyImage());
			pirate2.myImage = pirate2.idleImg;
		}
	}
}
