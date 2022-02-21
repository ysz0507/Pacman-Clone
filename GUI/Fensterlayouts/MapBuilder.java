package GUI.Fensterlayouts;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;

import GUI.Feld;
import GUI.Feld.Modus;

public class MapBuilder extends JPanel {

	private Feld[][] felder;

	public MapBuilder(int x, int y, Dimension größe) {
		super();
		this.setPreferredSize(größeAnpassen(x, y, größe));
		this.setBackground(Color.BLACK);

		setLayout(new java.awt.GridLayout(y, x));
		felder = new Feld[x][y];

		for (int i = 0; i < y; i++) {
			for (int i2 = 0; i2 < x; i2++) {
				felder[i2][i] = new Feld(i != 0 && i2 != 0 && i != y - 1 && i2 != x - 1, true);
				add(felder[i2][i].getPanel());
				felder[i2][i].grafikUpdate();
			}
		}

	}

	private Dimension größeAnpassen(int x, int y, Dimension größe) {
		if (größe.getHeight() / y != größe.getWidth() / x) {
			größe.setSize(größe.getHeight() / y * x, größe.getHeight());
		}
		return größe;
	}

	public void setModus(Modus m) {

		for (Feld i[] : felder) {
			for (Feld i2 : i) {
				i2.setModus(m);
			}
		}
	}

	public Feld[][] getFelder() {
		Feld fe[][] = new Feld[felder.length][felder[0].length];
		for (int i = 0; i < fe.length; i++) {
			for (int i2 = 0; i2 < fe[0].length; i2++) {
				fe[i][i2] = new Feld(felder[i][i2].getBegehbar(), false);
				fe[i][i2].setPilleErlaubt(felder[i][i2].getPilleErlaubt());
				fe[i][i2].setPille(felder[i][i2].getPille());
			}
		}
		return fe;
	}

	public void mapSpeichern() {
		try {
			setModus(Modus.Play);

			java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
					new java.io.FileOutputStream(new java.io.File("Maps", "test.bin")));
			out.writeObject(felder);
			out.close();
			setModus(Modus.Wand);

			System.out.println("Karte erfolgreich exportiert!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
