package Domain.Asteroid.SimpleAsteroid;

import java.security.SecureRandom;
import java.util.Random;

import Domain.Asteroid.Factory;
import Domain.Asteroid.SimpleMove;

public class SimpleAsteroidFactory implements Factory { // Factory and Singleton Pattern
	
	private static SimpleAsteroidFactory instance;
	
	private int payload = 25;
	private int gamePanelLength = 1820 - 2*payload;
	private int gamePanelWidth = 840 - 2*payload;
	private SecureRandom secureRandom = new SecureRandom();
	private Random moveOrStiff = new Random();
	
	private SimpleAsteroidFactory() {}
	
	public static synchronized SimpleAsteroidFactory getInstance() {
		
		if (instance == null) {
			instance = new SimpleAsteroidFactory();
		}
		return instance;
	}
	
	public SimpleAsteroid getSimpleAsteroid() {
		
		//simpleAsteroids.add(new SimpleAsteroid(512,260,true));
		double random = moveOrStiff.nextDouble();
		if (random >= 0.1) {
			SimpleMove simpleMove = null;
			return new SimpleAsteroidStiff(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false,simpleMove,true);
		}
		else {
			SimpleMove sm = new SimpleMove(secureRandom.nextInt(gamePanelLength) + payload);
			return new SimpleAsteroidMoveable(secureRandom.nextInt(gamePanelLength) + payload, secureRandom.nextInt(gamePanelWidth) + payload,false,sm,false);
		}
	}
	
}
