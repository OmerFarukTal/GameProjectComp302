package Domain.Alien;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.Asteroid;

public class TimeWastingAlien extends Alien{
	private BufferedImage timeWastingAlienImage;
	private int length = 80;
	private int width = 80;
	private Asteroid[] asteroids;
	private int timer = 0;
	
	public TimeWastingAlien(int xLoc, int yLoc, Asteroid[] asteroids) {
		super.xLoc = xLoc;
		super.yLoc = yLoc;
		super.type = "TimeWastingAlien";
		try {
			timeWastingAlienImage = ImageIO.read(new FileImageInputStream(new File("Assets/Time_Wasting_Alien.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(TimeWastingAlien.class.getName()).log(Level.SEVERE,null,ex);
		}
		this.asteroids = asteroids;
		this.freezeAsteroids();
	}
	
	private void freezeAsteroids() {
		int counter = 0;
		for (int i = 0; i < asteroids.length; i++) {
			if (asteroids[i] != null && counter < 8) {
				asteroids[i].setFreezeStatus(true);
				counter++;
			}
		}
	}
	
	private void meltAsteroids() {
		for (int i = 0; i < asteroids.length; i++) {
			if (asteroids[i] != null) {
				asteroids[i].setFreezeStatus(false);
			}
		}
	}
	
	public void perform() {
		timer += 5;
		if (timer == 5000) {
			meltAsteroids();
			timer = 0;
		}
	}
	
	/*
	 * Getter
	 */
	
	public BufferedImage getImage() {
		return this.timeWastingAlienImage;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getWidth() {
		return this.width;
	}

}
