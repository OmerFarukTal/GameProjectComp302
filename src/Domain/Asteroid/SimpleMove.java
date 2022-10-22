package Domain.Asteroid;

public class SimpleMove { // Strategy Pattern
	
	private int xLocRight;
	private int xLocLeft;
	private int xLocAct;
	private int xVel;
	
	public SimpleMove(int xLoc) {
		this.xLocRight = xLoc + 50;
		this.xLocLeft  = xLoc - 50;
		this.xVel = 2;
	}
	
	public int move(int xLoc) {
		
		if (xLoc <= this.xLocRight && this.xLocLeft <= xLoc) {
			xLoc += xVel;
			
		}
		else {
			xVel = - xVel;
			xLoc += xVel;
		}

		return xLoc;
	}

	public int getXLocAct() {
		return this.xLocAct;
	}
	
	public void setXLocAct(int a) {
		this.xLocAct = a;
	}
}
