package mob;

import GUI.Feld;
import GUI.MyPanel.Bilder;

public abstract class Player extends Geist {

	private int bewegX2;
	private int bewegY2;
	private Runnable panBewegen;
	public boolean mundOffen;

	public Player(int x, int y, Feld[][] felder) {
		super(x, y, felder, Name.PacMan);
		bewegY2 = 0;
		bewegX2 = 0;
		bewegungsThreadErstellen();
		getPanel().bildÄndern(Bilder.PacManZuOben);
	}

	public Player(int x, int y, Feld[][] felder, int wartezeit) {
		this(x, y, felder);
		if (wartezeit != 0) {
			try {
				Thread.sleep(wartezeit);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void bewegungsThreadErstellen() {

		if (warteZeit != 0) {
			try {
				Thread.sleep(warteZeit);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		threadbe = new Thread();
		panBewegen = new Runnable() {
			public void run() {
				while (spielLäuft && felder[getX() + bewegX][getY() + bewegY].getBegehbar()) {
					if ((bewegX2 != 0 || bewegY2 != 0) && felder[getX() + bewegX2][getY() + bewegY2].getBegehbar()) {
						bewegX = bewegX2;
						bewegY = bewegY2;
						bewegX2 = 0;
						bewegY2 = 0;
					}
					try {
						updateAnfordern();
						if (bewegX != 0) {
							bewegen(felder[0][0].getPanel().getWidth());
							setX(getX() + bewegX);
						} else if (bewegY != 0) {
							bewegen(felder[0][0].getPanel().getHeight());
							setY(getY() + bewegY);
						} else
							System.out.println("FEEEEEEEEEEEEEEEEEEEEEEEEEEEEEHLER");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (spielLäuft && (bewegX2 != 0 || bewegY2 != 0)
						&& felder[getX() + bewegX2][getY() + bewegY2].getBegehbar()) {
					bewegX = bewegX2;
					bewegY = bewegY2;
					bewegX2 = 0;
					bewegY2 = 0;
					run();
					return;
				}
				bewegX = 0;
				bewegY = 0;

				felder[getX()][getY()].getPunkte().kirscheUpdateNötig();
			}

			private void bewegen(int max) throws InterruptedException {
				long start = System.currentTimeMillis();
				for (int i = 0; i < max; setLocation(getLocationX() + bewegX, getLocationY() + bewegY)) {
					if (start + ++i * (250 / max) - System.currentTimeMillis() > 0)
						Thread.sleep(start + i * (250 / max) - System.currentTimeMillis());
				}
			}
		};
	}

	public void seiteBewegen(int bewegX) {
		if (felder[getX() + bewegX][getY()].getBegehbar() && threadbe.isAlive() == false) {
			this.bewegX = bewegX;
			bewegY = 0;
			threadbe = new Thread(panBewegen);
			threadbe.start();
		} else if (threadbe.isAlive() == true) {
			bewegY2 = 0;
			bewegX2 = bewegX;
		}
	}

	public void hochBewegen(int bewegY) {
		if (felder[getX()][getY() + bewegY].getBegehbar() && threadbe.isAlive() == false) {
			this.bewegY = bewegY;
			bewegX = 0;
			threadbe = new Thread(panBewegen);
			threadbe.start();
		} else if (threadbe.isAlive() == true) {
			bewegY2 = bewegY;
			bewegX2 = 0;
		}
	}

	/*
	public void grafikÄndern() {
		if (bewegX == 0 && bewegY == 0)
			return;
		if (bewegX > 0)
			if (mundOffen)
				getPanel().grafikÄndern(Bilder.PacManOffenRechts);
			else
				getPanel().grafikÄndern(Bilder.PacManZuRechts);
		else if (bewegX < 0)
			if (mundOffen)
				getPanel().grafikÄndern(Bilder.PacManOffenLinks);
			else
				getPanel().grafikÄndern(Bilder.PacManZuLinks);
		else if (bewegY > 0)
			if (mundOffen)
				getPanel().grafikÄndern(Bilder.PacManOffenUnten);
			else
				getPanel().grafikÄndern(Bilder.PacManZuUnten);
		else if (bewegY < 0)
			if (mundOffen)
				getPanel().grafikÄndern(Bilder.PacManOffenOben);
			else
				getPanel().grafikÄndern(Bilder.PacManZuOben);
	}
	*/

	public void grafikÄndern() {
		if (bewegX == 0 && bewegY == 0)
			return;
		if (bewegX > 0)
			if (mundOffen)
				getPanel().bildÄndern(Bilder.PacManOffenRechts);
			else
				getPanel().bildÄndern(Bilder.PacManZuRechts);
		else if (bewegX < 0)
			if (mundOffen)
				getPanel().bildÄndern(Bilder.PacManOffenLinks);
			else
				getPanel().bildÄndern(Bilder.PacManZuLinks);
		else if (bewegY > 0)
			if (mundOffen)
				getPanel().bildÄndern(Bilder.PacManOffenUnten);
			else
				getPanel().bildÄndern(Bilder.PacManZuUnten);
		else if (bewegY < 0)
			if (mundOffen)
				getPanel().bildÄndern(Bilder.PacManOffenOben);
			else
				getPanel().bildÄndern(Bilder.PacManZuOben);
	}
	
	
	@Override
	public void setX(int x) {
		super.setX(x);
		itemAbtasten();
	}

	@Override
	public void setY(int y) {
		super.setY(y);
		itemAbtasten();
	}

	private void itemAbtasten() {
		if (felder[getX()][getY()].getPille() == true) {
			felder[getX()][getY()].getPunkte().scoreErhöhen(10);
			felder[getX()][getY()].setPille(false);
			System.out.println("Pille aus");
		} else if (felder[getX()][getY()].getPowerPille() == true) {
			felder[getX()][getY()].setPowerPille(false);
			System.out.println("Power aus");
		} else if (felder[getX()][getY()].getKirsche() == true) {
			felder[getX()][getY()].setKirsche(false);
			felder[getX()][getY()].getPunkte().kischeEingesammelt();
			System.out.println("Kirsche eingesammelt");
		}
	}

	public void warten() {
		try {
			Thread.sleep(warteZeit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
