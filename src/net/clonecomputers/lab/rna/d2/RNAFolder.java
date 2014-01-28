package net.clonecomputers.lab.rna.d2;

import java.awt.*;

import javax.swing.*;

import net.clonecomputers.lab.rna.*;
import net.clonecomputers.lab.rna.d2.folders.*;

public abstract class RNAFolder {
	protected final RNASequence sequence;
	protected RNASequence bestSequence = null;
	protected int bestScore = -1;
	
	public static void main(String[] args) {
		RNASequence sequence = new RNASequence(JOptionPane.showInputDialog("Input an RNA string")
				.toUpperCase().replace('T', 'U')); // allow DNA strings too
		new RecursiveFolder(sequence).fold();
	}
	
	public RNAFolder(RNASequence sequence) {
		this.sequence = sequence;
	}
	
	public abstract void fold();
	
	protected void testSequence() {
		int score = sequence.numberOfHBonds();
		//System.out.println(score);
		if(score > bestScore){
			System.out.printf("better sequence found scoring %d: %s\n%s\n",score,sequence.pathString(),sequence);
			bestScore = score;
			bestSequence = new RNASequence(sequence);
			//System.out.println(sequence.pathString());
			RNAShapeDisplayer.displayRNAShape(new RNASequence(sequence), new Dimension(600,600));
		}
	}
	
}
