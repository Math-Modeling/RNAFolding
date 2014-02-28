package net.clonecomputers.lab.rna.d2;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.*;

import net.clonecomputers.lab.rna.*;
import net.clonecomputers.lab.rna.util.*;

public class RNAShapeDisplayer extends JPanel {
	
	private final BufferedImage bondLayer;
	private final BufferedImage basePairLayer;
	private final BufferedImage textLayer;
	private final RNASequence shape;
	
	private int top = 0;
	private int bottom = 0;
	private int left = 0;
	private int right = 0;
	
	private RNAShapeDisplayer(RNASequence shape, Dimension d) {
		bondLayer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
		basePairLayer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
		textLayer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
		this.shape = shape;
		this.setPreferredSize(d);
		calculateBounds();
		drawShape();
		drawHBonds();
		drawInfoText();
		repaint();
	}
	
	public void updateTextLayer(int score, int nTimes) {
		textLayer.getAlphaRaster().setPixels(0, 0, textLayer.getWidth(), textLayer.getHeight(), 
				new int[textLayer.getWidth() * textLayer.getHeight()]); // blank
		Graphics2D g2 = (Graphics2D)textLayer.getGraphics();
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Helvetica",Font.PLAIN,18));
		String text = String.format("%dx%d",score,nTimes);
		g2.drawString(text, 0, g2.getFontMetrics().getLineMetrics(text, g2).getAscent());
	}
	
	private void drawInfoText() {
		Graphics2D g2 = (Graphics2D)textLayer.getGraphics();
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Helvetica",Font.PLAIN,18));
		String numHBonds = Integer.toString(shape.getHBonds().size());
		g2.drawString(numHBonds, 0, g2.getFontMetrics().getLineMetrics(numHBonds, g2).getAscent());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(.9f,.9f,.9f));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(bondLayer, 0, 0, this);
		g.drawImage(basePairLayer, 0, 0, this);
		g.drawImage(textLayer, 0, 0, this);
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
		return (int)(((x-left+.5)/max(right-left+1,top-bottom+1)) * bondLayer.getWidth());
	}

	private int ygp(int y) {
		return (int)(((y-bottom+.5)/max(right-left+1,top-bottom+1)) * -bondLayer.getHeight()) + bondLayer.getHeight();
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
		Set<Pair> hBonds = shape.getHBonds();
		for(Pair p: hBonds) {
			drawHBond(shape.getPoint(p.a),shape.getPoint(p.b));
		}
	}
	
	private void drawHBond(Point lastPoint, Point thisPoint) {
		Graphics2D g2 = (Graphics2D) bondLayer.getGraphics();
		g2.setColor(new Color(.65f,.4f,1f));
		g2.setStroke(new BasicStroke(5f));
		//g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{5.0f, 5.0f}, 1));
		g2.drawLine(xgp(lastPoint.x), ygp(lastPoint.y), xgp(thisPoint.x), ygp(thisPoint.y));
	}

	private void drawPBond(Point lastPoint, Point thisPoint) {
		Graphics2D g2 = (Graphics2D)bondLayer.getGraphics();
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2f));
		g2.drawLine(xgp(lastPoint.x), ygp(lastPoint.y), xgp(thisPoint.x), ygp(thisPoint.y));
	}

	private void drawPair(RNABasePair pair, Point p) {
		Graphics g = basePairLayer.getGraphics();
		g.setColor(pair.getBackgroundColor());
		g.fillOval(xgp(p.x)-10, ygp(p.y)-10,20, 20);
		g.setColor(pair.getForegroundColor());
		g.setFont(new Font("Sanserif", Font.PLAIN, 13));
		FontMetrics metrics = g.getFontMetrics();
		int width = metrics.charWidth(pair.toString().charAt(0));
		g.drawString(pair.toString(), xgp(p.x)-width/2, ygp(p.y)+metrics.getAscent()/2);
		//System.out.printf("g.drawOval(%d, %d, 10, 10);\n",xgp(p.x)-5,ygp(p.y)-5);
	}

	public static RNAShapeDisplayer displayRNAShape(final JFrame window, final RNASequence shape, final Dimension d) {
		final RNAShapeDisplayer r = new RNAShapeDisplayer(shape, d);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				window.setContentPane(r);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.pack();
				window.setVisible(true);
			}
		});
		return r;
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
		RNAShapeDisplayer.displayRNAShape(new JFrame("RNA"), seq, new Dimension(600, 600));
	}
}
