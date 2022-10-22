package Domain.Asteroid.SimpleAsteroid;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.SimpleMove;

public class SimpleAsteroidStiff extends SimpleAsteroid{

	private SimpleMove simpleMove;
	
	public SimpleAsteroidStiff (int xLoc, int yLoc, boolean freezeStatus, SimpleMove simpleMove, boolean hasMoveLoadObject) {
		super(xLoc, yLoc, freezeStatus, simpleMove,hasMoveLoadObject);
		try {
			simpleAsteroidImage = ImageIO.read(new FileImageInputStream(new File("Assets/Simple_Asteroid.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(SimpleAsteroidStiff.class.getName()).log(Level.SEVERE,null,ex);
		}
		this.setSimpleMove(null);
	}	
	
	
	
	
	

	
}
