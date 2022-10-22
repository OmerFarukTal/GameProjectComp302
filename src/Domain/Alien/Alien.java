package Domain.Alien;

public abstract class Alien {

	protected int xLoc;
	protected int yLoc;
	protected String type;
	
	public void perform() {}
	
	public int getXLoc() {
		return xLoc;
	}
	public void setXLoc(int xLoc) {
		this.xLoc = xLoc;
	}
	public int getYLoc() {
		return yLoc;
	}
	public void setYLoc(int yLoc) {
		this.yLoc = yLoc;
	}
	public void setType(String t) {
		this.type = t;
	}
	public String getType() {
		return this.type;
	}
}
