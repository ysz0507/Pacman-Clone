package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyPanel extends JPanel {

	transient private static BufferedImage speicher[] = new BufferedImage[34];
	transient private Image img;
	transient private boolean hintergrund = false;

	public enum Bilder {
		Block, Frucht, Hintergrund, Text, Daume, Pille, PowerPille, Explosion, GeistLinksBlau, GeistLinksOrange,
		GeistLinksRosa, GeistLinksRot, GeistRechtsBlau, GeistRechtsOrange, GeistRechtsRosa, GeistRechtsRot,
		PacManOffenLinks, PacManOffenOben, PacManOffenUnten, PacManOffenRechts, PacManZuLinks, PacManZuOben,
		PacManZuUnten, PacManZuRechts, Start1, Start2, LautsprecherAn, LautsprecherAus, Play, Abbruch, Export, Import,
		Neustart, Test;

		private float size;

		public float getSize() {
			return size;
		}

		public void setSize(float d) {
			size = d;
		}
	}

	public MyPanel() {
		super();
		this.setBackground(Color.BLACK);
	}

	public void bilderLaden() {
		long start = System.currentTimeMillis();
		try {
			for (Bilder b : Bilder.values()) {
				switch (b) {
				case Block:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Map/Block.png"));
					break;

				case Frucht:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Map/Frucht.png"));
					break;
				case Hintergrund:
					break;
				case Text:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/pacLogo.png"));
					break;
				case Daume:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Buttons/Daumen.png"));
					break;
				case Pille:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Map/schwache pille.png"));
					break;
				case PowerPille:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Map/starke pille.png"));
					break;

				case Explosion:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Mob/explosion.gif"));
					break;
				case GeistLinksBlau:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Geist links blau.png"));
					break;
				case GeistLinksOrange:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Geist links orange.png"));
					break;
				case GeistLinksRosa:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Geist links rosa.png"));
					break;
				case GeistLinksRot:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Geist links rot.png"));
					break;
				case GeistRechtsBlau:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Geist rechts blau.png"));
					break;
				case GeistRechtsOrange:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Geist rechts orange.png"));
					break;
				case GeistRechtsRosa:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Geist rechts rosa.png"));
					break;
				case GeistRechtsRot:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Geist rechts rot.png"));
					break;

				case PacManOffenLinks:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Pac offen links.png"));
					break;
				case PacManOffenOben:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Pac offen oben.png"));
					break;
				case PacManOffenUnten:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Mob/Pac offen unten.png"));
					break;

				case PacManOffenRechts:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Mob/Pac offen.png"));
					break;
				case PacManZuLinks:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Mob/Pac zu links.png"));
					break;

				case PacManZuOben:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Mob/pac zu oben.png"));
					break;
				case PacManZuUnten:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Mob/pac zu unten.png"));
					break;
				case PacManZuRechts:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Mob/pac zu.png"));
					break;

				case Start1:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Buttons/Start Bild1.png"));
					break;
				case Start2:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Buttons/Start Bild2.png"));
					break;
				case LautsprecherAn:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Buttons/lautsprecher an.jpeg"));
					break;
				case LautsprecherAus:
					speicher[b.ordinal()] = ImageIO
							.read(ClassLoader.getSystemResource("designs/Buttons/lautsprecher aus.jpeg"));
					break;

				case Play:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Buttons/Play.png"));
					break;
				case Abbruch:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Buttons/Abbruch.png"));
					break;
				case Export:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Buttons/Export.png"));
					break;
				case Import:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Buttons/Import.png"));
					break;
				case Neustart:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Buttons/Neustart.png"));
					break;
				case Test:
					speicher[b.ordinal()] = ImageIO.read(ClassLoader.getSystemResource("designs/Buttons/Testen.png"));
					break;

				}
				if (b != Bilder.Hintergrund) {
					b.setSize((float) speicher[b.ordinal()].getWidth() / (float) speicher[b.ordinal()].getHeight());
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		start = System.currentTimeMillis() - start;
		System.out.println((float) start / 1000 + "s\n");
	}

	// um EVTL den Hintergrund zu l�schen
	public void bildÄndern(Bilder b) {
		bildÄnder(b);
	}

	private void bildÄnder(Bilder b) {
		if (b == Bilder.Hintergrund)
			hintergrund = true;
		else if (hintergrund)
			hintergrund = false;

		img = speicher[b.ordinal()];
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		if (hintergrund)
			super.paint(g);
		else {
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}

	}

	public void breiteAnpassen(int höhe, float f) {
		System.out.printf("x: %.2f  -  y: %d  -  f: %.2f \n", höhe * f, höhe, f);
		this.setPreferredSize(new Dimension((int) (höhe * f), höhe));
	}

}
