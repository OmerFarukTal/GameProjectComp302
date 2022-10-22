package Domain.Asteroid;

public class CircularMove { // Strategy Pattern
	
	private int xCenter;
	private int yCenter;
	private int xLocAct;
	private int yLocAct;
	private int angle;
	private double degree;
	private int radius;
	
	public CircularMove(int xCenter, int yCenter) {
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.angle = 0;
		this.degree = 0;
		this.radius = 75;
	}
	
	public int[] move() {
		int[] coordinates = new int[2];
		degree = angle * Math.PI * 2/ 360.0;
		
		coordinates[0] = (int) (xCenter + radius * Math.cos(degree));
		coordinates[1] = (int) (yCenter + radius * Math.sin(degree));
		
		angle += 1;
		angle = angle % 360;
		
		return coordinates;
	}

	public int getXLocAct() {
		return this.xLocAct;
	}
	public int getYLocAct() {
		return this.yLocAct;
	}
	public void setXLocAct(int a) {
		this.xLocAct = a;
	}
	public void setYLocAct(int a) {
		this.yLocAct = a;
	}
}

