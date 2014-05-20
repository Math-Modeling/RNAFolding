package net.clonecomputers.lab.rna.d2.folders;

import java.util.*;

import javax.swing.*;

import net.clonecomputers.lab.rna.*;
import net.clonecomputers.lab.rna.d2.*;

public class RandomFolder extends RNAFolder {
	
	public static void main(String[] args) {
		RNASequence sequence;
		if(args.length == 1 && args[0].equalsIgnoreCase("random")) {
			int len = (int)(Math.random()*30+20);
			StringBuilder sb = new StringBuilder();
			RNABasePair[] pairs = RNABasePair.values();
			for(int i = 0; i < len; i++) {
				sb.append(pairs[(int)(Math.random()*pairs.length)].name());
			}
			final String seq = sb.toString();
			System.out.println("sequence = "+seq);
			sequence = new RNASequence(seq);
		} else if(args.length == 1) {
			sequence = new RNASequence(args[0].toUpperCase().replace('T', 'U')); // allow DNA strings too
		} else {
			sequence = new RNASequence(JOptionPane.showInputDialog("Input an RNA string")
					.toUpperCase().replace('T', 'U')); // allow DNA strings too
		}
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
