package GUI.Fensterlayouts;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.MyPanel;
import GUI.MyPanel.Bilder;

public abstract class Startbildschirm extends JPanel {

	private static char taste;
	private MyPanel bild;

	public Startbildschirm(int x, int y, JFrame fen) {
		super(new java.awt.BorderLayout());
		bild = new MyPanel();
		bild.bilderLaden();
		bild.bildÄndern(Bilder.Start1);
		add(bild, java.awt.BorderLayout.CENTER);
		JPanel pan = new JPanel();
		bild.setLayout(null);
		pan.setBounds((x - 450) / 2, (y - 200) / 2, 450, 160);
		pan.setBackground(Color.BLUE);

		bild.add(pan);
		pan.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// taste = 'd';

				mouseExited(arg0);
				if (taste == 'd') {
					System.out.println("debug");
					laden("test.bin");
				} else if (taste == 'b') {
					System.out.println("build");
					build();
				} else {
					System.out.println("laden");
					laden("klassisch.bin");
				}
				fen.repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				bild.bildÄndern(Bilder.Start2);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				bild.bildÄndern(Bilder.Start1);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		fen.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				if (taste != arg0.getKeyChar()) {
					taste = arg0.getKeyChar();
					System.out.println(taste);
				}
				if (taste == KeyEvent.VK_ESCAPE) {
					System.out.println("EEEEEEESSSSSSSSSSSCCCCCCCCCC");
					löschen();
				}
			}
		});
	}

	abstract public void laden(String s);

	abstract public void build();

	abstract public void löschen();
}
