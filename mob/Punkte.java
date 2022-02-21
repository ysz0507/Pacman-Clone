package mob;

public class Punkte {

	private static byte anzErzeugungen = 0;
	private static int score = 0;
	private static int zeit = 0;
	private static int leben = 4;
	private static boolean scoreUpdate, lebenUpdate, kirscheUpdate;

	public Punkte() {
		if (anzErzeugungen > 0)
			try {
				throw new java.lang.Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		anzErzeugungen++;

	}

	public void tick() {
		zeit += 2;
	}

	public void scoreErhöhen(int i) {
		score += i;
		scoreUpdate = true;
	}

	public int getZeit() {
		return zeit;
	}

	public int getScore() {
		return score;
	}

	public void neustart() {
		leben = 4;
		score = 0;
		zeit = 0;
		neueRunde();
	}

	public void neueRunde() {
		leben--;
		lebenUpdate = true;
		scoreUpdate = true;

		System.out.println("leben: " + getLeben());
	}

	public boolean scoreUpdateNötig() {
		if (scoreUpdate) {
			scoreUpdate = false;
			return true;
		} else
			return scoreUpdate;
	}

	public boolean lebenUpdateNötig() {
		if (lebenUpdate) {
			lebenUpdate = false;
			return true;
		} else
			return lebenUpdate;
	}

	public int getLeben() {
		return leben;
	}

	public void kischeEingesammelt() {
		kirscheUpdate = true;
		scoreErhöhen(100);
	}

	public boolean kirscheUpdateNötig() {
		if (kirscheUpdate) {
			kirscheUpdate = false;
			return true;
		} else
			return kirscheUpdate;
	}

}
