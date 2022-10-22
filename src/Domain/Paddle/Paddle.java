package Domain.Paddle;

import java.awt.*;

public class Paddle {
	private int angle = 0;
	private int angularVelocity = 5;
	private final int HEIGHT = 20;
	private int WIDTH = 100;
	private final int VELOCITY = WIDTH / 2;
	private int location = (1820 - WIDTH) / 2;
	private boolean wrapStatus = false;

	public void draw(Graphics g) {
		((Graphics2D) g).rotate(Math.toRadians(angle),location+(WIDTH/2),820);
		g.setColor(new Color(50, 50, 50));
		g.fillRoundRect(location, 810, WIDTH, HEIGHT, 10, 10);
	}

	public void moveLeft() {
		if (location - VELOCITY < 10 && !wrapStatus) {
			location = 10;
		} else if (location - VELOCITY < 10 && wrapStatus){
			location = 1810 - WIDTH;
		} else {
			location -= VELOCITY;
		}
	}

	public void moveRight() {
		if (location + WIDTH + VELOCITY > 1810 && !wrapStatus) {
			location = 1810 - WIDTH;
		} else if(location + WIDTH + VELOCITY > 1810 && wrapStatus) {
			location = 10;
		}else {
			location += VELOCITY;
		}
	}

	public void rotateRight() {
		if(angle <= 40) {
			angle+=angularVelocity;
		}
	}

	public void rotateLeft() {
		if(angle >= -40) {
			angle-=angularVelocity;
		}
	}

	public void reset() {
		location = (1810 - WIDTH) / 2;
		angle = 0;
	}

	public int getLocation() {
		return location;
	}

	public int getAngle() {
		return angle;  
	}
	
	public void setLocation(int location) {
		this.location = location;
	}
	public void setWrapStatus(boolean ws) {
		this.wrapStatus = ws;
	}
	public boolean getWrapStatus() {
		return this.wrapStatus;
	}
	public int getWidth() {
		return this.WIDTH;
	}
	public void setWidth(int w) {
		this.WIDTH = w;
	}
}