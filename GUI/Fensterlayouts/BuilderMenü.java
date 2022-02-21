package GUI.Fensterlayouts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JPanel;

import GUI.Feld;
import GUI.Feld.Modus;
import GUI.MyPanel;
import GUI.MyPanel.Bilder;

public class BuilderMenü extends JPanel {

	private MapBuilder builder;
	private MyPanel buttons[] = new MyPanel[4];
	private JPanel buttonsRahmen;
	private TestFeld tester;

	public BuilderMenü(Dimension größe) {
		super();
		this.setSize(größe);

		builder = new MapBuilder(19, 22, größe);
		this.setLayout(new java.awt.BorderLayout());

		add(builder, java.awt.BorderLayout.WEST);

		add(buttonHinzufügen(), java.awt.BorderLayout.CENTER);

		tester = new TestFeld(getHeight(), getWidth(), (int) builder.getPreferredSize().getWidth());
		add(tester, java.awt.BorderLayout.EAST);
		getSpielfeld().neustart();
	}

	public MapBuilder getBuilder() {
		return builder;
	}

	private JPanel buttonHinzufügen() {
		buttonsRahmen = new JPanel();
		buttonsRahmen.setLayout(new java.awt.GridLayout(4, 1, 0, 10));

		for (int i = 0; i < 4; i++) {
			buttons[i] = new MyPanel();
			if (i == 0)
				buttons[i].bildÄndern(Bilder.Frucht);
			else if (i == 1)
				buttons[i].bildÄndern(Bilder.Pille);
			else if (i == 2)
				buttons[i].bildÄndern(Bilder.PowerPille);
			else if (i == 3)
				buttons[i].bildÄndern(Bilder.Block);

			buttons[i].addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent arg0) {
					if (arg0.getSource().equals(buttons[0]))
						builder.setModus(Modus.Kirsche);
					else if (arg0.getSource().equals(buttons[1]))
						builder.setModus(Modus.Pille);
					else if (arg0.getSource().equals(buttons[2]))
						builder.setModus(Modus.PowerPille);
					else if (arg0.getSource().equals(buttons[3]))
						builder.setModus(Modus.Wand);
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
			buttonsRahmen.add(buttons[i]);
			buttonsRahmen.setBackground(new Color(48, 37, 37));
		}

		return buttonsRahmen;
	}

	public Spielfeld getSpielfeld() {
		return tester.getSpielfeld();
	}

	public void löschen() {
		tester.löschen();
		tester = null;
	}

	private class TestFeld extends JPanel {
		private MyPanel[] buttons = new MyPanel[4];
		private Spielfeld sf;

		private TestFeld(int höhe, int breite, int kurzeBreite) {
			super(new java.awt.BorderLayout());
			this.setBackground(Color.BLACK);
			this.add(buttonHinzufügen(höhe, breite, kurzeBreite), java.awt.BorderLayout.NORTH);
			spielfeldHinzufügen(höhe, breite, kurzeBreite);
			add(sf, java.awt.BorderLayout.SOUTH);

		}

		private JPanel buttonHinzufügen(float höhe, float breite, float kurzeBreite) {
			buttonsRahmen = new JPanel();
			buttonsRahmen.setBackground(Color.BLACK);
			buttonsRahmen.setLayout(new java.awt.GridLayout(1, 4));

			for (int i = 0; i < 4; i++) {
				buttons[i] = new MyPanel();
				if (i == 0)
					buttons[i].bildÄndern(Bilder.Test);
				else if (i == 1)
					buttons[i].bildÄndern(Bilder.Play);
				else if (i == 2)
					buttons[i].bildÄndern(Bilder.Export);
				else if (i == 3)
					buttons[i].bildÄndern(Bilder.Import);

				buttons[i].addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent arg0) {
						if (arg0.getSource().equals(buttons[0])) {
							sf.setFelder(builder.getFelder());

							arg0.setSource(buttons[1]);
							mouseClicked(arg0);
							return;
						} else if (arg0.getSource().equals(buttons[1])) {
							if (getSpielfeld().getWiederholung()) {
								getSpielfeld().setWiederholung(false);
								getSpielfeld().abbruch();
								buttons[1].bildÄndern(Bilder.Neustart);
							} else {
								getSpielfeld().setWiederholung(true);
								getSpielfeld().neustart();
								buttons[1].bildÄndern(Bilder.Abbruch);
							}

						} else if (arg0.getSource().equals(buttons[2]))
							builder.mapSpeichern();
						else if (arg0.getSource().equals(buttons[3])) {

							try {
								ObjectInputStream in = new ObjectInputStream(
										new FileInputStream(new File("Maps", "test.bin")));
								sf.setFelder((Feld[][]) in.readObject());

								arg0.setSource(buttons[1]);
								mouseClicked(arg0);
								return;
							} catch (IOException | ClassNotFoundException e) {
								e.printStackTrace();
							}

						}
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
				buttonsRahmen.add(buttons[i]);
			}
			buttonsRahmen.setPreferredSize(new Dimension((int) (breite - kurzeBreite - (höhe / 4)),
					(int) (höhe - ((breite - (höhe / 4) - kurzeBreite) / (kurzeBreite / höhe)))));
			return buttonsRahmen;
		}

		private Dimension spielfeldGröße(float höhe, float breite, float kurzeBreite) {
			return new Dimension((int) (breite - kurzeBreite - (höhe / 4)),
					(int) ((breite - (höhe / 4) - kurzeBreite) / (kurzeBreite / höhe)));
		}

		private void spielfeldHinzufügen(float höhe, float breite, float kurzeBreite) {
			System.out.printf(
					"h�he / b1 : %.2f\nbreite / a3 : %.2f\nkurzeBreite / a1 : %.2f\nergebnis: %.3f / %.3f = %.3f\n\n",
					höhe, breite, kurzeBreite, breite - (höhe / 4) - kurzeBreite, kurzeBreite / höhe,
					höhe - (breite - (höhe / 4) - kurzeBreite) / (kurzeBreite / höhe));
			spielfeldHinzufügen(spielfeldGröße(höhe, breite, kurzeBreite));
		}

		private void spielfeldHinzufügen(Dimension größe) {

			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("Maps", "test.bin")));

				sf = new Spielfeld(größe, (Feld[][]) in.readObject(), false) {
					protected void scoreUpdaten(int i) {
					}

					protected void zeitUpdaten(int i) {
					}

					protected void lebenUpdaten(int i) {
					}

					protected void kirscheUpdaten() {
					}
				};
				in.close();
			} catch (IOException | ClassNotFoundException e) {
				sf = new Spielfeld(19, 22, größe, false) {
					protected void scoreUpdaten(int i) {
					}

					protected void zeitUpdaten(int i) {
					}

					protected void lebenUpdaten(int i) {
					}

					protected void kirscheUpdaten() {
					}
				};
			}
		}

		public void löschen() {
			sf.löschen();
			sf = null;
		}

		public Spielfeld getSpielfeld() {
			return sf;
		}
	}

}
