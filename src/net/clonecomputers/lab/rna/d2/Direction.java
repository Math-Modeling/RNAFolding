package net.clonecomputers.lab.rna.d2;

public enum Direction {
	NORTH(0,1,"↑"),
	EAST(-1,0,"→"),
	SOUTH(0,-1,"↓"),
	WEST(1,0,"←");
	
	private final GridPoint mod;
	private final String dir;
	
	private Direction(int x, int y, String dir) {
		this.mod = new GridPoint(x,y);
		this.dir = dir;
	}
	
	public int getModX() {
		return mod.x;
	}
	
	public int getModY() {
		return mod.y;
	}
	
	public String toString() {
		return dir;
	}
}
