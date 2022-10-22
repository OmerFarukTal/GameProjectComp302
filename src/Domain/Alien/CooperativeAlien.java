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

public class CooperativeAlien extends Alien{
	private BufferedImage cooperativeAlienImage;
	private int xVel = 6;
	private int length = 80;
	private int width = 80;
	private int xLocRight = 1820 - length;
	private int xLocLeft = length;
	private Asteroid[] asteroids;
	private SimpleAsteroid[] simpleAsteroids;
	private FirmAsteroid[] firmAsteroids;
	private ExplosiveAsteroid[] explosiveAsteroids;
	private GiftAsteroid[] giftAsteroids;
	private boolean destroyed = false ;
	private int randomRow;
 	
	public CooperativeAlien(int yLoc, Asteroid[] asteroid, SimpleAsteroid[] simpleAsteroids, FirmAsteroid[] firmAsteroids, ExplosiveAsteroid[] explosiveAsteroids, GiftAsteroid[] giftAsteroids) {
		super.xLoc = xLocLeft+ 10;
		super.yLoc = yLoc;
		super.type = "CooperativeAlien";
		try {
			cooperativeAlienImage = ImageIO.read(new FileImageInputStream(new File("Assets/Cooperative_Alien.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(CooperativeAlien.class.getName()).log(Level.SEVERE,null,ex);
		}
		this.asteroids = asteroid;
		this.simpleAsteroids = simpleAsteroids;
		this.firmAsteroids = firmAsteroids;
		this.explosiveAsteroids = explosiveAsteroids;
		this.giftAsteroids = giftAsteroids;
		randomRow = this.chooseRandomRow();
	}
	
	private int chooseRandomRow() {
		if (this.asteroids == null)
			System.out.println("NULLLL COPERATİIVE");
		int randomY = 0;
		for (Asteroid currAst: asteroids) {
			if (currAst != null) {
				randomY = currAst.getYLoc();
				break;
			}
		}
		return randomY;
	}
	
	private void destroyAsteroids() {
		
		int simpleAstLength = simpleAsteroids.length;
		int firmAstLength = firmAsteroids.length;
		int expAstLength = explosiveAsteroids.length;
		int giftAstLength = giftAsteroids.length;
		int totalLength = simpleAstLength +  firmAstLength + expAstLength + giftAstLength;
		if (destroyed == false) {
			for (int i = 0; i < totalLength; i++) {
				if (asteroids[i] != null && asteroids[i].getYLoc() == randomRow) {// Eksik simpleAsteroid[] ve benzeri de nullanmalı
					asteroids[i]  = null;
					if (i < simpleAstLength) {
						simpleAsteroids[i] = null;
					}
					else if (i < simpleAstLength + firmAstLength) {
						firmAsteroids[i - simpleAstLength] = null; 
					}
					else if (i < simpleAstLength + firmAstLength + expAstLength) {
						explosiveAsteroids[i - (simpleAstLength + firmAstLength)] = null; 
					}
					else {
						giftAsteroids[i - (simpleAstLength + firmAstLength + expAstLength)] = null;
					}
				}
			}
			destroyed = true;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
	public void perform() {
		destroyAsteroids();
		if (xLoc <= this.xLocRight && this.xLocLeft <= xLoc) {
			xLoc += xVel;
		}
		else {
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
	
	/*
	 * Getter
	 */
	
	public BufferedImage getImage() {
		return this.cooperativeAlienImage;
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
