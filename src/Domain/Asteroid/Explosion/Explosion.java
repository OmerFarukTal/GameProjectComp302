package Domain.Asteroid.Explosion;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

public class Explosion {
	
	private BufferedImage explosionImage;
	private int xLoc;
	private int yLoc;
	private int radius;
	
	public Explosion(int xLoc, int yLoc) {
		try {
			explosionImage = ImageIO.read(new FileImageInputStream(new File("Assets/Explosion.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(Explosion.class.getName()).log(Level.SEVERE,null,ex);
		}
		this.radius  = 150; 
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		
	}
	
	/*
	 * Getter And Setter
	 */
	
	public BufferedImage getImage() {
		return explosionImage;
	}
	public int getXLoc() {
		return xLoc;
	}
	public int getYLoc() {
		return yLoc;
	}
	public int getRadius() {
		return radius;
	}
}
