package Domain.Asteroid.GiftAsteroid;

import java.security.SecureRandom;

import Domain.Asteroid.Factory;

public class GiftAsteroidFactory implements Factory{
	
	private static GiftAsteroidFactory instance;
	private int payload = 25;
	private int gamePanelLength = 1820 - 2*payload;
	private int gamePanelWidth = 840 - 2*payload;
	private SecureRandom secureRandom = new SecureRandom();
	
	private GiftAsteroidFactory() {}
	
	public static synchronized GiftAsteroidFactory getInstance() {
		
		if (instance == null) {
			instance = new GiftAsteroidFactory();
		}
		return instance;
	}
	
	public GiftAsteroid getGiftAsteroid(int randomGiftNumber) {
		
		if (randomGiftNumber == 0) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Taller");
		}
		else if (randomGiftNumber == 1) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Magnet");
		}
		else if (randomGiftNumber == 2) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Laser");
		}
		else if (randomGiftNumber == 3) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Chance");
		}
		else if (randomGiftNumber == 4) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Wrap");
		}
		else if (randomGiftNumber == 5) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Gang-of-Balls");
		}
		else if (randomGiftNumber == 6) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Repairing Alien");
		}
		else if (randomGiftNumber == 7) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Protecting Alien");
		}
		else if (randomGiftNumber == 8) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Cooperative Alien");
		}
		else if (randomGiftNumber == 9) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Time-wasting Alien");
		}
		else if (randomGiftNumber == 10) {
			return new GiftAsteroid(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, "Suprising Alien");
		}
		return null;
	}
}
