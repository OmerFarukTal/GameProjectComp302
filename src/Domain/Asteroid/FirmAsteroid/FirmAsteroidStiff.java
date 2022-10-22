package Domain.Asteroid.FirmAsteroid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.SimpleMove;

public class FirmAsteroidStiff extends FirmAsteroid{
	public FirmAsteroidStiff(int xLoc, int yLoc, boolean freezeStatus, int radius, SimpleMove simpleMove, boolean hasMoveLoadObject) {
		super(xLoc, yLoc, freezeStatus, radius, simpleMove, hasMoveLoadObject);
		try {
			firmAsteroidImage = ImageIO.read(new FileImageInputStream(new File("Assets/Firm_Asteroid.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(FirmAsteroidStiff.class.getName()).log(Level.SEVERE,null,ex);
		}
		this.setSimpleMove(null);
	}
}
