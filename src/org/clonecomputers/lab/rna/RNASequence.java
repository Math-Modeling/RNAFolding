package org.clonecomputers.lab.rna;

public class RNASequence {
	private RNABasePair[] pairs;
	
	public RNASequence(String sequence) {
		pairs = new RNABasePair[sequence.length()];
		int i = 0;
		for(char c: sequence.toUpperCase().toCharArray()){
			pairs[i++] = RNABasePair.valueOf(String.valueOf(c));
		}
	}
	
}
