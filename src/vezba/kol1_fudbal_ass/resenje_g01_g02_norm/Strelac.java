package vezba.kol1_fudbal_ass.resenje_g01_g02_norm;

/*
 * Takodje je potrebno implementirati klasu Strelac za predstavljanje podataka o
 * strelcima na utakmici, koja ima sledeca polja: ime strelca, minut u kojem je
 * postignut gol, boolean indikator da li u pitanju gol iz penala, i indikator
 * da li je u pitanju autogol.
 */
class Strelac {

	public final String ime;
	public final int minut;
	public final boolean izPenala;
	public final boolean autoGol;

	public Strelac(String ime, int minut, boolean izPenala, boolean autoGol) {
		this.ime = ime;
		this.minut = minut;
		this.izPenala = izPenala;
		this.autoGol = autoGol;
	}

	@Override
	public String toString() {
		String s = ime + " " + minut + "'";
		if (izPenala) {
			s += " (P)";
		}
		if (autoGol) {
			s += " (A)";
		}
		return s;
	}
}
