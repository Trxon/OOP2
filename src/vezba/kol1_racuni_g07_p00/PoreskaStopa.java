package vezba.kol1_racuni_g07_p00;

public enum PoreskaStopa {

	OPSTA(20, '\u00d0'), POSEBNA(10, 'E'), OSLOBODJEN(0, 'G');

	private final int stopa;
	private final char oznaka;

	private PoreskaStopa(int stopa, char oznaka) {
		this.stopa = stopa;
		this.oznaka = oznaka;
	}

	public int getStopa() {
		return stopa;
	}

	public char getOznaka() {
		return oznaka;
	}
}
