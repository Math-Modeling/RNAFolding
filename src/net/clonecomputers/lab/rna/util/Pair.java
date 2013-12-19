package net.clonecomputers.lab.rna.util;

public class Pair {
	public final int a;
	public final int b;
	
	public Pair(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	public String toString() {
		return String.format("(%d, %d)",a,b);
	}
	
	public boolean equals(Object o) {
		return (o instanceof Pair) && 
				((((Pair)o).a == a && ((Pair)o).b == b) || (((Pair)o).a == b && ((Pair)o).b == a));
	}
}
