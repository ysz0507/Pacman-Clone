package GUI.Fensterlayouts;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public abstract class Menü extends javax.swing.JPanel {

	public Menü() {
		java.awt.GridLayout lay = new java.awt.GridLayout();
		lay.setColumns(1);
		lay.setRows(3);
		setLayout(lay);
		JLabel play = new JLabel("Play");
		JLabel laden = new JLabel("Map laden");
		JLabel build = new JLabel("Map erstellen");

		play.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				play();
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});

		laden.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				laden();
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});

		build.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				build();
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});

		play.setFont(new Font("ITALIC", Font.BOLD, 60));
		laden.setFont(new Font("ITALIC", Font.BOLD, 60));
		build.setFont(new Font("ITALIC", Font.BOLD, 60));

		add(play);
		add(laden);
		add(build);
	}

	abstract public void play();

	abstract public void laden();

	abstract public void build();
}
