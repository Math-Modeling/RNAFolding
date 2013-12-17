package net.clonecomputers.lab.rna;

import net.clonecomputers.lab.rna.d2.*;

public class RNASequence {
	private RNABasePair[] pairs;
	private Turn[] path;
	
	public RNASequence(String sequence) {
		pairs = new RNABasePair[sequence.length()];
		path = new Turn[pairs.length];
		int i = 0;
		for(char c: sequence.toUpperCase().toCharArray()){
			pairs[i++] = RNABasePair.valueOf(String.valueOf(c));
		}
	}
	
}
