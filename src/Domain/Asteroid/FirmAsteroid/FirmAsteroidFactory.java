package Domain.Asteroid.FirmAsteroid;

import java.security.SecureRandom;
import java.util.Random;

import Domain.Asteroid.Factory;
import Domain.Asteroid.SimpleMove;

public class FirmAsteroidFactory implements Factory{
	
	private static FirmAsteroidFactory instance;

	private int payload = 25;
	private int gamePanelLength = 1820 - 2*payload;
	private int gamePanelWidth = 840 - 2*payload;
	private SecureRandom secureRandom = new SecureRandom();
	private Random moveOrStiff = new Random();
	private Random randomRadii = new Random();
	
	private FirmAsteroidFactory() {}
	
	public static synchronized FirmAsteroidFactory getInstance() {
		if (instance == null) {
			instance = new FirmAsteroidFactory();
		}
		return instance;
	}
	
	public FirmAsteroid getFirmAsteroid() {
		double random = moveOrStiff.nextDouble();
		int radius = 30 + randomRadii.nextInt(5);
		if (random >= 0.1) {
			SimpleMove simpleMove = null;
			return new FirmAsteroidStiff(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, radius, simpleMove, false);
		}
		else {
			SimpleMove sm = new SimpleMove(secureRandom.nextInt(gamePanelLength) + payload);
			return new FirmAsteroidMoveable(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false, radius, sm, true);
		}
	}
}
