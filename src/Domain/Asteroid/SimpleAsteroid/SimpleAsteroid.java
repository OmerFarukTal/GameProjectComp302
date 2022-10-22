package Domain.Asteroid.SimpleAsteroid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.Asteroid;
import Domain.Asteroid.SimpleMove;

public class SimpleAsteroid extends Asteroid{
	
	protected BufferedImage simpleAsteroidImage;
	private int length = 30;
	private int width = 30;
	private SimpleMove simpleMove;
	private boolean hasMoveLoadObject;
	
	public SimpleAsteroid (int xLoc, int yLoc, boolean freezeStatus, SimpleMove simpleMove, boolean hasMoveLoadObject) {
		super.xLoc = xLoc;
		super.yLoc = yLoc;
		super.freezeStatus = freezeStatus;
		this.simpleMove = simpleMove;
		this.hasMoveLoadObject = hasMoveLoadObject;
		
		try {
			simpleAsteroidImage = ImageIO.read(new FileImageInputStream(new File("Assets/Simple_Asteroid.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(SimpleAsteroid.class.getName()).log(Level.SEVERE,null,ex);
		}
	}	
	
	public void move() {}

	/*
	 * Getter And Setter
	 */
	
	public BufferedImage getImage() {
		return this.simpleAsteroidImage;
	}
	public int getLength() {
		return length;
	}
	public int getWidth() {
		return width;
	}
	public void setSimpleMove(SimpleMove sm) {
		this.simpleMove = sm;
	}
	public SimpleMove getSimpleMove() {
		return simpleMove;
	}
	public boolean getHasMoveLoadObject() {
		return this.hasMoveLoadObject;
	}
	public boolean getFreezeStatus() {
		return this.freezeStatus;
	}
	public void setFreezeStatus(boolean freezeStatus) {
		this.freezeStatus = freezeStatus;
	}
}








