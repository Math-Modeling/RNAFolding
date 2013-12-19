package net.clonecomputers.lab.rna.d2;

public class Point {
	public final int x;
	public final int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return String.format("(%d, %d)",x,y);
	}
	
	public boolean equals(Object o) {
		return (o instanceof Point) && ((Point)o).x == x && ((Point)o).y == y;
	}
	
	public boolean isAdjacent(Point p) {
		return (p.x == x+1 && p.y == y) ||
				(p.x == x-1 && p.y == y) ||
				(p.x == x && p.y == y+1) ||
				(p.x == x && p.y == y-1);
	}
}
