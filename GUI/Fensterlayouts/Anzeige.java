package GUI.Fensterlayouts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GUI.MyPanel;
import GUI.MyPanel.Bilder;

public class Anzeige extends JPanel {
	Infos in;
	javax.swing.JLabel level;

	public Anzeige(int weite) {
		this.setPreferredSize(new Dimension(weite, 0));
		this.setBackground(Color.BLACK);
		this.setLayout(new java.awt.BorderLayout());

		level = new javax.swing.JLabel("Level [x]");
		level.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 39));
		level.setForeground(Color.GRAY);
		level.setHorizontalAlignment(SwingConstants.CENTER);
		level.setPreferredSize(new Dimension(weite, 100));
		this.add(level, java.awt.BorderLayout.NORTH);
		in = new Infos();
		this.add(in, java.awt.BorderLayout.CENTER);
	}

	public static class Infos extends JPanel {
		private static JPanel leben;
		private static JPanel kirschen;
		private static JLabel rekord, score;
		private static JLabel zeit;

		public Infos() {
			setBackground(Color.BLACK);
			this.setLayout(new java.awt.GridLayout(3, 2));

			leben = new JPanel();
			leben.setBackground(Color.BLACK);
			lebenZurücksetzen();
			lebenHinzufügen();
			kirschen = new JPanel();
			kirschen.setBackground(Color.BLACK);
			kirscheHinzufügen();
			kirscheHinzufügen();

			setScore(2564);
			setRekord(99999);
			setZeit(606.6F);

			add(leben);
			add(kirschen);
			add(panMitTextErzeugen(Color.BLACK, "Rekord: ", "Score: ", "Zeit: "));
			add(panMitTextErzeugen(rekord, score, zeit));

			JPanel jp = new JPanel();
			jp.setLayout(new BorderLayout());
			jp.setBackground(Color.BLACK);

			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			fl.setHgap(0);
			fl.setVgap(10);
			JPanel flow = new JPanel(fl);
			flow.setBackground(Color.BLACK);

			MyPanel mp = new MyPanel();
			mp.bildÄndern(Bilder.Daume);
			mp.setPreferredSize(new Dimension(40, 40));

			flow.add(mp);
			jp.add(flow, BorderLayout.SOUTH);
			add(jp);

			this.setBackground(Color.BLACK);
		}

		private JPanel panMitTextErzeugen(Color bg, String... s) {
			JPanel jp = panMitTextErzeugen(s);
			jp.setBackground(bg);
			return (jp);
		}

		private JPanel panMitTextErzeugen(String... s) {
			JPanel jp = new JPanel();
			jp.setLayout(new GridLayout(s.length, 1));
			for (int i = 0; i < s.length; i++) {
				JLabel jl = new JLabel();
				jl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
				jl.setForeground(Color.WHITE);
				jl.setText(s[i]);
				jp.add(jl);
			}
			return jp;
		}

		private JPanel panMitTextErzeugen(JLabel... j) {
			JPanel jp = new JPanel();
			jp.setBackground(Color.BLACK);
			jp.setLayout(new GridLayout(j.length, 1));
			for (int i = 0; i < j.length; i++) {
				if (j[i] == null)
					j[i] = new JLabel();
				j[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
				j[i].setForeground(Color.WHITE);
				jp.add(j[i]);
			}
			return jp;
		}

		public static void kirschenZurücksetzen() {
			kirschen.removeAll();
		}

		public static void kirscheHinzufügen() {
			MyPanel j = new MyPanel();
			j.bildÄndern(Bilder.Frucht);
			j.setPreferredSize(new Dimension(30, 30));
			kirschen.add(j);
		}

		public static void lebenZurücksetzen() {
			leben.removeAll();
			for (int i = 0; i < 3; i++) {
				lebenHinzufügen();
			}
		}

		private static void lebenHinzufügen() {
			MyPanel j = new MyPanel();
			j.bildÄndern(Bilder.PacManOffenRechts);
			j.setPreferredSize(new Dimension(30, 30));
			leben.add(j);
		}

		public static void lebenEntfernen(int i) {
			if (leben.getComponents().length > i) {
				for (int i2 = i; i2 < leben.getComponents().length; i2++) {
					leben.remove(leben.getComponents()[0]);
				}
				leben.repaint();
			} else if (leben.getComponents().length < i) {
				for (int i2 = leben.getComponents().length; i2 < i; i2++) {
					lebenHinzufügen();
				}
			} else
				return;
		}

		public static void setRekord(int s) {
			if (rekord == null)
				rekord = new JLabel();
			rekord.setText(new String().valueOf(s));
		}

		public static void setScore(int s) {
			if (score == null)
				score = new JLabel();
			score.setText(new String().valueOf(s));
		}

		public static void setZeit(int s) {
			setZeit((float) s / 10F);
		}

		public static void setZeit(float s) {
			if (zeit == null)
				zeit = new JLabel();
			zeit.setText(new String().valueOf(s));
		}

	}

}
