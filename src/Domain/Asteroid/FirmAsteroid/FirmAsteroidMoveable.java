package Domain.Asteroid.FirmAsteroid;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.SimpleMove;

public class FirmAsteroidMoveable extends FirmAsteroid{
	
	private SimpleMove simpleMove;
	
	public FirmAsteroidMoveable(int xLoc, int yLoc, boolean freezeStatus, int radius, SimpleMove simpleMove, boolean hasMoveLoadObject) {
		super(xLoc, yLoc, freezeStatus, radius, simpleMove, hasMoveLoadObject);
		try {
			firmAsteroidImage = ImageIO.read(new FileImageInputStream(new File("Assets/Firm_Asteroid.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(FirmAsteroidMoveable.class.getName()).log(Level.SEVERE,null,ex);
		}
		
		if(this.simpleMove == null) {
			this.simpleMove = new SimpleMove(xLoc);
		} else {
			this.simpleMove = simpleMove;
		}
	}
	
	public void move() {
		this.xLoc = simpleMove.move(this.xLoc);
	}

	public void setSimpleMove(SimpleMove sm) {
		this.simpleMove = sm;
	}
	
	@Override
	public void setXLoc(int xLoc) {
		super.setXLoc(xLoc);
		this.simpleMove = new SimpleMove(xLoc);
	}
	
}
