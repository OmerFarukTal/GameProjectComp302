package Domain.PowerUp;

import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

public class TallerPaddlePowerUp extends PowerUp {
	
	private int timer = 0;

	public TallerPaddlePowerUp(int xLoc, int yLoc) {
		super(xLoc, yLoc);
		this.type = "TallerPaddlePowerUp";
		try {
			super.Image = ImageIO.read(new FileImageInputStream(new File("Assets/Taller_Paddle_Power_Up.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(TallerPaddlePowerUp.class.getName()).log(Level.SEVERE,null,ex);
		}
	}
	
	public boolean catchPaddle(int paddleLocation, int paddleAngle, int width) {

		int[] xCoors = new int[4];
		int[] yCoors = new int[4];

		double paddleInnerAngle = Math.atan(0.20);
		double lengthToCornerFromCenter = Math.sqrt((10*10) + (width/2*width/2));

		int centerXCoord = paddleLocation + width/2;
		int centerYCoord = 820;

		double firstCornerYLength = Math.sin(Math.toRadians(paddleAngle) + paddleInnerAngle)*lengthToCornerFromCenter;
		double firstCornerXLength = Math.cos(Math.toRadians(paddleAngle) + paddleInnerAngle)*lengthToCornerFromCenter;

		double ndCornerXDistanceFromFirst = 20*Math.sin(Math.toRadians(paddleAngle));
		double ndCorderYDistanceFromFirst = 20*Math.cos(Math.toRadians(paddleAngle));

		xCoors[0] = (int)(centerXCoord - firstCornerXLength);
		xCoors[1] = (int)(xCoors[0] - ndCornerXDistanceFromFirst);
		xCoors[2] = (int)(centerXCoord + firstCornerXLength);
		xCoors[3] = (int)(xCoors[2] + ndCornerXDistanceFromFirst);

		yCoors[0] = (int)(centerYCoord - firstCornerYLength);
		yCoors[1] = (int)(yCoors[0] + ndCorderYDistanceFromFirst);
		yCoors[2] = (int)(centerYCoord + firstCornerYLength);
		yCoors[3] = (int)(yCoors[2] - ndCorderYDistanceFromFirst);

		Polygon interPoly = new Polygon(xCoors, yCoors, 4);

		return interPoly.intersects(super.xLoc, super.yLoc, 17, 17);
	}
	
	public boolean perform() {
		timer += 5;
		if (timer <= 15000) {
			return true; // Paddle should be doubled
		}
		else {
			timer = 0;
			return false; // if false returned then the powerUp should die 
		}
	}

}
