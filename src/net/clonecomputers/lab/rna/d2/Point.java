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
		for(Direction d: Direction.values()){
			if(this.equals(d.move(p))) return true;
		}
		return false;
	}
}
