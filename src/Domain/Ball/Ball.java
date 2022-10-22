package Domain.Ball;

import java.awt.*;
import java.util.Observable;

public class Ball extends Observable {
	private int xLocation = 900;
	private int yLocation = 790;
	private double xVelocity = 0;
	private double yVelocity = -(4*Math.sqrt(2));
	private final double RADIUS = 8.5;
	private boolean intersectable = true;

	/*
	 * @requires -> X coordinate location and the angle of the paddle should be given. The game shouldn't be paused.
	 * @modifies -> X and Y coordinate of the ball will be changed, the ball will move.
	 */
	public void move(int paddleLocation, int paddleAngle, int paddleWidth) {
		
		xLocation += xVelocity;
		yLocation += yVelocity;

		if (xLocation < 10) {
			xLocation = 10;
			reflectHorizontal();
		} else if (xLocation > 1820 - RADIUS * 2) {
			xLocation = (int) (1820 - RADIUS * 2);
			reflectHorizontal();
		}

		if (yLocation < 10) {
			yLocation = 10;
			reflectVertical();
		}

		if (intersectionWithPaddle(paddleLocation,paddleAngle,paddleWidth)) {

			double incomingAngleOverall;
			if(xVelocity == 0) {
				incomingAngleOverall = Math.toRadians(90);
			} else {
				incomingAngleOverall = Math.atan(Math.abs(yVelocity)/Math.abs(xVelocity));
			}

			double totalVel = 4*Math.sqrt(2);
			double outgoingAngle;
			double paddleAngleAbs = Math.toRadians(Math.abs(paddleAngle));

			if(paddleAngle<0 && xVelocity >= 0) {
				outgoingAngle = incomingAngleOverall + (2*paddleAngleAbs);
				yVelocity = -(totalVel*Math.sin(outgoingAngle));
				xVelocity = (totalVel*Math.cos(outgoingAngle));
			} else if(paddleAngle<0 && xVelocity <= 0) {
				outgoingAngle = incomingAngleOverall - (2*paddleAngleAbs);
				yVelocity = -(totalVel*Math.sin(outgoingAngle));
				xVelocity = -(totalVel*Math.cos(outgoingAngle));
			} else if(paddleAngle>0 && xVelocity >= 0) {
				outgoingAngle = incomingAngleOverall - (2*paddleAngleAbs);
				yVelocity = -(totalVel*Math.sin(outgoingAngle));
				xVelocity =  (totalVel*Math.cos(outgoingAngle));
			} else if(paddleAngle>0 && xVelocity <= 0) {
				outgoingAngle = incomingAngleOverall + (2*paddleAngleAbs);
				yVelocity = -(totalVel*Math.sin(outgoingAngle));
				xVelocity = -(totalVel*Math.cos(outgoingAngle));
			} else {
				yVelocity= -yVelocity;
			}
		}
		
		if (yLocation >= 860) {
			setChanged();
			notifyObservers();
		}
	}

	public void draw(Graphics g) {
		g.setColor(new Color(255, 88, 81));
		g.fillOval(xLocation, yLocation, (int) RADIUS * 2, (int)RADIUS * 2);
	}

	public boolean intersectionWithPaddle(int paddleLocation, int paddleAngle, int paddleWidth) {

		if(!intersectable) {
			return false;
		}
		
		int[] xCoors = new int[4];
		int[] yCoors = new int[4];

		double paddleInnerAngle = Math.atan(0.20);
		double lengthToCornerFromCenter = Math.sqrt((10*10) + (paddleWidth/2*paddleWidth/2));

		int centerXCoord = paddleLocation + paddleWidth/2;
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

		return interPoly.intersects(xLocation, yLocation, 17, 17);

	}

	public void reflectVertical() {
		if(Math.abs(yVelocity) < 1) {
			yVelocity = 1;
		}
		yVelocity = -yVelocity;
	}

	public void reflectHorizontal() {
		if(Math.abs(xVelocity) < 1) {
			xVelocity = 1;
		}
		xVelocity = -xVelocity;
	}

	public void reset() {
		xLocation = 900;
		yLocation = 790;
		xVelocity = 0;
		yVelocity = -(4*Math.sqrt(2));
	}
	
	/*
	 * Getter And Setter
	 */
	
	public int getXLocation() {
		return xLocation;
	}
	public int getYLocation() {
		return yLocation;
	}
	public double getRadius() {
		return RADIUS;
	}
	public void setXLocation(int xLocation) {
		this.xLocation = xLocation;
	}
	public double getxVelocity() {
		return xVelocity;
	}
	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	public double getyVelocity() {
		return yVelocity;
	}
	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	public void setYLocation(int yLocation) {
		this.yLocation = yLocation;
	}
}
