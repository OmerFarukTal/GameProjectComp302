package Domain.Asteroid.ExplosiveAsteroid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.Asteroid;
import Domain.Asteroid.CircularMove;

public class ExplosiveAsteroid extends Asteroid{
	
	protected BufferedImage explosiveAsteroidImage;
	private int xCenter;
	private int yCenter;
	private int length = 60;
	private int width = 60;
	private CircularMove circularMove;
	private boolean hasMoveLoadObject;
	
	public ExplosiveAsteroid(int xLoc, int yLoc, boolean freezeStatus, int xCenter, int yCenter, CircularMove circularMove, boolean hasMoveLoadObject) {
		super.xLoc = xLoc;
		super.yLoc = yLoc;
		super.freezeStatus = freezeStatus;
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.circularMove = circularMove;
		this.hasMoveLoadObject = hasMoveLoadObject;
		try {
			explosiveAsteroidImage = ImageIO.read(new FileImageInputStream(new File("Assets/Explosive_Asteroid.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(ExplosiveAsteroid.class.getName()).log(Level.SEVERE,null,ex);
		}
	}

	public void move() {}

	/*
	 * Getter And Setter
	 */

	public BufferedImage getImage() {
		return this.explosiveAsteroidImage;
	}
	public int getLength() {
		return length;
	}
	public int getWidth() {
		return width;
	}
	public void setCircularMove(CircularMove cm) {
		this.circularMove = cm;
	}
	public CircularMove getCircularMove() {
		return circularMove;
	}
	public boolean getHasMoveLoadObject() {
		return this.hasMoveLoadObject;
	}
	public boolean getFreezeStatus() {
		return this.freezeStatus;
	}
}
