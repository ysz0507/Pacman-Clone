package GUI.Fensterlayouts;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JPanel;

import GUI.Feld;
import GUI.MyPanel;
import GUI.MyPanel.Bilder;

public class Spielmenü extends JPanel {
	private MyPanel text;
	private Spielfeld sf;
	private JPanel infos;

	public Spielmenü(Dimension spielgröße, String file) {
		this.setLayout(new java.awt.BorderLayout(0, 0));
		this.setBackground(Color.BLACK);
		this.setSize(spielgröße);

		text = new MyPanel();
		text.bildÄndern(Bilder.Text);
		text.breiteAnpassen((int) spielgröße.getHeight(), Bilder.Text.getSize());
		this.add(text, java.awt.BorderLayout.WEST);

		laden(spielgröße, file);

		this.add(sf, java.awt.BorderLayout.CENTER);

		infos = new Anzeige(
				(int) (this.getWidth() - text.getPreferredSize().getWidth() - sf.getSize().getWidth()) - 20);
		add(infos, java.awt.BorderLayout.EAST);
	}

	private void laden(Dimension spielgröße, String file) {

		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("Maps", file)));
			sf = new Spielfeld(spielgröße, (Feld[][]) in.readObject(), true) {

				protected void scoreUpdaten(int i) {
					setScore(i);
				}

				protected void zeitUpdaten(int i) {
					setZeit(i);
				}

				protected void lebenUpdaten(int i) {
					lebenAbziehen(i);
				}

				protected void kirscheUpdaten() {
					kirscheHinzufügen();
				}
			};
			in.close();

		} catch (IOException |

				ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void löschen() {
		sf.löschen();
		sf = null;
	}

	public Spielfeld getSpielfeld() {
		return sf;
	}

	private void setScore(int i) {
		Anzeige.Infos.setScore(i);
	}

	private void setZeit(int i) {
		Anzeige.Infos.setZeit(i);
	}

	private void lebenAbziehen(int i) {
		Anzeige.Infos.lebenEntfernen(i);
	}

	private void kirscheHinzufügen() {
		Anzeige.Infos.kirscheHinzufügen();
	}
}
