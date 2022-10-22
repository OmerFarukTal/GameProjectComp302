package Domain.Alien;

import Domain.Asteroid.Asteroid;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroid;
import Domain.Asteroid.FirmAsteroid.FirmAsteroid;
import Domain.Asteroid.GiftAsteroid.GiftAsteroid;
import Domain.Asteroid.SimpleAsteroid.SimpleAsteroid;

public class AlienFactory {
	
	private static AlienFactory instance;
	private int asteroidAmount;
	private AlienFactory() {}
	
	public static synchronized AlienFactory getInstance() {
		if (instance == null) {
			instance = new AlienFactory();
		}
		return instance;
	}
	
	public Alien getAlien(String alienType, Asteroid[] asteroids, SimpleAsteroid[] simpleAsteroids, FirmAsteroid[] firmAsteroids, 
			ExplosiveAsteroid[] explosiveAsteroids, GiftAsteroid[] giftAsteroids, int[][] locs) {
		
		int yOfLowest = 0;
		for(int i = 0; i < asteroids.length; i++) {
			if(asteroids[i] != null) {
				if(asteroids[i].getYLoc() > yOfLowest) {
					yOfLowest = asteroids[i].getYLoc();
				}
			}
		}
		
		if (alienType.equals("Protecting Alien")) {
			return new ProtectingAlien(500, yOfLowest+100);
		}
		else if (alienType.equals("Cooperative Alien")) {
			return new CooperativeAlien(500, asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids);
		}
		else if (alienType.equals("Repairing Alien")) {
			return new RepairingAlien(500, 500, asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
		}
		else if (alienType.equals("Time-wasting Alien")) {
			return new TimeWastingAlien(500, 500, asteroids);
		}
		else if (alienType.equals("Suprising Alien")){
			return new SuprisingAlien(asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
		}
		return null;
	}
}
