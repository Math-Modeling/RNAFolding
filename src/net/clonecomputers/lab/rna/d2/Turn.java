package net.clonecomputers.lab.rna.d2;

public enum Turn {
	LEFT(-1,"↑"),
	RIGHT(1,"↓"),
	CENTER(0,"→");
	
	private final int dirMod;
	private final String dir;
	
	private Turn(int dirMod, String dir) {
		this.dirMod = dirMod;
		this.dir = dir;
	}
	
	public Direction getDirection(Direction startDir) {
		Direction[] dirs = Direction.values();
		return dirs[(startDir.ordinal() + dirMod + dirs.length) % (dirs.length)];
	}
	
	public String toString() {
		return dir;
	}
}
