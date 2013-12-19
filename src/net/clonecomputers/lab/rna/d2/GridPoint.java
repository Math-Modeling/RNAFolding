package net.clonecomputers.lab.rna.d2;

import java.awt.Point;


public class GridPoint extends Point {
	
	public GridPoint() {
		super();
	}
	
	public GridPoint(Point p) {
		super(p);
	}
	
	public GridPoint(int x, int y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)",x,y);
	}
	
	public boolean isAdjacent(Point other) {
		return (this.x == other.x && Math.abs(this.y - other.y) == 1) ||
			   (this.y == other.y && Math.abs(this.x - other.x) == 1);
	}
	
	public boolean isAdjacent(Point other, Direction dir) {
		return this.x + dir.getModX() == other.x &&
			   this.y + dir.getModX() == other.y;
	}
	
	public GridPoint copyTranslate(int x, int y) {
		return new GridPoint(this.x + x, this.y + y);
	}
	
	public GridPoint copyMove(Direction d, int distance) {
		return this.copyTranslate(d.getModX()*distance, d.getModY()*distance);
	}
	
	public void move(Direction d, int distance) {
		this.translate(d.getModX()*distance, d.getModY()*distance);
	}
	
}
