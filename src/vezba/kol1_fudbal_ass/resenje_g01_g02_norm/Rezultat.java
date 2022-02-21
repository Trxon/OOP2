package vezba.kol1_fudbal_ass.resenje_g01_g02_norm;

/*
 * Potom implementirati klasu Rezultat koja ima dva celobrojna polja u kojima se
 * nalazi broj golova na utakmici za svaki od timova.
 */
class Rezultat {

	public final int timA;
	public final int timB;

	public Rezultat(int timA, int timB) {
		this.timA = timA;
		this.timB = timB;
	}

	@Override
	public String toString() {
		return timA + "-" + timB;
	}
}
