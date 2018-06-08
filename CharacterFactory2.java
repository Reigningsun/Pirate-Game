package ccGame2;

public class CharacterFactory2 {

	public static Character2 createCharacter(String type, PlayerShip2 ship){		
		type = type.toLowerCase();
		Pirate2 pirate = new Pirate2();
		Whirlpool2 whirlpool = new Whirlpool2(ship);
		
		
		if (type.equals("easypirate")){
			pirate.setDiff(new Easy2());
			return pirate;
			
		} else if (type.equals("mediumpirate")){
			pirate.setDiff(new Medium2());
			return pirate;
			
		} else if (type.equals("hardpirate")){
			pirate.setDiff(new Hard2());
			return pirate;
			
		} else if (type.equals("treasure")){
			return new Treasure2();
			
		} else if (type.equals("easywhirlpool")){
			whirlpool.setDiff(new Easy2());
			return whirlpool;
			
		} else if (type.equals("mediumwhirlpool")){
			whirlpool.setDiff(new Medium2());
			return whirlpool;
			
		} else if (type.equals("hardwhirlpool")){
			whirlpool.setDiff(new Hard2());
			return whirlpool;
		}
		
		return null;
	}
}
