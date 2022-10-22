package Domain.Asteroid.ExplosiveAsteroid;

import Domain.Asteroid.Factory;
import Domain.Asteroid.CircularMove;

public class ExplosiveAsteroidLoadFactory  implements Factory{

	private static ExplosiveAsteroidLoadFactory instance;
	
	private ExplosiveAsteroidLoadFactory() {}
	
	public static synchronized ExplosiveAsteroidLoadFactory getInstance() {
		
		if (instance == null) {
			instance = new ExplosiveAsteroidLoadFactory();
		}
		return instance;
	}
	
	public ExplosiveAsteroid getExplosiveAsteroid(boolean asteroidHasMove, int xLoc, int yLoc, boolean freezeStatus, int actXLoc, int actYLoc) {

		if (!asteroidHasMove) {
			CircularMove CircularMove = null;
			return new ExplosiveAsteroidStiff(xLoc, yLoc, freezeStatus , xLoc+30, yLoc+30, CircularMove,asteroidHasMove);
		}
		else {
			CircularMove cm = new CircularMove(actXLoc,actYLoc);
			return new ExplosiveAsteroidMoveable(xLoc, yLoc ,freezeStatus,xLoc+30, yLoc+30, cm,asteroidHasMove);
		}
	} 
}