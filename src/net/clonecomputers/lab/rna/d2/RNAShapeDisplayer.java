package net.clonecomputers.lab.rna.d2;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

import net.clonecomputers.lab.rna.*;

public class RNAShapeDisplayer extends JPanel {
	private final BufferedImage bondLayer;
	private final BufferedImage basePairLayer;
	private final RNASequence shape;
	
	private int top = 0;
	private int bottom = 0;
	private int left = 0;
	private int right = 0;
	
	private RNAShapeDisplayer(RNASequence shape, Dimension d) {
		bondLayer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
		basePairLayer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
		this.shape = shape;
		this.setPreferredSize(d);
		calculateBounds();
		drawShape();
		drawHBonds();
		repaint();
	}
	
	@Override public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(bondLayer, 0, 0, this);
		g.drawImage(basePairLayer, 0, 0, this);
	}
	
	private void calculateBounds() {
		Point p = new Point(0,0);
		Direction dir = Direction.EAST;
		for(Turn t: shape.getPath()) {
			dir = t.getDirection(dir);
			p = dir.move(p);
			//System.out.println(p);
			right = max(right,p.x);
			left = min(left,p.x);
			top = max(top,p.y);
			bottom = min(bottom,p.y);
		}
		//System.out.println("right = "+right);
		//System.out.println("left = "+left);
		//System.out.println("top = "+top);
		//System.out.println("bottom = "+bottom);
	}
	
	private static int max(int a, int b) {
		return a>b? a: b;
	}
	
	private static int min(int a, int b) {
		return a<b? a: b;
	}
	
	private int xgp(int x) {
		return (int)(((x-left+.5)/(right-left+1)) * bondLayer.getWidth());
	}

	private int ygp(int y) {
		return (int)(((y-bottom+.5)/(top-bottom+1)) * -bondLayer.getHeight()) + bondLayer.getHeight();
	}
	
	private void drawShape() {
		Point thisPoint = new Point(0,0);
		Point lastPoint = null;
		Direction d = Direction.EAST;
		Turn t;
		RNABasePair p;
		for(int i = 0; i < shape.getLength(); i++) {
			t = shape.getTurn(i);
			p = shape.getPair(i);
			d = t.getDirection(d);
			drawPair(p, thisPoint);
			if(lastPoint != null) drawPBond(lastPoint, thisPoint);
			lastPoint = thisPoint;
			thisPoint = d.move(thisPoint);
		}
	}
	
	private void drawHBonds() {
		
	}
	
	private void drawHBond(Point lastPoint, Point thisPoint) {
		Graphics g = bondLayer.getGraphics();
		g.setColor(Color.GREEN);
		g.drawLine(xgp(lastPoint.x), ygp(lastPoint.y), xgp(thisPoint.x), ygp(thisPoint.y));
	}

	private void drawPBond(Point lastPoint, Point thisPoint) {
		Graphics g = bondLayer.getGraphics();
		g.setColor(Color.BLACK);
		g.drawLine(xgp(lastPoint.x), ygp(lastPoint.y), xgp(thisPoint.x), ygp(thisPoint.y));
	}

	private void drawPair(RNABasePair pair, Point p) {
		Graphics g = basePairLayer.getGraphics();
		g.setColor(pair.getColor());
		g.fillOval(xgp(p.x)-5, ygp(p.y)-5, 10, 10);
		//System.out.printf("g.drawOval(%d, %d, 10, 10);\n",xgp(p.x)-5,ygp(p.y)-5);
	}

	public static void displayRNAShape(final RNASequence shape, final Dimension d) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame window = new JFrame();
				window.setContentPane(new RNAShapeDisplayer(shape, d));
				window.pack();
				window.setVisible(true);
			}
		});
	}
	
	public static void main(String[] args) {
		char[] pairs = {'A','C','G','U'};
		Turn[] turns = Turn.values();
		StringBuilder s = new StringBuilder();
		int length = (int)(Math.random()*100);
		for(int i = 0; i < length; i++) {
			s.append(pairs[(int)(Math.random()*pairs.length)]);
		}
		//System.out.println(s.toString());
		RNASequence seq = new RNASequence(s.toString());
		for(int i = 0; i < seq.getLength(); i++) {
			seq.setTurn(i, turns[(int)(Math.random()*turns.length)]);
		}
		System.out.println(seq);
		RNAShapeDisplayer.displayRNAShape(seq, new Dimension(600, 600));
	}
}