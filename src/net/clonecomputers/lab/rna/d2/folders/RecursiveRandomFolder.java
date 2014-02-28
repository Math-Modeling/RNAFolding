package net.clonecomputers.lab.rna.d2.folders;

import java.util.*;

import javax.swing.*;

import net.clonecomputers.lab.rna.*;
import net.clonecomputers.lab.rna.d2.*;

public class RecursiveRandomFolder extends RNAFolder {
	
	public static void main(String[] args) {
		RNASequence sequence = new RNASequence(JOptionPane.showInputDialog("Input an RNA string")
				.toUpperCase().replace('T', 'U')); // allow DNA strings too
		new RecursiveRandomFolder(sequence).fold();
	}
	
	Random r = new Random();

	public RecursiveRandomFolder(RNASequence sequence) {
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
		for(Turn t: shuffle(sequence.getValidTurns(level))) {
			sequence.setTurn(level, t);
			recurse(level+1);
		}
	}

	private <T> T[] shuffle(T[] arr) {
		T tmp;
		if(arr.length == 2) {
			if(r.nextBoolean()){
				tmp = arr[0];
				arr[0] = arr[1];
				arr[1] = tmp;
			}
			return arr;
		}
		if(arr.length == 3) {
			int i = r.nextInt(3);
			tmp = arr[0];
			arr[0] = arr[i];
			arr[i] = tmp;
			if(r.nextBoolean()){
				tmp = arr[1];
				arr[1] = arr[2];
				arr[2] = tmp;
			}
			return arr;
		}
		if(arr.length <= 1) return arr;
		return null;
	}

}
