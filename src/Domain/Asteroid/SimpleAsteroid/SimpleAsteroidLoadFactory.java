package Domain.Asteroid.SimpleAsteroid;

import Domain.Asteroid.Factory;
import Domain.Asteroid.SimpleMove;

public class SimpleAsteroidLoadFactory  implements Factory{

	private static SimpleAsteroidLoadFactory instance;
	
	private SimpleAsteroidLoadFactory() {}
	
	public static synchronized SimpleAsteroidLoadFactory getInstance() {
		
		if (instance == null) {
			instance = new SimpleAsteroidLoadFactory();
		}
		return instance;
	}
	
	public SimpleAsteroid getSimpleAsteroid(boolean asteroidHasMove, int xLoc, int yLoc, boolean freezeStatus, int actXLoc) {

		if (!asteroidHasMove) {
			SimpleMove simpleMove = null;
			return new SimpleAsteroidStiff(xLoc, yLoc, freezeStatus , simpleMove,asteroidHasMove);
		}
		else {
			SimpleMove sm = new SimpleMove(actXLoc);
			return new SimpleAsteroidMoveable(xLoc, yLoc ,freezeStatus,sm,asteroidHasMove);
		}
	} 
}
