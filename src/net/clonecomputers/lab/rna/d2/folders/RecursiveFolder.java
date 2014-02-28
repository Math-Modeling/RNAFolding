package net.clonecomputers.lab.rna.d2.folders;


import javax.swing.*;

import net.clonecomputers.lab.rna.*;
import net.clonecomputers.lab.rna.d2.*;

public class RecursiveFolder extends RNAFolder {
	
	public static void main(String[] args) {
		RNASequence sequence = new RNASequence(JOptionPane.showInputDialog("Input an RNA string")
				.toUpperCase().replace('T', 'U')); // allow DNA strings too
		new RecursiveFolder(sequence).fold();
	}

	public RecursiveFolder(RNASequence sequence) {
		super(sequence);
	}

	@Override
	public void foldSequence() {
		sequence.setTurn(0, Turn.CENTER);
		recurse(1);
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

}
