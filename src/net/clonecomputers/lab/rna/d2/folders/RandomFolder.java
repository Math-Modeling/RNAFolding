package net.clonecomputers.lab.rna.d2.folders;

import java.util.*;

import javax.swing.*;

import net.clonecomputers.lab.rna.*;
import net.clonecomputers.lab.rna.d2.*;

public class RandomFolder extends RNAFolder {
	
	public static void main(String[] args) {
		RNASequence sequence = new RNASequence(JOptionPane.showInputDialog("Input an RNA string")
				.toUpperCase().replace('T', 'U')); // allow DNA strings too
		new RandomFolder(sequence).fold();
	}

	public RandomFolder(RNASequence sequence) {
		super(sequence);
	}

	@Override
	public void foldSequence() {
		Random r = new Random();
		outer:
		while(true) {
			sequence.setTurn(0, Turn.CENTER);
			for(int i = 1; i < sequence.getLength(); i++) {
				Turn[] validTurns = sequence.getValidTurns(i);
				if(validTurns.length == 0) continue outer;
				sequence.setTurn(i, validTurns[r.nextInt(validTurns.length)]);
			}
			testSequence();
		}
	}

}
