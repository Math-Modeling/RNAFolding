package net.clonecomputers.lab.rna.d2;

import java.awt.*;
import java.util.concurrent.*;

import javax.swing.*;

import net.clonecomputers.lab.rna.*;
import net.clonecomputers.lab.rna.d2.folders.*;

public abstract class RNAFolder {
	protected JFrame window;
	protected final RNASequence sequence;
	protected RNASequence bestSequence = null;
	protected int bestScore = -1;
	protected int numTimes = 0;
	private RNAShapeDisplayer curentDisplay;
	private long startTime;
	
	public static void main(String[] args) {
		int len = (int)(Math.random()*30+20);
		StringBuilder sb = new StringBuilder();
		RNABasePair[] pairs = RNABasePair.values();
		for(int i = 0; i < len; i++) {
			sb.append(pairs[(int)(Math.random()*pairs.length)].name());
		}
		final String seq = sb.toString();
		System.out.println("sequence = "+seq);
		ExecutorService es = Executors.newFixedThreadPool(3);
		es.execute(new Runnable() {
			@Override public void run() {
				RecursiveFolder f = new RecursiveFolder(new RNASequence(seq));
				/*synchronized (seq) {
					try {
						System.out.println("waiting");
						seq.wait();
						System.out.println("done");
					} catch (InterruptedException e) {
						System.out.println("interrupted");
					}
				}*/
				f.fold();
			}
		});
		es.execute(new Runnable() {
			@Override public void run() {
				RecursiveRandomFolder f = new RecursiveRandomFolder(new RNASequence(seq));
				/*synchronized (seq) {
					try {
						System.out.println("waiting");
						seq.wait();
						System.out.println("done");
					} catch (InterruptedException e) {
						System.out.println("interrupted");
					}
				}*/
				f.fold();
			}
		});
		es.execute(new Runnable() {
			@Override public void run() {
				RandomFolder f = new RandomFolder(new RNASequence(seq));
				/*synchronized (seq) {
					try {
						System.out.println("waiting");
						//seq.wait();
						System.out.println("done");
					} catch (InterruptedException e) {
						System.out.println("interrupted");
					}
				}*/
				f.fold();
			}
		});
		synchronized(seq) {
			seq.notifyAll();
		}
	}
	
	public RNAFolder(RNASequence sequence) {
		this.sequence = sequence;
		this.window = new JFrame("RNA " + this.getClass().getSimpleName());
	}
	
	public void fold() {
		startTime = System.currentTimeMillis();
		foldSequence();
	}
	
	protected abstract void foldSequence();
	
	protected void testSequence() {
		int score = sequence.numberOfHBonds();
		//System.out.println(score);
		if(score == bestScore) {
			numTimes++;
			curentDisplay.updateTextLayer(score, numTimes);
		}
		if(score > bestScore){
			numTimes = 1;
			System.out.printf("%s found new score %d:\t%s\t(time = %d)\n",
					this.getClass().getSimpleName(),score,sequence.pathString(), System.currentTimeMillis()-startTime);
			bestScore = score;
			bestSequence = new RNASequence(sequence);
			//System.out.println(sequence.pathString());
			curentDisplay = RNAShapeDisplayer.displayRNAShape(window, new RNASequence(sequence), new Dimension(600,600));
		}
	}
	
}
