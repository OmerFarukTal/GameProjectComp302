package Domain.Asteroid.ExplosiveAsteroid;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.CircularMove;

public class ExplosiveAsteroidMoveable extends ExplosiveAsteroid{
	private CircularMove circularMove;
	
	public ExplosiveAsteroidMoveable(int xLoc, int yLoc, boolean freezeStatus, int xCenter, int yCenter, CircularMove circularMove, boolean hasMoveLoadObject) {
		super(xLoc, yLoc, freezeStatus, xCenter, yCenter, circularMove,hasMoveLoadObject);
		try {
			explosiveAsteroidImage = ImageIO.read(new FileImageInputStream(new File("Assets/Explosive_Asteroid.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(ExplosiveAsteroidMoveable.class.getName()).log(Level.SEVERE,null,ex);
		}
		if(this.circularMove == null) {
			this.circularMove = new CircularMove(xCenter,yCenter);
		} else {
			this.circularMove = circularMove;
		}
	}
	
	public void move() {
		
		int[] coordinates = circularMove.move();
		
		this.xLoc = coordinates[0];
		this.yLoc = coordinates[1];
	}

	public void setCircularMove(CircularMove cm) {
		this.circularMove = cm;
	}
	
	public void setXcenterYCenter(int xCenter, int yCenter) {
		this.circularMove = new CircularMove(xCenter, yCenter);
	}
}
