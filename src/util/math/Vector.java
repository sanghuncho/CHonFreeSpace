package util.math;

public abstract class Vector implements Comparable<Vector>{
	
	protected int dimensions, xValue, yValue, zValue;
	
	protected  Vector(int x, int y) {
		xValue = x;
		yValue = y;
		dimensions = 2;
	}
	
	protected Vector(double x, double y) {
		xValue = (int) x;
		yValue = (int) y;
		dimensions = 2;
	}
	
	protected Vector(double x, double y, int scaling) {
		xValue = (int) (x / scaling);
		yValue = (int) (y / scaling);
		dimensions = 2;
	}
	
	protected Vector(int x, int y, int z) {
		xValue = x;
		yValue = y;
		zValue = z;
		dimensions = 3;
	}
	
	public int getNumberOfDimensions() {
		return dimensions;
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
	
	public synchronized int getZ() {
		return zValue;
	}
	
	public synchronized void setZ(int z) {
		yValue = z;
	}
	
	public int compareTo(Vector vector) {
		int better = -1;
		int equal = 0;
		int worse = 1;
		if (zValue < vector.getZ()) {
			return better;
		} else if (zValue > vector.getZ()) {
			return worse;
		} else {
			if (yValue < vector.getY()) {
				return better;
			} else if (yValue > vector.getY()) {
				return worse;
			} else {
				if (xValue < vector.getX()) {
					return better;
				} else if (xValue > vector.getX()) {
					return worse;
				} else {
					return equal;
				}
			}
		}
	}
	
	public boolean equals(Object object) {

		if (!(object instanceof Vector)) {
			return super.equals(object);
		} else {

			Vector vec = (Vector) object;
			return vec != null && dimensions == vec.getNumberOfDimensions() 
					&& xValue == vec.getX() && yValue == vec.getY() 
					&& zValue == vec.getZ();
		}
	}

}
