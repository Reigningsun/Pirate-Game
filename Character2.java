package ccGame2;

import java.awt.Point;
import java.util.Observer;

import javafx.scene.image.ImageView;

public interface Character2 extends Observer{
	ImageView getMyImage();
	Point getLoc();
	void setLoc(int x, int y);
	void setDiff(Difficulty diff);
	void setImg(ImageView img);
	boolean isTreasure();
}
