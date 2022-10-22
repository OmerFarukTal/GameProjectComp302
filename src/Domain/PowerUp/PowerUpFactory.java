package Domain.PowerUp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

public class PowerUpFactory {
	
	private static PowerUpFactory instance;
	
	private PowerUpFactory() {}
	
	public static synchronized PowerUpFactory getInstance() {
		if (instance == null ) {
			instance = new PowerUpFactory();
		}
		return instance;
	}
	
	public PowerUp getPowerUp(String PowerUpName, int xLoc, int yLoc) {
		if (PowerUpName.equals("Chance")) {
			return new ChancePowerUp(xLoc, yLoc);
		}
		else if (PowerUpName.equals("Taller Paddle")) {
			return new TallerPaddlePowerUp(xLoc, yLoc);
		}
		else if (PowerUpName.equals("Magnet")) {
			return new MagnetPowerUp(xLoc, yLoc);
		}
		else if (PowerUpName.equals("Wrap")) {
			return new WrapPowerUp(xLoc, yLoc);
		}
		return null;
	}
	


}
