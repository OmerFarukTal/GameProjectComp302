package Domain.Alien;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.Asteroid;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroid;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroidFactory;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroidMoveable;
import Domain.Asteroid.FirmAsteroid.FirmAsteroid;
import Domain.Asteroid.FirmAsteroid.FirmAsteroidFactory;
import Domain.Asteroid.GiftAsteroid.GiftAsteroid;
import Domain.Asteroid.GiftAsteroid.GiftAsteroidFactory;
import Domain.Asteroid.SimpleAsteroid.SimpleAsteroid;
import Domain.Asteroid.SimpleAsteroid.SimpleAsteroidFactory;
import GameManager.RandomListGenerator;

public class RepairingAlien extends Alien{
	private BufferedImage repairingAlienImage;
	private int length = 80;
	private int width = 80;
	private Asteroid[] asteroids;
	private SimpleAsteroid[] simpleAsteroids;
	private FirmAsteroid[] firmAsteroids;
	private ExplosiveAsteroid[] explosiveAsteroids;
	private GiftAsteroid[] giftAsteroids;
	private int[][] locs;
	private int timer = 0;
	private int totalAsteroidDestroyed;
	
	public RepairingAlien (int xLoc, int yLoc, Asteroid[] asteroid, SimpleAsteroid[] simpleAsteroids, FirmAsteroid[] firmAsteroids, ExplosiveAsteroid[] explosiveAsteroids, GiftAsteroid[] giftAsteroids,int[][] locs) {
		super.xLoc = xLoc;
		super.yLoc = yLoc;
		super.type = "RepairingAlien";
		try {
			repairingAlienImage = ImageIO.read(new FileImageInputStream(new File("Assets/Repairing_Alien.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(RepairingAlien.class.getName()).log(Level.SEVERE,null,ex);
		}
		this.asteroids = asteroid;
		this.simpleAsteroids = simpleAsteroids;
		this.firmAsteroids = firmAsteroids;
		this.explosiveAsteroids = explosiveAsteroids;
		this.giftAsteroids = giftAsteroids;
		this.locs = locs;
		this.totalAsteroidDestroyed = this.countDestroyedAsteroids();
	}
	
	private int countDestroyedAsteroids() {
		int totalDestroyedAsteroidNumber = 0;
		int totalAsteroid = asteroids.length;
		
		for (int i = 0; i < totalAsteroid; i++) {
			if (asteroids[i] == null) {
				totalDestroyedAsteroidNumber += 1;
			}
		}
		return totalDestroyedAsteroidNumber;
		
	}
	
	private void createAsteroids() {
		int simpleAstLength = simpleAsteroids.length;
		int firmAstLength = firmAsteroids.length;
		int expAstLength = explosiveAsteroids.length;
		int giftAstLength = giftAsteroids.length;
		int totalLength = simpleAstLength +  firmAstLength + expAstLength + giftAstLength;
		
		for (int i = 0; i < totalLength; i++) {
			
			if (totalAsteroidDestroyed == 0) {
				break;
			}
			
			int[] randomLocIndex = RandomListGenerator.generateRandom(totalLength);
			int randI = randomLocIndex[i];
			 
			if (asteroids[i] == null) {// Eksik simpleAsteroid[] ve benzeri de nullanmalı
				if (i < simpleAstLength) {
					simpleAsteroids[i] = SimpleAsteroidFactory.getInstance().getSimpleAsteroid();
					simpleAsteroids[i].setXLoc(locs[randI][0]);
					simpleAsteroids[i].setYLoc(locs[randI][1]);
					asteroids[i] = simpleAsteroids[i];
				}
				else if (i < simpleAstLength + firmAstLength) {
					int i_th = i - simpleAstLength;
					firmAsteroids[i_th] = FirmAsteroidFactory.getInstance().getFirmAsteroid();
					firmAsteroids[i_th].setXLoc(locs[randI][0]);
					firmAsteroids[i_th].setYLoc(locs[randI][1]);
					asteroids[i] = firmAsteroids[i_th];
				}
				else if (i < simpleAstLength + firmAstLength + expAstLength) {
					int i_th = i - simpleAstLength - firmAstLength;
					explosiveAsteroids[i_th] = ExplosiveAsteroidFactory.getInstance().getExplosiveAsteroid();
					if (explosiveAsteroids[i_th] instanceof ExplosiveAsteroidMoveable) {
						ExplosiveAsteroidMoveable curr = (ExplosiveAsteroidMoveable) explosiveAsteroids[i_th];
						curr.setXcenterYCenter(locs[randI][0], locs[randI][1]);
					}
					explosiveAsteroids[i_th].setXLoc(locs[randI][0]);
					explosiveAsteroids[i_th].setYLoc(locs[randI][1]);
					asteroids[i] = explosiveAsteroids[i_th];
				}
				else {
					int i_th = i - simpleAstLength - firmAstLength - expAstLength;
					giftAsteroids[i_th] = GiftAsteroidFactory.getInstance().getGiftAsteroid(i % 11);
					giftAsteroids[i_th].setXLoc(locs[randI][0]);
					giftAsteroids[i_th].setYLoc(locs[randI][1]);
					asteroids[i] = giftAsteroids[i_th];
				}
				break;
			}
		}
		
		totalAsteroidDestroyed --;
	}
	
	public void perform() {
		timer += 5;
		if (timer == 5000) {
			createAsteroids();
			timer = 0;
		}
	}

	/*
	 * Getter
	 */
	
	public BufferedImage getImage() {
		return this.repairingAlienImage;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getWidth() {
		return this.width;
	}
}
