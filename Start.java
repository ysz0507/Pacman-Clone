import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import GUI.Fensterlayouts.BuilderMenü;
import GUI.Fensterlayouts.Spielmenü;
import GUI.Fensterlayouts.Startbildschirm;
import mob.Player;

public class Start {

	static Spielmenü sm;
	static BuilderMenü builder;
	static Player hug;

	public static void main(String[] args) {
		JFrame fenster = new JFrame();
		fenster.setResizable(false);
		fenster.setLocation(100, 100);
		fenster.setTitle("PacMan");
		fenster.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		fenster.setSize(960, 540);
		fenster.setContentPane(new Startbildschirm(960, 540, fenster) {

			public void build() {
				builder = new BuilderMenü(fenster.getContentPane().getSize());
				fenster.setContentPane(builder);
				fenster.setVisible(true);
			}

			public void laden(String file) {
				sm = new Spielmenü(fenster.getContentPane().getSize(), file);
				fenster.setContentPane(sm);
				fenster.setVisible(true);

				sm.getSpielfeld().neustart();
			}

			public void löschen() {
				if (sm != null) {
					sm.löschen();
					sm = null;
				}
				if (builder != null) {
					builder.löschen();
					builder = null;
				}
				fenster.setContentPane(this);
			}
		});

		fenster.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {

				if (builder != null) {

					// if (arg0.getKeyCode() == KeyCode.SPACE.getCode())
					if (arg0.getKeyCode() == 32)
						builder.getBuilder().mapSpeichern();
					else if (arg0.getKeyCode() == 39) {
						builder.getSpielfeld().getPlayer().seiteBewegen(1);
					} else if (arg0.getKeyCode() == 37) {
						builder.getSpielfeld().getPlayer().seiteBewegen(-1);
					} else if (arg0.getKeyCode() == 38) {
						builder.getSpielfeld().getPlayer().hochBewegen(-1);
					} else if (arg0.getKeyCode() == 40) {
						builder.getSpielfeld().getPlayer().hochBewegen(1);
					}

				} else {
					if (arg0.getKeyCode() == 39) {
						sm.getSpielfeld().getPlayer().seiteBewegen(1);
					} else if (arg0.getKeyCode() == 37) {
						sm.getSpielfeld().getPlayer().seiteBewegen(-1);
					} else if (arg0.getKeyCode() == 38) {
						sm.getSpielfeld().getPlayer().hochBewegen(-1);
					} else if (arg0.getKeyCode() == 40) {
						sm.getSpielfeld().getPlayer().hochBewegen(1);
					}
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		fenster.setVisible(true);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			sm.addMouseMotionListener(new java.awt.event.MouseMotionListener() {
				public void mouseDragged(MouseEvent arg0) {
				}

				public void mouseMoved(MouseEvent arg0) {
					fenster.setTitle(arg0.getX() + " - " + arg0.getY());
				}
			});
		} catch (java.lang.NullPointerException e) {
			try {
				builder.addMouseMotionListener(new java.awt.event.MouseMotionListener() {
					public void mouseDragged(MouseEvent arg0) {
					}

					public void mouseMoved(MouseEvent arg0) {
						fenster.setTitle(arg0.getX() + " - " + arg0.getY());
					}
				});
			} catch (java.lang.NullPointerException e2) {
				System.out.println("kein LocPointer generiert");
			}
		}

	}

}
