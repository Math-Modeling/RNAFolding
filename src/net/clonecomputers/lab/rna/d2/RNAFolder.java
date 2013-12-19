package net.clonecomputers.lab.rna.d2;

import java.awt.*;
import java.io.*;

import net.clonecomputers.lab.rna.*;

public class RNAFolder {
	private final RNASequence sequence;
	private RNASequence bestSequence = null;
	private int bestScore = -1;
	
	public static void main(String[] args) throws IOException {
		RNASequence sequence = new RNASequence(new BufferedReader(new InputStreamReader(System.in)).readLine());
		new RNAFolder(sequence).fold();
	}
	
	public RNAFolder(RNASequence sequence) {
		this.sequence = sequence;
	}
	
	public void fold() {
		recurse(0);
		System.out.println("done");
	}
	
	private void recurse(int level) {
		if(level >= sequence.getLength()){
			testSequence();
			return;
		}
		for(Turn t: sequence.getValidTurns(level)) {
			sequence.setTurn(level, t);
			recurse(level+1);
		}
	}
	
	private void testSequence() {
		int score = sequence.numberOfHBonds();
		//System.out.println(score);
		if(score > bestScore){
			System.out.printf("better sequence found scoring %d: %s\n",score,sequence.pathString());
			bestScore = score;
			bestSequence = new RNASequence(sequence);
			//System.out.println(sequence.pathString());
			RNAShapeDisplayer.displayRNAShape(new RNASequence(sequence), new Dimension(600,600));
		}
	}
	
}
