package Domain.Asteroid.FirmAsteroid;

import Domain.Asteroid.Factory;
import Domain.Asteroid.SimpleMove;

public class FirmAsteroidLoadFactory  implements Factory{

	private static FirmAsteroidLoadFactory instance;
	
	private FirmAsteroidLoadFactory() {}
	
	public static synchronized FirmAsteroidLoadFactory getInstance() {
		
		if (instance == null) {
			instance = new FirmAsteroidLoadFactory();
		}
		return instance;
	}
	
	public FirmAsteroid getFirmAsteroid(boolean asteroidHasMove, int xLoc, int yLoc, boolean freezeStatus, int radius, int actXLoc ) {

		if (!asteroidHasMove) {
			SimpleMove SimpleMove = null;
			return new FirmAsteroidStiff(xLoc, yLoc, freezeStatus , radius, SimpleMove,asteroidHasMove);
		}
		else {
			SimpleMove sm = new SimpleMove(actXLoc);
			return new FirmAsteroidMoveable(xLoc, yLoc ,freezeStatus,radius, sm,asteroidHasMove);
		}
	} 
}