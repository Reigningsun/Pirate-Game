package ccGame2;

import java.awt.Point;


public class Idle2 implements State2 {

	Pirate2 pirate2;
	
	public Idle2 (Pirate2 pirate2){
		this.pirate2 = pirate2;
	}

	@Override
	public void move() {
		Point randMove = pirate2.getRandMove();
		pirate2.piratePos.setLocation(randMove.getX(), randMove.getY());
	}

	
	
	@Override
	public void lookForPlayer() {
		if (pirate2.canSeePlayer() && pirate2.timeSincePlayerLastSeen > 0){
			pirate2.timeSincePlayerLastSeen = 0;
			State2 defending = new Defending2(pirate2); 
			pirate2.setState(defending);
			OceanExplorer2.root.getChildren().remove(pirate2.getMyImage());
			pirate2.setImg(pirate2.defendingImg);
		} else {
			pirate2.timeSincePlayerLastSeen += 1;
		}
	}

	
	
	@Override
	public void relax() {
		State2 idle = new Idle2(pirate2);
		pirate2.setState(idle);
	}
}
