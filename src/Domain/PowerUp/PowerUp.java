package Domain.PowerUp;

import java.awt.image.BufferedImage;

public class PowerUp {
	
	protected int xLoc;
	protected int yLoc;
	protected int yVel;
	protected BufferedImage Image;
	protected int length;
	protected int width;
	protected String type;
	
	public PowerUp(int xLoc, int yLoc) {
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.length  = 40;
		this.width = 40;
		this.yVel = 6;
	}
	

	public void fall() {
		this.yLoc += this.yVel; 
	}
	
	public BufferedImage getImage() {
		return Image;
	}
	public void setImage(BufferedImage image) {
		Image = image;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getxLoc() {
		return xLoc;
	}
	public void setxLoc(int xLoc) {
		this.xLoc = xLoc;
	}
	public int getyLoc() {
		return yLoc;
	}
	public void setyLoc(int yLoc) {
		this.yLoc = yLoc;
	}
	public int getyVel() {
		return yVel;
	}
	public void setyVel(int yVel) {
		this.yVel = yVel;
	}
	public String getType() {
		return this.type;
	}
}
