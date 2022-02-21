package GUI.Fensterlayouts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import GUI.Feld;
import GUI.MyPanel.Bilder;
import mob.Geist;
import mob.Geist.Name;
import mob.Player;

public abstract class Spielfeld extends JLayeredPane {

	private boolean nochmal = true;

	private Thread spiel;
	private Timer echtzeit;
	private Timer render;
	private Feld[][] felder;
	private JPanel pan;
	private Player hug;
	private Geist[] gegner = { new Geist(9, 9, getFelder(), 1000, Name.Roter) {
		protected Player getPlayer() {
			return hug;
		}

		protected void reset() {
			neustart();
		}
	}, new Geist(8, 10, getFelder(), 2000, Name.Blauer) {
		protected Player getPlayer() {
			return hug;
		}

		protected void reset() {
			neustart();
		}
	}, new Geist(9, 10, getFelder(), 3000, Name.Pinker) {
		protected Player getPlayer() {
			return hug;
		}

		protected void reset() {
			neustart();
		}
	}, new Geist(10, 10, getFelder(), 4000, Name.Orangener) {
		protected Player getPlayer() {
			return hug;
		}

		protected void reset() {
			neustart();
		}
	} };

	public Spielfeld(int x, int y, Dimension größe, boolean wiederholung) {
		this(größe, felderErzeugen(x, y), wiederholung);
	}

	private static Feld[][] felderErzeugen(int x, int y) {
		Feld[][] felde = new Feld[x][y];
		for (int i = 0; i < y; i++) {
			for (int i2 = 0; i2 < x; i2++) {
				// felde[i2][i] = new Feld(
				// (i != 0 && i2 != 0 && i != y - 1 && i2 != x - 1) && new Random().nextInt(4)
				// != 0, false);
				felde[i2][i] = new Feld(i != 0 && i2 != 0 && i != y - 1 && i2 != x - 1 && !(i % 3 != 0 && i2 % 4 != 0),
						false);
			}
		}
		return felde;
	}

	public Spielfeld(Dimension größe, Feld[][] felder, boolean wiederholung) {
		super();
		this.nochmal = wiederholung;
		this.felder = felder;
		pan = new JPanel();
		pan.setLayout(new java.awt.GridLayout(felder[0].length, felder.length));

		this.setBackground(Color.GREEN);
		setSize(größe);
		setPreferredSize(größe);

		pan.setSize(größeAnpassen(felder.length, felder[0].length, größe));
		pan.setPreferredSize(größeAnpassen(felder.length, felder[0].length, größe));
		pan.setBackground(Color.BLACK);

		for (int i = 0; i < felder[0].length; i++) {
			for (int i2 = 0; i2 < felder.length; i2++) {
				pan.add(felder[i2][i].getPanel());
			}
		}

		add(pan, new Integer("1"));

		hug = new Player(9, 16, getFelder(), 1000) {
			protected Player getPlayer() {
				return hug;
			}

			protected void reset() {
				neustart();
			}
		};
		hug.setFelder(felder);
		add(hug.getPanel(), new Integer("2"));

		for (Geist g : gegner) {
			g.setFelder(felder);
			add(g.getPanel(), new Integer("2"));
		}

		// mund auf zu im falschen Thread !?!?!?
		echtzeit = new Timer(200, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hug.mundOffen = !hug.mundOffen;
				hug.updateAnfordern();
				Feld.getPunkte().tick();
				zeitUpdaten(Feld.getPunkte().getZeit());
			}
		});

		render = new Timer(1000 / 60, new ActionListener() {
			long zähler = 0;
			byte fps = 0;

			public void actionPerformed(ActionEvent arg0) {
				if (System.currentTimeMillis() - zähler > 1000) {
					zähler = System.currentTimeMillis();
					System.out.println(fps);
					fps = 1;
				} else
					fps++;

				if (hug.getPanel().getLocation().getX() != hug.getLocationX()
						|| hug.getPanel().getLocation().getY() != hug.getLocationY())
					hug.getPanel().setLocation(hug.getLocationX(), hug.getLocationY());
				for (Geist g : gegner) {
					g.getPanel().setLocation(g.getLocationX(), g.getLocationY());
					if (g.updateNötig())
						g.grafikÄndern();
				}
				if (Feld.getPunkte().scoreUpdateNötig())
					scoreUpdaten(Feld.getPunkte().getScore());
				if (Feld.getPunkte().lebenUpdateNötig())
					lebenUpdaten(Feld.getPunkte().getLeben());
				if (Feld.getPunkte().kirscheUpdateNötig())
					kirscheUpdaten();
				if (hug.updateNötig())
					hug.grafikÄndern();
			}
		});
		render.start();
		this.infosUndAnpassen();
	}

	public void startvorbereitung() {

		gegner[0].setX(9);
		gegner[0].setY(9);
		gegner[0].setWartezeit(1000);

		gegner[1].setX(8);
		gegner[1].setY(10);
		gegner[1].setWartezeit(2000);

		gegner[2].setX(9);
		gegner[2].setY(10);
		gegner[2].setWartezeit(3000);

		gegner[3].setX(10);
		gegner[3].setY(10);
		gegner[3].setWartezeit(4000);

		hug.setX(9);
		hug.setY(16);

		infosUndAnpassen();

		if (Feld.getPunkte().getLeben() > 1) {
			Feld.getPunkte().neueRunde();
		} else {
			Feld.getPunkte().neustart();

			for (Feld f[] : felder) {
				for (Feld fe : f) {
					if (fe.getPowerPilleErlaubt())
						fe.setPowerPille(true);
					else if (fe.getKirscheErlaubt())
						fe.setKirsche(true);
					else if (fe.getPilleErlaubt())
						fe.setPille(true);
				}
			}
		}
	}

	public void neustart() {
		spiel = new Thread() {
			public void run() {

				for (int i = 0; i < 50 && nochmal; i++) {

					startvorbereitung();

					/////////////////////////////////////////////
					hug.warten();

					for (Geist g : gegner) {
						g.bewegungsThreadErstellen();
					}
					echtzeit.start();

					for (Geist g : gegner) {
						try {
							g.getBewegung().join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					echtzeit.stop();

				}

			}
		};
		spiel.start();
	}

	public void abbruch() {
		Geist.spielBeenden();
	}

	public void setWiederholung(boolean b) {
		nochmal = b;
	}

	public boolean getWiederholung() {
		return nochmal;
	}

	private Dimension größeAnpassen(int x, int y, Dimension größe) {
		if (größe.getHeight() / y != größe.getWidth() / x) {
			größe.setSize(größe.getHeight() / y * x, größe.getHeight());
		}
		return größe;
	}

	public void setFelder(Feld fe[][]) {
		this.setWiederholung(false);
		Geist.spielBeenden();
		try {
			if (spiel != null)
				spiel.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		felder = fe;
		pan.removeAll();
		for (int i = 0; i < felder[0].length; i++) {
			for (int i2 = 0; i2 < felder.length; i2++) {
				pan.add(felder[i2][i].getPanel());
			}
		}
		Geist.setFelder(felder);
		pan.doLayout();
		pan.repaint();
		infosUndAnpassen();
		pan.repaint();
	}

	public void infosUndAnpassen() {
		getPlayer().getPanel().setBounds(getFelder()[getPlayer().getX()][getPlayer().getY()].getPanel().getX(),
				getFelder()[getPlayer().getX()][getPlayer().getY()].getPanel().getY(),
				getFelder()[getPlayer().getX()][getPlayer().getY()].getPanel().getWidth(),
				getFelder()[getPlayer().getX()][getPlayer().getY()].getPanel().getHeight());

		hug.setLocation((int) felder[hug.getX()][hug.getY()].getPanel().getLocation().getX(),
				(int) felder[hug.getX()][hug.getY()].getPanel().getLocation().getY());

		for (Geist g : gegner) {
			g.setLocation(getFelder()[g.getX()][g.getY()].getPanel().getX(),
					getFelder()[g.getX()][g.getY()].getPanel().getY());
			g.getPanel().setBounds(getFelder()[g.getX()][g.getY()].getPanel().getX(),
					getFelder()[g.getX()][g.getY()].getPanel().getY(),
					getFelder()[g.getX()][g.getY()].getPanel().getWidth(),
					getFelder()[g.getX()][g.getY()].getPanel().getHeight());
		}

		// ??????
		pan.setPreferredSize(größeAnpassen(felder.length, felder[0].length, this.getSize()));

		System.out.printf(
				"Die Größe der einzelnen Felder: %d %d\nDie Größe des Spielfeldes: pan: %d %d normal: %d %d \nDie ausmaße des Spielfeldes: %d %d\n\n",
				felder[0][0].getPanel().getWidth(), felder[0][0].getPanel().getHeight(), pan.getWidth(),
				pan.getHeight(), getWidth(), getHeight(), felder[0].length, felder.length);
		System.out.printf(
				"Koordinaten vom Roten: %d %d\nLocation vom Roten: %d %d\nLocation die dargestellt wird: %d %d\nLocation vom entsprechendem Feld: %d %d\n\n",
				gegner[0].getX(), gegner[0].getY(), gegner[0].getLocationX(), gegner[0].getLocationY(),
				(int) gegner[0].getPanel().getLocation().getX(), (int) gegner[0].getPanel().getLocation().getY(),
				(int) felder[gegner[0].getX()][gegner[0].getY()].getPanel().getLocation().getX(),
				(int) felder[gegner[0].getX()][gegner[0].getY()].getPanel().getLocation().getY());

		for (Feld[] f : felder) {
			for (Feld fe : f) {
				if (fe.getBegehbar() == false)
					fe.getPanel().bildÄndern(Bilder.Block);
				else if (fe.getPille())
					fe.getPanel().bildÄndern(Bilder.Pille);
				else if (fe.getKirsche())
					fe.getPanel().bildÄndern(Bilder.Frucht);
				else if (fe.getPowerPille())
					fe.getPanel().bildÄndern(Bilder.PowerPille);
				else
					fe.getPanel().bildÄndern(Bilder.Hintergrund);
			}
		}

	}

	public void löschen() {
		setWiederholung(false);
		abbruch();
		render.stop();
	}

	public Player getPlayer() {
		return hug;
	}

	public Feld[][] getFelder() {
		return felder;
	}

	public Dimension getSize() {
		return größeAnpassen(felder.length, felder[0].length, pan.getSize());
	}

	protected abstract void scoreUpdaten(int i);

	protected abstract void zeitUpdaten(int i);

	protected abstract void lebenUpdaten(int i);

	protected abstract void kirscheUpdaten();

}
