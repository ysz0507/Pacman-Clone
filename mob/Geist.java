package mob;

import java.util.Random;

import GUI.Feld;
import GUI.MyPanel;
import GUI.MyPanel.Bilder;

public abstract class Geist {

	private Name name;
	private MyPanel pan;
	protected int bewegX;
	protected int bewegY;
	private int x;
	private int y;
	private int locX;
	private int locY;
	protected int warteZeit;
	protected static Feld[][] felder;
	protected Thread threadbe;
	protected boolean update;

	static boolean spielLäuft = true;

	public enum Name {
		Roter, Pinker, Orangener, Blauer, PacMan;
	}

	public Geist(int x, int y, Feld[][] felder, Name name) {
		pan = new MyPanel();
		this.x = x;
		this.y = y;
		bewegY = 0;
		bewegX = 0;
		locX = pan.getX();
		locY = pan.getY();
		setName(name);
		rechts();
	}

	public Geist(int x, int y, Feld[][] felder, int wartezeit, Name name) {
		this(x, y, felder, name);
		warteZeit = wartezeit;
	}

	public void bewegungsThreadErstellen() {
		spielLäuft = true;

		threadbe = new Thread() {
			boolean quadrat;
			int richtungdwechselX = getX();
			int richtungdwechselY = getY();

			public void run() {
				if (warteZeit != 0) {
					try {
						Thread.sleep(warteZeit);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				richtungBerechnen();
				while (spielLäuft && (felder[getX()][getY() + 1].getBegehbar()
						|| felder[getX() + 1][getY() + 1].getBegehbar() || felder[getX() + 1][getY()].getBegehbar()
						|| felder[getX() + 1][getY() - 1].getBegehbar() || felder[getX()][getY() - 1].getBegehbar()
						|| felder[getX() - 1][getY() - 1].getBegehbar() || felder[getX() - 1][getY()].getBegehbar()
						|| felder[getX() - 1][getY() + 1].getBegehbar())) {

					if (Math.abs(getPlayer().getLocationX() - getLocationX()) < felder[0][0].getPanel().getWidth()
							&& Math.abs(getPlayer().getLocationY() - getLocationY()) < felder[0][0].getPanel()
									.getHeight()) {
						bewegX = 0;
						bewegY = 0;
					} else if (felder[getX() + 1][getY()]
							.getBegehbar() != felder[richtungdwechselX + 1][richtungdwechselY].getBegehbar()
							|| felder[getX() - 1][getY()]
									.getBegehbar() != felder[richtungdwechselX - 1][richtungdwechselY].getBegehbar()
							|| felder[getX()][getY() + 1]
									.getBegehbar() != felder[richtungdwechselX][richtungdwechselY + 1].getBegehbar()
							|| felder[getX()][getY() - 1]
									.getBegehbar() != felder[richtungdwechselX][richtungdwechselY - 1].getBegehbar())
						richtungBerechnen();

					try {
						if (bewegX != 0) {
							updateAnfordern();
							bewegen(felder[0][0].getPanel().getWidth());
							setX(getX() + bewegX);
						} else if (bewegY != 0) {
							bewegen(felder[0][0].getPanel().getHeight());
							setY(getY() + bewegY);
						} else {
							System.out.println("HAB DSCH");
							spielLäuft = false;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				System.out.println("ende: " + name);

			}

			private void bewegen(int max) throws InterruptedException {
				long start = System.currentTimeMillis();
				for (int i = 0; i < max; setLocation(getLocationX() + bewegX, getLocationY() + bewegY)) {
					if (start + ++i * (400 / max) - System.currentTimeMillis() > 0)
						Thread.sleep(start + i * (400 / max) - System.currentTimeMillis());
					if (Math.abs(getPlayer().getLocationX() - getLocationX()) < felder[0][0].getPanel().getWidth()
							&& Math.abs(getPlayer().getLocationY() - getLocationY()) < felder[0][0].getPanel()
									.getHeight())
						break;
				}
			}

			private void richtungBerechnen() {
				richtungdwechselX = getX();
				richtungdwechselY = getY();
				int bewegXAlt = bewegX;
				int bewegYAlt = bewegY;
				bewegX = 0;
				bewegY = 0;
				quadrat = false;

				if (getX() == 9 && getY() == 9)
					bewegY = -1;
				else if (getX() == 9 && getY() == 10)
					bewegY = -1;
				else if (getX() == 8 && getY() == 10)
					bewegX = 1;
				else if (getX() == 10 && getY() == 10)
					bewegX = -1;
				// rechts oben
				else if (getPlayer().getX() > getX() && getPlayer().getY() < getY()) {
					// x l�nger als y
					if (getX() - getPlayer().getX() == getY() - getPlayer().getY()) {
						quadrat = true;
					}
					if (getX() - getPlayer().getX() > getY() - getPlayer().getY()
							|| (quadrat && new Random().nextInt(2) == 1)) {
						if (felder[getX() + 1][getY()].getBegehbar() && bewegXAlt != -1)
							bewegX = 1;
						else if (felder[getX()][getY() - 1].getBegehbar() && bewegYAlt != 1)
							bewegY = -1;
						else if (felder[getX()][getY() + 1].getBegehbar() && bewegYAlt != -1)
							bewegY = 1;
						else if (felder[getX() - 1][getY()].getBegehbar() && bewegXAlt != 1)
							bewegX = -1;
						// y l�nger als x
					} else if (getX() - getPlayer().getX() < getY() - getPlayer().getY() || quadrat) {
						if (felder[getX()][getY() - 1].getBegehbar() && bewegYAlt != 1)
							bewegY = -1;
						else if (felder[getX() + 1][getY()].getBegehbar() && bewegXAlt != -1)
							bewegX = 1;
						else if (felder[getX() - 1][getY()].getBegehbar() && bewegXAlt != 1)
							bewegX = -1;
						else if (felder[getX()][getY() + 1].getBegehbar() && bewegYAlt != -1)
							bewegY = 1;
					}
					// links oben
				} else if (getPlayer().getX() < getX() && getPlayer().getY() < getY()) {
					// x l�nger als y
					if (getPlayer().getX() - getX() == getPlayer().getY() - getY()) {
						quadrat = true;
					}
					if (getX() - getPlayer().getX() > getY() - getPlayer().getY()
							|| (quadrat && new Random().nextInt(2) == 1)) {
						if (felder[getX() - 1][getY()].getBegehbar() && bewegXAlt != 1)
							bewegX = -1;
						else if (felder[getX()][getY() - 1].getBegehbar() && bewegYAlt != 1)
							bewegY = -1;
						else if (felder[getX()][getY() + 1].getBegehbar() && bewegYAlt != -1)
							bewegY = 1;
						else if (felder[getX() + 1][getY()].getBegehbar() && bewegXAlt != -1)
							bewegX = 1;
						// y l�nger als x
					} else if (getX() - getPlayer().getX() < getY() - getPlayer().getY() || quadrat) {
						if (felder[getX()][getY() - 1].getBegehbar() && bewegYAlt != 1)
							bewegY = -1;
						else if (felder[getX() - 1][getY()].getBegehbar() && bewegXAlt != 1)
							bewegX = -1;
						else if (felder[getX() + 1][getY()].getBegehbar() && bewegXAlt != -1)
							bewegX = 1;
						else if (felder[getX()][getY() + 1].getBegehbar() && bewegYAlt != -1)
							bewegY = 1;
					}
					// rechts unten
				} else if (getPlayer().getX() > getX() && getPlayer().getY() > getY()) {
					// x l�nger als y
					if (getX() - getPlayer().getX() == getY() - getPlayer().getY()) {
						quadrat = true;
					}
					if (getPlayer().getX() - getX() > getPlayer().getY() - getY()
							|| (quadrat && new Random().nextInt(2) == 1)) {
						if (felder[getX() + 1][getY()].getBegehbar() && bewegXAlt != -1)
							bewegX = 1;
						else if (felder[getX()][getY() + 1].getBegehbar() && bewegYAlt != -1)
							bewegY = 1;
						else if (felder[getX()][getY() - 1].getBegehbar() && bewegYAlt != 1)
							bewegY = -1;
						else if (felder[getX() - 1][getY()].getBegehbar() && bewegXAlt != 1)
							bewegX = -1;
						// y l�nger als x
					} else if (getPlayer().getX() - getX() < getPlayer().getY() - getY() || quadrat) {
						if (felder[getX()][getY() + 1].getBegehbar() && bewegYAlt != -1)
							bewegY = 1;
						else if (felder[getX() + 1][getY()].getBegehbar() && bewegXAlt != -1)
							bewegX = 1;
						else if (felder[getX() - 1][getY()].getBegehbar() && bewegXAlt != 1)
							bewegX = -1;
						else if (felder[getX()][getY() - 1].getBegehbar() && bewegYAlt != 1)
							bewegY = -1;
					}
					// links unten
				} else if (getPlayer().getX() < getX() && getPlayer().getY() > getY()) {
					// x l�nger als y
					if (getPlayer().getX() - getX() == getY() - getPlayer().getY()) {
						quadrat = true;
					}
					if (getX() - getPlayer().getX() > getPlayer().getY() - getY()
							|| (quadrat && new Random().nextInt(2) == 1)) {
						if (felder[getX() - 1][getY()].getBegehbar() && bewegXAlt != 1)
							bewegX = -1;
						else if (felder[getX()][getY() + 1].getBegehbar() && bewegYAlt != -1)
							bewegY = 1;
						else if (felder[getX()][getY() - 1].getBegehbar() && bewegYAlt != 1)
							bewegY = -1;
						else if (felder[getX() + 1][getY()].getBegehbar() && bewegXAlt != -1)
							bewegX = 1;
						// y l�nger als x
					} else if (getX() - getPlayer().getX() < getPlayer().getY() - getY() || quadrat) {
						if (felder[getX()][getY() + 1].getBegehbar() && bewegYAlt != -1)
							bewegY = 1;
						else if (felder[getX() - 1][getY()].getBegehbar() && bewegXAlt != 1)
							bewegX = -1;
						else if (felder[getX() + 1][getY()].getBegehbar() && bewegXAlt != -1)
							bewegX = 1;
						else if (felder[getX()][getY() - 1].getBegehbar() && bewegYAlt != 1)
							bewegY = -1;
					}
				} else if (getPlayer().getX() == getX() && getPlayer().getY() != getY()) {
					if (getPlayer().getY() > getY()) {
						if (felder[getX()][getY() + 1].getBegehbar())
							bewegY = 1;
						else if (felder[getX() - 1][getY()].getBegehbar()
								&& (felder[getX() + 1][getY()].getBegehbar() == false || new Random().nextInt(2) == 1))
							bewegX = -1;
						else if (felder[getX() + 1][getY()].getBegehbar())
							bewegX = 1;
						else if (felder[getX()][getY() - 1].getBegehbar())
							bewegY = -1;
					} else if (getPlayer().getY() < getY()) {
						if (felder[getX()][getY() - 1].getBegehbar())
							bewegY = -1;
						else if (felder[getX() - 1][getY()].getBegehbar()
								&& (felder[getX() + 1][getY()].getBegehbar() == false || new Random().nextInt(2) == 1))
							bewegX = -1;
						else if (felder[getX() + 1][getY()].getBegehbar())
							bewegX = 1;
						else if (felder[getX()][getY() + 1].getBegehbar())
							bewegY = 1;
					}
				} else if (getPlayer().getY() == getY() && getPlayer().getX() != getX()) {
					if (getPlayer().getX() > getX()) {
						if (felder[getX() + 1][getY()].getBegehbar())
							bewegX = 1;
						else if (felder[getX()][getY() + 1].getBegehbar()
								&& (felder[getX() - 1][getY()].getBegehbar() == false || new Random().nextInt(2) == 1))
							bewegY = 1;
						else if (felder[getX()][getY() - 1].getBegehbar())
							bewegY = -1;
						else if (felder[getX() - 1][getY()].getBegehbar())
							bewegX = -1;
					} else if (getPlayer().getX() < getX()) {
						if (felder[getX() - 1][getY()].getBegehbar())
							bewegX = -1;
						else if (felder[getX()][getY() + 1].getBegehbar()
								&& (felder[getX() - 1][getY()].getBegehbar() == false || new Random().nextInt(2) == 1))
							bewegY = 1;
						else if (felder[getX()][getY() - 1].getBegehbar())
							bewegY = -1;
						else if (felder[getX() + 1][getY()].getBegehbar())
							bewegX = 1;
					}
				} else if ((getPlayer().getX() == getX() && getPlayer().getY() == getY()) == false) {
					if (felder[getX() + 1][getY()].getBegehbar() == false
							&& felder[getX()][getY() + 1].getBegehbar() == false
							&& felder[getX()][getY() - 1].getBegehbar() == false)
						bewegX = -1;
					else if (felder[getX() - 1][getY()].getBegehbar() == false
							&& felder[getX()][getY() + 1].getBegehbar() == false
							&& felder[getX()][getY() - 1].getBegehbar() == false)
						bewegX = 1;
					else if (felder[getX() + 1][getY()].getBegehbar() == false
							&& felder[getX() - 1][getY()].getBegehbar() == false
							&& felder[getX()][getY() + 1].getBegehbar() == false)
						bewegY = -1;
					else if (felder[getX() + 1][getY()].getBegehbar() == false
							&& felder[getX() - 1][getY()].getBegehbar() == false
							&& felder[getX()][getY() - 1].getBegehbar() == false)
						bewegY = 1;
				}

				if (bewegX == 0 && bewegY == 0) {
					System.out.println("FEHHHLER!");
				}
			}
		};
		threadbe.start();
	}

	public Thread getBewegung() {
		return threadbe;
	}

	public void updateAnfordern() {
		update = true;
	}

	public boolean updateNötig() {
		if (!update)
			return update;
		update = false;
		return true;
	}

	public void grafikÄndern() {
		if (bewegX == 0)
			return;
		if (bewegX > 0)
			rechts();
		else if (bewegX < 0)
			links();
	}

	private void rechts() {
		switch (this.getName()) {
		case Blauer:
			pan.bildÄndern(Bilder.GeistRechtsBlau);
			break;
		case Roter:
			pan.bildÄndern(Bilder.GeistRechtsRot);
			break;
		case Pinker:
			pan.bildÄndern(Bilder.GeistRechtsRosa);
			break;
		case Orangener:
			pan.bildÄndern(Bilder.GeistRechtsOrange);
			break;
		}
	}

	private void links() {
		switch (this.getName()) {
		case Blauer:
			pan.bildÄndern(Bilder.GeistLinksBlau);
			break;
		case Roter:
			pan.bildÄndern(Bilder.GeistLinksRot);
			break;
		case Pinker:
			pan.bildÄndern(Bilder.GeistLinksRosa);
			break;
		case Orangener:
			pan.bildÄndern(Bilder.GeistLinksOrange);
			break;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		if (x <= 0) {
			x = felder.length - 1;
			setLocation(felder[x][getY()].getPanel().getX(), getLocationY());
		} else if (x >= felder.length - 1) {
			x = 0;
			setLocation(felder[x][getY()].getPanel().getX(), getLocationY());
		}
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if (y <= 0) {
			y = felder[0].length - 1;
			setLocation(getLocationX(), felder[getX()][y].getPanel().getY());
		} else if (y >= felder[0].length - 1) {
			y = 0;
			setLocation(getLocationX(), felder[getX()][y].getPanel().getY());
		}
		this.y = y;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public int getLocationX() {
		return locX;
	}

	public int getLocationY() {
		return locY;
	}

	public MyPanel getPanel() {
		return pan;
	}

	public void setWartezeit(int i) {
		warteZeit = i;
	}

	public void setLocation(int x, int y) {
		locX = x;
		locY = y;
	}

	public static void setFelder(Feld[][] fe) {
		Geist.felder = fe;
	}

	public static void spielBeenden() {
		spielLäuft = false;
	}

	public static boolean getSpielLäuft() {
		return spielLäuft;
	}

	abstract protected Player getPlayer();

	abstract protected void reset();

}
