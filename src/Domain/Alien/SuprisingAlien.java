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
import Domain.Asteroid.FirmAsteroid.FirmAsteroid;
import Domain.Asteroid.GiftAsteroid.GiftAsteroid;
import Domain.Asteroid.SimpleAsteroid.SimpleAsteroid;

public class SuprisingAlien extends Alien {
	
	private Alien alienType1;
	private BufferedImage suprisingAlienImage;
	private int length = 80;
	private int width = 80;
	private Asteroid[] asteroids;
	private SimpleAsteroid[] simpleAsteroids; 
	private FirmAsteroid[] firmAsteroids;
	private ExplosiveAsteroid[] explosiveAsteroids; 
	private GiftAsteroid[] giftAsteroids; 
	private int[][] locs;
	private double percentage;
	private int timer = 0;
	private int xLocRight = 1745;
	private String type;
	
	public SuprisingAlien(Asteroid[] asteroids, SimpleAsteroid[] simpleAsteroids, FirmAsteroid[] firmAsteroids, 
			ExplosiveAsteroid[] explosiveAsteroids, GiftAsteroid[] giftAsteroids, int[][] locs) {
		try {
			suprisingAlienImage = ImageIO.read(new FileImageInputStream(new File("Assets/Suprising_Alien.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(SuprisingAlien.class.getName()).log(Level.SEVERE,null,ex);
		}
		this.asteroids = asteroids;
		this.simpleAsteroids = simpleAsteroids;
		this.firmAsteroids = firmAsteroids;
		this.explosiveAsteroids = explosiveAsteroids;
		this.giftAsteroids = giftAsteroids;
		super.type = "SurprisingAlien";
		this.locs = locs;
		percentage = calculateDestroyedAsteroidPercentage();
		this.initializeStrategy();
	}
	
	private double calculateDestroyedAsteroidPercentage() {
		double counter = 0;
		for (int i = 0; i < asteroids.length; i++) {
			if (asteroids[i] == null) {
				counter ++;
			}
		}
		return counter / (double) asteroids.length;
	}
	
	private void initializeStrategy() {
		System.out.println("Percenatge " + percentage);
		
		if (percentage <= 0.30) {
			alienType1 = AlienFactory.getInstance().getAlien("Cooperative Alien", asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
		}
		else if ( 0.30 <= percentage && percentage <= 0.40){
			// Do nothing
		}
		else if ( 0.40 <= percentage && percentage <= 0.50){
			alienType1 = AlienFactory.getInstance().getAlien("Repairing Alien", asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
		}
		else if ( 0.50 <= percentage && percentage <= 0.60){
			alienType1 = AlienFactory.getInstance().getAlien("Protecting Alien", asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
		}
		else if ( 0.60 <= percentage && percentage <= 0.70){
			// Do nothing
		}
		else {
			alienType1 = AlienFactory.getInstance().getAlien("Protecting Alien", asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
		}
		
		if (alienType1 != null) {
			super.xLoc = alienType1.getXLoc();
			super.yLoc = alienType1.getYLoc();
		}
		else {
			super.xLoc = 500;
			super.yLoc = 400;
		}
	}
	
	public void perform() {
		timer += 5;
		if (alienType1 != null) {
			alienType1.perform();
			super.xLoc = alienType1.getXLoc();
			super.yLoc = alienType1.getYLoc();
		}
		if (percentage <= 30 && timer == 40000 && alienType1 instanceof CooperativeAlien) {
			alienType1 = AlienFactory.getInstance().getAlien("Time-wasting Alien", asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
			timer = 0;
		}
		if (alienType1 == null && timer == 5000) {
			super.xLoc += 6;
		}
	}
	
	/*
	 * Getter
	 */
	
	public BufferedImage getImage() {
		return this.suprisingAlienImage;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getXLocRight() {
		return this.xLocRight;
	}
}
