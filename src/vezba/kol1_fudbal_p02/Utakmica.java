package vezba.kol1_fudbal_p02;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Utakmica {

	public final Grupa grupa;
	public final int redniBroj;
	public final LocalDate datum;
	public final LocalTime vreme;
	public final String stadion;
	public final String zemljaA;
	public final String zemljaB;
	public final Rezultat naKrajuVremena;
	public final Rezultat poslePenala;
	public final Rezultat posleProduzetaka;
	public final Rezultat naPoluvremenu;
	public final List<Strelac> strelciA;
	public final List<Strelac> strelciB;
	public Utakmica(Grupa grupa, int redniBroj, LocalDate datum, LocalTime vreme, String stadion, String zemljaA,
			String zemljaB, Rezultat naKrajuVremena, Rezultat poslePenala, Rezultat posleProduzetaka,
			Rezultat naPoluvremenu, List<Strelac> strelciA, List<Strelac> strelciB) {
		super();
		this.grupa = grupa;
		this.redniBroj = redniBroj;
		this.datum = datum;
		this.vreme = vreme;
		this.stadion = stadion;
		this.zemljaA = zemljaA;
		this.zemljaB = zemljaB;
		this.naKrajuVremena = naKrajuVremena;
		this.poslePenala = poslePenala;
		this.posleProduzetaka = posleProduzetaka;
		this.naPoluvremenu = naPoluvremenu;
		this.strelciA = strelciA;
		this.strelciB = strelciB;
	}

	@Override
	public String toString() {
		return String.format("#%d %s : %s (%s) %s %s%nPoluvreme: %s, Produzeci: %s, Penali: %s, Kraj: %s%n%s : %s%n",
				redniBroj, zemljaA, zemljaB, grupa, datum, vreme,
				naPoluvremenu, posleProduzetaka == null ? "---" : posleProduzetaka, poslePenala == null ? "---" : poslePenala, naKrajuVremena, 
				strelciA, strelciB
		);
	}
}
