package net.clonecomputers.lab.rna;

import java.awt.*;

public enum RNABasePair {
	A("U",new Color(1f,1f,0f),Color.BLACK),
	C("G",new Color(0f,1f,1f),Color.BLACK),
	G("C",new Color(0f,.5f,1f),new Color(1f,1f,.6f)),
	U("A",new Color(1f,.5f,0f),Color.BLACK);
	
	//private final RNABasePair conjugate;
	private final String conjugateName;
	private final Color backgroundColor;
	private final Color foregroundColor;
	
	private RNABasePair(String conjugateName, Color backgroundColor, Color foregroundColor) {
		//conjugate = get(conjugateName);
		this.conjugateName = conjugateName;
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
	}
	
	public boolean bondsWith(RNABasePair pair) {
		return pair.name().equalsIgnoreCase(conjugateName);
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public Color getForegroundColor() {
		return foregroundColor;
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
