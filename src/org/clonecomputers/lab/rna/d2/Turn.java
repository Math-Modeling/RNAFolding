package org.clonecomputers.lab.rna.d2;

public enum Turn {
	LEFT(-1),
	RIGHT(1),
	CENTER(0);
	
	private final int dirMod;
	
	private Turn(int dirMod) {
		this.dirMod = dirMod;
	}
	
	public Direction getDirection(Direction startDir) {
		Direction[] dirs = Direction.values();
		return dirs[(startDir.ordinal() + dirMod) % (dirs.length)];
	}
}
