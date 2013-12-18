package net.clonecomputers.lab.rna.d2;

public enum Direction {
	NORTH(0,1,"↑"),
	EAST(-1,0,"→"),
	SOUTH(0,-1,"↓"),
	WEST(1,0,"←");
	
	private final Point mod;
	private final String dir;
	
	private Direction(int x, int y, String dir) {
		this.mod = new Point(x,y);
		this.dir = dir;
	}
	
	public Point move(Point p) {
		return new Point(p.x + mod.x, p.y + mod.y);
	}
	
	public String toString() {
		return dir;
	}
}
