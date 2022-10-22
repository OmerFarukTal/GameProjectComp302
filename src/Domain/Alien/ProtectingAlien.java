package Domain.Alien;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

public class ProtectingAlien extends Alien {
	
	private BufferedImage protectingAlienImage;
	private int xVel = 6;
	private int length = 80;
	private int width = 80;
	private int xLocRight = 1820 - length;
	private int xLocLeft = length;
	
	public ProtectingAlien(int xLoc, int yLoc) {
		super.xLoc = xLoc;
		super.yLoc = yLoc;
		super.type = "ProtectingAlien";
		try {
			protectingAlienImage = ImageIO.read(new FileImageInputStream(new File("Assets/Protecting_Alien.png")));
		}
		catch (IOException ex) {
			Logger.getLogger(ProtectingAlien.class.getName()).log(Level.SEVERE,null,ex);
		}
	}
	
	private void move() {
		if (xLoc <= this.xLocRight && this.xLocLeft <= xLoc) {
			xLoc += xVel;
			
		}
		else {
			xVel = - xVel;
			xLoc += xVel;
		}
	}
	
	public void perform() {
		move();
	}
 	
	/*
	 * Getter
	 */
	
	public BufferedImage getImage() {
		return this.protectingAlienImage;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getWidth() {
		return this.width;
	}
}
