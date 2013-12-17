package net.clonecomputers.lab.rna.d2;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.clonecomputers.lab.rna.RNASequence;

public class RNAShapeDisplayer extends JPanel {
	
	private RNAShapeDisplayer(RNASequence shape) {
		//TODO do stuff
	}

	public static void displayRNAShape(final RNASequence shape) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame window = new JFrame();
				window.setContentPane(new RNAShapeDisplayer(shape));
				window.pack();
				window.setVisible(true);
			}
		});
	}
	
	
}
