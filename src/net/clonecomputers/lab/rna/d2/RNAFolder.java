package net.clonecomputers.lab.rna.d2;

import java.io.*;

import net.clonecomputers.lab.rna.*;

public class RNAFolder {
	private final RNASequence sequence;
	
	public static void main(String[] args) throws IOException {
		RNASequence sequence = new RNASequence(new BufferedReader(new InputStreamReader(System.in)).readLine());
		new RNAFolder(sequence).fold();
	}
	
	public RNAFolder(RNASequence sequence) {
		this.sequence = sequence;
	}
	
	public void fold() {
		//TODO: do stuff here
	}
	
}
