package util.math;

public class Vector {
	
	private int xValue, yValue;
	
	public Vector(int x, int y) {
		xValue = x;
		yValue = y;
	}
	
	public Vector(double x, double y) {
		xValue = (int) x;
		yValue = (int) y;
	}
	
	public Vector(double x, double y, int scaling) {
		xValue = (int) (x / scaling);
		yValue = (int) (y / scaling);
	}
	
	
	public synchronized int getX() {
		return xValue;
	}


	public synchronized void setX(int x) {
		xValue = x;
	}

	public synchronized int getY() {
		return yValue;
	}

	public synchronized void setY(int y) {
		yValue = y;
	}


}
