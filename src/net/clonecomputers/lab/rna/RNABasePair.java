package net.clonecomputers.lab.rna;

import java.awt.*;

public enum RNABasePair {
	A("U",new Color(1f,1f,0f)),
	C("G",new Color(1f,.5f,0f)),
	G("C",new Color(0f,.5f,1f)),
	U("A",new Color(0f,1f,1f));
	
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
