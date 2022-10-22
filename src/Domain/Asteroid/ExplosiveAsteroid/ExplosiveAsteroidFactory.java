package Domain.Asteroid.ExplosiveAsteroid;

import java.security.SecureRandom;

import Domain.Asteroid.CircularMove;
import Domain.Asteroid.Factory;

public class ExplosiveAsteroidFactory implements Factory{
	
	private static ExplosiveAsteroidFactory instance;
	
	private int payload = 25;
	private int gamePanelLength = 1820 - 3*payload;
	private int gamePanelWidth = 840 - 10*payload;
	private final SecureRandom random = new SecureRandom();

	private ExplosiveAsteroidFactory() {}
	
	public static synchronized ExplosiveAsteroidFactory getInstance() {
		if (instance == null) {
			instance = new ExplosiveAsteroidFactory();
		}
		return instance;
	}
	
	public ExplosiveAsteroid getExplosiveAsteroid() {
		double randomDouble = random.nextDouble();
		if (randomDouble >= 0.9) {
			//explosiveAsteroids[i] = new ExplosiveAsteroidMoveable(900, 400, false, 900, 400);
			int x = random.nextInt(gamePanelLength - 3*payload) + 3*payload;
			int y = random.nextInt(gamePanelWidth) + payload;
			CircularMove circularMove = null;
			return new ExplosiveAsteroidMoveable(x,y, false, x,y, circularMove, false);
		}
		else {
			int x = random.nextInt(gamePanelLength - 3*payload) + 3*payload;
			int y = random.nextInt(gamePanelWidth) + payload;
			CircularMove circularMove = new CircularMove(x,y);
			return new ExplosiveAsteroidStiff(x,y, false, x,y, circularMove, true);
		}
	}
}
