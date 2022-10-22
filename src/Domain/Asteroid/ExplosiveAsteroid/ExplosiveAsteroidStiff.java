package Domain.Asteroid.ExplosiveAsteroid;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.CircularMove;

public class ExplosiveAsteroidStiff extends ExplosiveAsteroid{

	private CircularMove circularMove;
	public ExplosiveAsteroidStiff(int xLoc, int yLoc, boolean freezeStatus, int xCenter, int yCenter, CircularMove circularMove, boolean hasMoveLoadObject) {
		super(xLoc, yLoc, freezeStatus, xCenter, yCenter, circularMove, hasMoveLoadObject);
		try {
			explosiveAsteroidImage = ImageIO.read(new FileImageInputStream(new File("Assets/Explosive_Asteroid.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(ExplosiveAsteroidStiff.class.getName()).log(Level.SEVERE,null,ex);
		}
		this.setCircularMove(null);
	}
}
