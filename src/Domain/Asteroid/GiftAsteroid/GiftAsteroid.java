package Domain.Asteroid.GiftAsteroid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.Asteroid;

public class GiftAsteroid extends Asteroid{

	protected BufferedImage giftAsteroidImage;
	private int length = 30;
	private int width = 30;
	private String gift;
	
	public GiftAsteroid (int xLoc, int yLoc, boolean freezeStatus, String gift) {
		super.xLoc = xLoc;
		super.yLoc = yLoc;
		super.freezeStatus = freezeStatus;
		try {
			giftAsteroidImage = ImageIO.read(new FileImageInputStream(new File("Assets/Gift_Asteroid.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(GiftAsteroid.class.getName()).log(Level.SEVERE,null,ex);
		}
		this.gift = gift;
	}
	/*
	 * Getter And Setter
	 */
	
	public BufferedImage getImage() {
		return this.giftAsteroidImage;
	}
	public int getLength() {
		return length;
	}
	public int getWidth() {
		return width;
	}
	public String getGift() {
		return this.gift;
	}
	public void setFreezeStatus(boolean a) {
		this.freezeStatus = a;
	}
	public boolean getFreezeStatus() {
		return this.freezeStatus;
	}
}
