package net.clonecomputers.lab.rna.upperbound;

import javax.swing.*;

import net.clonecomputers.lab.rna.*;

public class UpperBoundFinder {
	
	public static void main(String[] args) {
		String seq = JOptionPane.showInputDialog("Input an RNA string")
				.toUpperCase().replace('T', 'U'); // allow DNA strings too
		JOptionPane.showMessageDialog(null, new UpperBoundFinder().findUpperBound(seq));
	}
	
	public int findUpperBound(String seq) {
		seq = seq.toUpperCase();
		int n = seq.length();
		int E = 0;
		for(int i = 0; i < n; i++) {
			for(int j = i+3; j < n; j+=2) {
				if(bonds(seq,i,j)){
					E++;
					break;
				}
			}
		}
		return E;
	}
	
	public boolean bonds(String seq, int i, int j) {
		return RNABasePair.get(seq.charAt(i)).bondsWith(RNABasePair.get(seq.charAt(j)));
	}
}
