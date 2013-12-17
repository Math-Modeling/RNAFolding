package net.clonecomputers.lab.rna;

import java.util.*;

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
	
	public RNASequence(RNASequence s) {
		pairs = Arrays.copyOf(s.pairs, s.pairs.length);
		path = Arrays.copyOf(s.path, s.path.length);
	}
	
	public Iterable<RNABasePair> getPairs() {
		return new Iterable<RNABasePair>() {
			
			@Override
			public Iterator<RNABasePair> iterator() {
				return new Iterator<RNABasePair>() {
					int i = 0;
					
					@Override
					public boolean hasNext() {
						return i < RNASequence.this.pairs.length;
					}

					@Override
					public RNABasePair next() {
						return RNASequence.this.pairs[i++];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
					
				};
			}
		};
	}
	
	public Iterable<Turn> getPath() {
		return new Iterable<Turn>() {
			
			@Override
			public Iterator<Turn> iterator() {
				return new Iterator<Turn>() {
					int i = 0;
					
					@Override
					public boolean hasNext() {
						return i < RNASequence.this.path.length;
					}

					@Override
					public Turn next() {
						return RNASequence.this.path[i++];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
					
				};
			}
		};
	}
}
