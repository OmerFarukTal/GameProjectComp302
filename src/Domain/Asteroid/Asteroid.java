package Domain.Asteroid;

public abstract class Asteroid {
	
	protected int xLoc;
	protected int yLoc;
	protected boolean freezeStatus;

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
	public boolean getFreezeStatus() {
		return freezeStatus;
	}
	public void setFreezeStatus(boolean freezeStatus) {
		this.freezeStatus = freezeStatus;
	}
}
