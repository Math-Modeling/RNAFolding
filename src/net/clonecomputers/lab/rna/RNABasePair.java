package net.clonecomputers.lab.rna;

public enum RNABasePair {
	A("U"),
	C("G"),
	G("C"),
	U("A");
	
	private final RNABasePair conjugate;
	
	private RNABasePair(String conjugateName) {
		conjugate = RNABasePair.valueOf(conjugateName);
	}
	
	public boolean bondsWith(RNABasePair pair) {
		return pair.equals(conjugate);
	}
}
