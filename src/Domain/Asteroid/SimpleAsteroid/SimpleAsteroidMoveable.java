package Domain.Asteroid.SimpleAsteroid;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Domain.Asteroid.SimpleMove;

public class SimpleAsteroidMoveable extends SimpleAsteroid{

	private SimpleMove simpleMove;
	
	public SimpleAsteroidMoveable (int xLoc, int yLoc, boolean freezeStatus, SimpleMove simpleMove, boolean hasMoveLoadObject) {
		super(xLoc, yLoc, freezeStatus,simpleMove,hasMoveLoadObject);
		try {
			simpleAsteroidImage = ImageIO.read(new FileImageInputStream(new File("Assets/Simple_Asteroid.png")));
		}
		catch(IOException ex) {
			Logger.getLogger(SimpleAsteroidMoveable.class.getName()).log(Level.SEVERE,null,ex);
		}

		if(this.simpleMove == null) {
			this.simpleMove = new SimpleMove(xLoc);
		} else {
			this.simpleMove = simpleMove;
		}
	}	
	
	@Override
	public void setXLoc(int xLoc) {
		super.setXLoc(xLoc);
		this.simpleMove = new SimpleMove(xLoc);
	}
	
	public void move() {
		this.xLoc = simpleMove.move(this.xLoc);
	}

	public void setSimpleMove(SimpleMove sm) {
		this.simpleMove = sm;
	}
	
	

	
	
}
