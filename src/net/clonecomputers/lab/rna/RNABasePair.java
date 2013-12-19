package net.clonecomputers.lab.rna;

import java.awt.*;

public enum RNABasePair {
	
	A("U", new Color(0, 223, 0)),
	C("G", Color.RED),
	G("C", Color.BLUE),
	U("A", Color.MAGENTA);
	
	private final RNABasePair conjugate;
	private final Color color;
	
	private RNABasePair(String conjugateName, Color color) {
		conjugate = get(conjugateName);
		this.color = color;
	}
	
	public boolean bondsWith(RNABasePair pair) {
		return pair.equals(conjugate);
	}
	
	public Color getColor() {
		return color;
	}
	
	public String toString() {
		return name();
	}
	
	public static RNABasePair get(String s) {
		if(s.toUpperCase().charAt(0) == 'A') return A;
		if(s.toUpperCase().charAt(0) == 'C') return C;
		if(s.toUpperCase().charAt(0) == 'G') return G;
		if(s.toUpperCase().charAt(0) == 'U') return U;
		else return null;
	}
}
