package net.clonecomputers.lab.rna;

import java.awt.Point;
import java.util.*;

import net.clonecomputers.lab.rna.d2.*;
import net.clonecomputers.lab.rna.util.*;

public class RNASequence implements Iterable<Point> {
	private RNABasePair[] pairs;
	private Turn[] path;
	
	public RNASequence(String sequence) {
		pairs = new RNABasePair[sequence.length()];
		path = new Turn[pairs.length];
		int i = 0;
		for(char c: sequence.toUpperCase().toCharArray()){
			//System.out.print(c);
			pairs[i++] = RNABasePair.get(String.valueOf(c));
		}
		//System.out.println();
	}
	
	public RNASequence(RNASequence s) {
		pairs = Arrays.copyOf(s.pairs, s.pairs.length);
		path = Arrays.copyOf(s.path, s.path.length);
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < pairs.length; i++) {
			s.append(pairs[i].toString());
			s.append(path[i].toString());
		}
		
		return s.toString();
	}
	
	public String pathString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < pairs.length; i++) {
			s.append(path[i].toString());
		}
		
		return s.toString();
	}
	
	public String pairString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < pairs.length; i++) {
			s.append(pairs[i].toString());
		}
		
		return s.toString();
	}
	
	public int getLength() {
		return pairs.length;
	}
	
	public RNABasePair getPair(int i) {
		return pairs[i];
	}
	
	public Turn getTurn(int i) {
		return path[i];
	}
	
	public Turn[] getValidTurns(int position) {
		Set<Turn> turns = new HashSet<Turn>(Arrays.asList(Turn.values()));
		Set<Turn> badTurns = new HashSet<Turn>();
		Point p = getPoint(position);
		Direction initialDir = getDir(position);
		int i = 0;
		for(Point testPoint: this) {
			if(i++ > position) break;
			for(Turn t: turns){
				if(testPoint.equals(t.getDirection(initialDir).move(p))) badTurns.add(t);
			}
			turns.removeAll(badTurns);
		}
		return turns.toArray(new Turn[0]);
	}
	
	public Point getPoint(int position) {
		Point p = new Point(0,0);
		Direction dir = Direction.EAST;
		for(int i = 0; i < position; i++) {
			dir = path[i].getDirection(dir);
			p = dir.move(p);
		}
		return p;
	}
	
	public Direction getDir(int position) {
		Direction dir = Direction.EAST;
		for(int i = 0; i < position; i++) {
			dir = path[i].getDirection(dir);
		}
		return dir;
	}
	
	public Iterator<Point> iterator(){
		return new Iter();
	}
	
	public Iterable<Point> iterable(Iterator<Point> i) {
		if(!(i instanceof Iter)) return null;
		return new Iter((Iter)i);
	}
	
	private class Iter implements Iterator<Point>, Iterable<Point> {
		private Point p;
		private Direction dir;
		int i;
		
		public Iter() {
			i = 0;
			p = new Point(0,0);
			dir = Direction.EAST;
		}
		
		public Iter(Iter iter) {
			i = iter.i;
			p = iter.p;
			dir = iter.dir;
		}
		
		@Override
		public Iterator<Point> iterator() {
			return this;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Point next() {
			Point ret = p;
			if(hasNext()){
				dir = path[i++].getDirection(dir);
				p = dir.move(p);
			}
			return ret;
		}
		
		@Override
		public boolean hasNext() {
			return i < path.length && path[i] != null;
		}
	}
	
	public void setTurn(int i, Turn t) {
		path[i] = t;
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
	
	public Set<Pair> getHBonds() { // positions that are bonded together
		//TODO: find ideal way
		Set<Pair> hBonds = new HashSet<Pair>();
		Set<Integer> usedPoints = new HashSet<Integer>();
		int i = 0;
		for(Iterator<Point> iter = this.iterator(); iter.hasNext();) {
			Point p = iter.next();
			RNABasePair pair = this.getPair(i++);
			int j = i; // intentionally one more
			for(Point p2: iterable(iter)) {
				RNABasePair pair2 = this.getPair(j++);
				//System.out.printf("%d,%d:%s%s\n",i-1,j-1,pair,pair2);
				if(		j - i > 2 &&
						pair.bondsWith(pair2) &&
						!usedPoints.contains(i-1) &&
						!usedPoints.contains(j-1) &&
						p.isAdjacent(p2)){
					hBonds.add(new Pair(i-1,j-1));
					usedPoints.add(i-1);
					usedPoints.add(j-1);
				}
			}
		}
		return hBonds;
	}

	public int numberOfHBonds() {
		return getHBonds().size();
	}
}
