package vezba.kol1_fudbal_p02;

public class Strelac {

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
		return ime + " " + minut + "'" + (izPenala ? " (P)" : "") + (autoGol ? " (A)" : "");
	}
}
