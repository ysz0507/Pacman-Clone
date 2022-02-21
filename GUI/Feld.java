package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Random;

import GUI.MyPanel.Bilder;
import mob.Punkte;

public class Feld implements Serializable {

	public static Punkte punkte;
	private MyPanel pan;
	private boolean pilleErlaubt, pille, powerPille, powerPilleErlaubt, kirsche, kirscheErlaubt, begehbar;

	private static boolean mausGedrücktLi = false;
	private static boolean mausGedrücktRe = false;

	private Modus modus;

	public enum Modus {
		Kirsche, Pille, PowerPille, Wand, Play;
	}

	/*
	 * pille auf hintergrund erzeugt einen Block im Hinergrund lösen durch ein
	 * übertragen der Bilder durch eine neue methode update welche die Grafiken an
	 * die werte anpasst
	 */

	public Feld(boolean begehbar, boolean bearbeiten) {
		pan = new MyPanel();
		if (bearbeiten)
			setModus(Modus.Wand);
		else
			setModus(Modus.Play);

		if (begehbar) {
			this.setBegehbar(true);

			if (modus != Modus.Play) {
				this.setPilleErlaubt(true);
				this.setPille(true);
			}
		} else
			this.setBegehbar(false);

	}

	static Random zsf;
	{
		zsf = new Random();
	}

	Feld(boolean bearbeiten) {
		this(zsf.nextInt(4) == 0, bearbeiten);
	}

	public void setModus(Modus m) {
		if (modus != m) {
			modus = m;
			verändernBeiClick(modus != Modus.Play);
		}
	}

	public boolean getBegehbar() {
		return begehbar;
	}

	public void setBegehbar(boolean b) {
		if (b != begehbar && getPille() == false) {
			this.begehbar = b;
			grafikUpdate();
		}
	}

	public void setPille(boolean b) {
		if (getBegehbar() && getPowerPille() == false && getPille() != b
				&& (modus == Modus.Pille || getPilleErlaubt())) {
			pille = b;
			if (getPilleErlaubt() != b && modus == Modus.Pille)
				setPilleErlaubt(b);
			grafikUpdate();
		}
	}

	public void setPowerPille(boolean b) {
		if (getBegehbar() && getPille() == false && getPowerPille() != b
				&& (modus == Modus.PowerPille || getPowerPilleErlaubt())) {
			powerPille = b;
			if (getPowerPilleErlaubt() != b && modus == Modus.PowerPille)
				setPowerPilleErlaubt(b);
			grafikUpdate();
		}
	}

	public void setKirsche(boolean b) {
		if (getBegehbar() && getPille() == false && getKirsche() != b
				&& (modus == Modus.Kirsche || getKirscheErlaubt())) {
			kirsche = b;
			if (getKirscheErlaubt() != b && modus == Modus.Kirsche)
				setKirscheErlaubt(b);
			grafikUpdate();
		}
	}

	public boolean getPowerPille() {
		return powerPille;
	}

	public boolean getKirsche() {
		return kirsche;
	}

	public boolean getPille() {
		return pille;
	}

	public boolean getPowerPilleErlaubt() {
		return powerPilleErlaubt;
	}

	public boolean getPilleErlaubt() {
		return pilleErlaubt;
	}

	public void setPilleErlaubt(boolean setzbar) {
		if (modus == Modus.Pille)
			pilleErlaubt = setzbar;
	}

	public void setPowerPilleErlaubt(boolean setzbar) {
		if (modus == Modus.PowerPille)
			powerPilleErlaubt = setzbar;
	}

	public void setKirscheErlaubt(boolean setzbar) {
		if (modus == Modus.Kirsche)
			kirscheErlaubt = setzbar;
	}

	public boolean getKirscheErlaubt() {
		return kirscheErlaubt;
	}

	public static Punkte getPunkte() {
		if (punkte == null)
			punkte = new Punkte();
		return punkte;
	}

	public void grafikUpdate() {
		if (!getBegehbar())
			pan.bildÄndern(Bilder.Block);
		else if (getPille())
			pan.bildÄndern(Bilder.Pille);
		else if (getPowerPille())
			pan.bildÄndern(Bilder.PowerPille);
		else if (getKirsche())
			pan.bildÄndern(Bilder.Frucht);
		else
			pan.bildÄndern(Bilder.Hintergrund);
	}

	private void verändernBeiClick(boolean b) {
		if (b && pan.getMouseListeners().length == 0) {
			pan.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent arg0) {
				}

				public void mouseEntered(MouseEvent arg0) {
					if (mausGedrücktLi)
						switch (modus) {
						case Kirsche:
							setKirsche(true);
							break;
						case Pille:
							setPille(true);
							break;
						case PowerPille:
							setPowerPille(true);
							break;
						case Wand:
							setBegehbar(false);
							break;
						}
					else if (mausGedrücktRe) {
						setPille(false);
						setPowerPille(false);
						setBegehbar(true);
					} else
						return;

					grafikUpdate();
				}

				public void mouseExited(MouseEvent arg0) {
				}

				public void mousePressed(MouseEvent arg0) {

					if (arg0.getButton() == MouseEvent.BUTTON1 && mausGedrücktLi == false)
						mausGedrücktLi = true;
					else if (arg0.getButton() == MouseEvent.BUTTON3 && mausGedrücktRe == false)
						mausGedrücktRe = true;

					mouseEntered(arg0);
				}

				public void mouseReleased(MouseEvent arg0) {
					mausGedrücktLi = false;
					mausGedrücktRe = false;
				}
			});
		} else if (!b && pan.getMouseListeners().length > 0)
			pan.removeMouseListener(pan.getMouseListeners()[0]);
	}

	public MyPanel getPanel() {
		return pan;
	}

}
