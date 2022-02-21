package vezba.kol1_racuni_g03_p04;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

final class Racun {

	private final int broj;
	private final LocalDateTime vreme;
	private final int uplaceno;
	private final List<Stavka> stavke;

	public Racun(int broj, LocalDateTime vreme, int uplaceno, List<Stavka> stavke) {
		this.broj = broj;
		this.vreme = vreme;
		this.uplaceno = uplaceno;
		this.stavke = Collections.unmodifiableList(stavke);
	}

	public int getBroj() {
		return broj;
	}

	public LocalDateTime getVreme() {
		return vreme;
	}

	public int getUplaceno() {
		return uplaceno;
	}

	public List<Stavka> getStavke() {
		return stavke;
	}
}

final class Stavka {

	private final String proizvod;
	private final double kolicina;
	private final double cena;
	private final PoreskaStopa stopa;

	public Stavka(String proizvod, double kolicina, double cena, PoreskaStopa stopa) {
		this.proizvod = proizvod;
		this.kolicina = kolicina;
		this.cena = cena;
		this.stopa = stopa;
	}

	public String getProizvod() {
		return proizvod;
	}

	public double getKolicina() {
		return kolicina;
	}

	public double getCena() {
		return cena;
	}

	public PoreskaStopa getStopa() {
		return stopa;
	}
}

final class Racuni {

	public static final Stream<String> stringStream(int n) {
		return racuniStream(n).map(o -> toStringTxt(o));
	}

	public static final Stream<String> xmlStream(int n) {
		return racuniStream(n).map(o -> toStringXml(o));
	}

	public static final Stream<String> jsonStream(int n) {
		return racuniStream(n).map(o -> toStringJson(o));
	}

	public static final Stream<Racun> racuniStream(int n) {
		Stanje stanje = new Stanje(n);
		return Stream.generate(() -> racun(stanje)).limit(n);
	}

	private static final String[] PROIZVODI = new String[] {
			"Mleko 3,2%", "Mleko 2,8%", "Mleko, kesa", "Mleko 1,5l", "Mleko 2l", "Mleko, kozije",
			"Jogurt 1,5kg", "Jogurt 1kg", "Jogurt, casa", "Jogurt, light",
			"Kiselo mleko 500g", "Kiselo mleko", "Ekstra kiselo mleko",
			"Pavlaka 1kg", "Pavlaka 500g", "Pavlaka, casa", "Mileram 400g", "Mileram, casa",
			"Sirni namaz", "Namaz sa paprikom", "Namaz sa renom", "Mali namaz",
			"Kajmak RF", "Zlatiborski kajmak RF",
			"Sir RF", "Koziji sir RF", "Ovciji sir RF", "Mladi sir RF", "Feta RF",
			"Trapist RF", "Edamer RF", "Bjuval RF", "Gauda RF", "Tilsiter RF",
			"Paprika u pavlaci RF", "Ljuta paprika u pavlaci RF"
	};

	private static final int[] CENE = new int[] {
			100, 80, 75, 120, 160, 250,
			120, 90, 40, 150,
			60, 30, 40,
			160, 90, 60, 120, 80,
			120, 150, 150, 100,
			1500, 1100,
			500, 900, 800, 400, 600,
			1000, 1200, 1500, 1100, 1200,
			500, 600
	};

	private static class Stanje {

		private final int f;

		public Stanje(int broj) {
			this.f = 20;
		}

		public final Random random = new Random(0);

		public double[] popusti = new double[PROIZVODI.length];
		{
			Arrays.fill(popusti, 1.0);
		}

		private int brojac = 1;
		public int broj() {
			return brojac++;
		}

		private LocalDateTime vreme = LocalDateTime.of(2018, 1, 1, 8, 0);
		public LocalDateTime vreme() {
			int p = random.nextInt(f) * random.nextInt(f);
			LocalDateTime vreme = this.vreme.plusMinutes(p);
			if (p == 0 || vreme.getHour() > 18) {
				vreme = vreme.plusDays(1).withHour(8).plusMinutes(p);
			}
			if (this.vreme.getDayOfMonth() != vreme.getDayOfMonth()) {
				int indeks = random.nextInt(popusti.length);
				popusti[indeks] *= .9;
				if (vreme.getDayOfMonth() == 1) {
					Arrays.fill(popusti, 1.0);
				}
			}
			this.vreme = vreme;
			return vreme;
		}
	}

	private static final Racun racun(Stanje stanje) {
		int broj = stanje.broj();
		LocalDateTime vreme = stanje.vreme();
		List<Stavka> stavke = new ArrayList<>();
		double ukupno = 0;
		int k = 5 + stanje.random.nextInt(15);
		for (int i = stanje.random.nextInt(k); i < PROIZVODI.length; i += 1 + stanje.random.nextInt(k)) {
			Stavka stavka = stavka(stanje, i);
			stavke.add(stavka);
			ukupno += stavka.getKolicina() * stavka.getCena();
		}
		Collections.shuffle(stavke);
		int uplaceno = (int) Math.ceil(ukupno);
		int z = stanje.random.nextInt(5);
		if (z == 0) {
			uplaceno = (uplaceno + 499) / 500 * 500;
		} else if (z == 1) {
			uplaceno = (uplaceno + 99) / 100 * 100;
		} else if (z == 2) {
			uplaceno = (uplaceno + 49) / 50 * 50;
		} else if (z == 3) {
			uplaceno = (uplaceno + 9) / 10 * 10;
		} else if (z == 4) {
			uplaceno = (uplaceno + 4) / 5 * 5;
		}
		return new Racun(broj, vreme, uplaceno, stavke);
	}

	private static final Stavka stavka(Stanje stanje, int indeks) {
		PoreskaStopa stopa = indeks < 10 ? PoreskaStopa.POSEBNA : PoreskaStopa.OPSTA;
		String proizvod = PROIZVODI[indeks];
		double cena = CENE[indeks] * stanje.popusti[indeks];
		double kolicina;
		if (indeks < 22) {
			cena = cena - 0.01;
			kolicina = stanje.random.nextBoolean() ? 1 : 2 + stanje.random.nextInt(5);
		} else {
			kolicina = (10 + stanje.random.nextInt(240)) / 100.0;
		}
		return new Stavka(proizvod, kolicina, cena, stopa);
	}

	private static final DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	private static final DateTimeFormatter vremeFormat = DateTimeFormatter.ofPattern("HH:mm");

	private static final String toStringXml(Racun racun) {
		StringBuilder b = new StringBuilder();
		b.append("<racun>\n");
		b.append("  <broj>" + racun.getBroj() + "</broj>\n");
		b.append("  <datum>" + racun.getVreme().format(datumFormat) + "</datum>\n");
		b.append("  <vreme>" + racun.getVreme().format(vremeFormat) + "</vreme>\n");
		for (Stavka stavka : racun.getStavke()) {
			b.append("  <stavka>\n");
			b.append("    <proizvod>" + stavka.getProizvod() + "</proizvod>\n");
			if (stavka.getKolicina() == (int) stavka.getKolicina()) {
				b.append("    <kolicina>" + (int) stavka.getKolicina() + "</kolicina>\n");
			} else {
				b.append("    <kolicina>" + stavka.getKolicina() + "</kolicina>\n");
			}
			b.append("    <cena>" + stavka.getCena() + "</cena>\n");
			b.append("    <stopa>" + stavka.getStopa().toString().toLowerCase() + "</stopa>\n");
			b.append("  </stavka>\n");
		}
		b.append("  <uplaceno>" + racun.getUplaceno() + "</uplaceno>\n");
		b.append("</racun>");
		return b.toString();
	}

	private static final String toStringJson(Racun racun) {
		StringBuilder b = new StringBuilder();
		b.append("{\n");
		b.append("  \"broj\": " + racun.getBroj() + ",\n");
		b.append("  \"datum\": \"" + racun.getVreme().format(datumFormat) + "\",\n");
		b.append("  \"vreme\": \"" + racun.getVreme().format(vremeFormat) + "\",\n");
		b.append("  \"stavke\": [\n");
		for (Stavka stavka : racun.getStavke()) {
			b.append("    {\n");
			b.append("      \"proizvod\": \"" + stavka.getProizvod() + "\",\n");
			if (stavka.getKolicina() == (int) stavka.getKolicina()) {
				b.append("      \"kolicina\": " + (int) stavka.getKolicina() + ",\n");
			} else {
				b.append("      \"kolicina\": " + stavka.getKolicina() + ",\n");
			}
			b.append("      \"cena\": " + stavka.getCena() + ",\n");
			b.append("      \"stopa\": \"" + stavka.getStopa().toString().toLowerCase() + "\",\n");
			b.append("    }, \n");
		}
		b.delete(b.length() - 3, b.length());
		b.append("\n");
		b.append("  ],\n");
		b.append("  \"uplaceno\": " + racun.getUplaceno() + "\n");
		b.append("}");
		return b.toString();
	}

	private static final String toStringTxt(Racun racun) {
		StringBuilder b = new StringBuilder();
		b.append(String.format("====================================%n"));
		b.append(String.format("                                    %n"));
		b.append(String.format("         PK Muzinjak d.o.o.         %n"));
		b.append(String.format("        Zlatiborski drum BB         %n"));
		b.append(String.format("           Mali Muzinjak            %n"));
		b.append(String.format("                                    %n"));
		b.append(String.format("PIB:975321950                       %n"));
		b.append(String.format("IBFM:AG477624                       %n"));
		b.append(String.format("------------------------------------%n"));
		double ukupnoOpsta = 0;
		double ukupnoPosebna = 0;
		for (Stavka stavka : racun.getStavke()) {
			b.append(String.format("%-36s%n", stavka.getProizvod()));
			double ukupno = stavka.getKolicina() * stavka.getCena();
			if (stavka.getKolicina() == (int) stavka.getKolicina()) {
				b.append(String.format("%6.0fx %10.2f %15.2f %s%n", stavka.getKolicina(), stavka.getCena(), ukupno, stavka.getStopa().getOznaka()));
			} else {
				b.append(String.format("%6.3fx %10.2f %15.2f %s%n", stavka.getKolicina(), stavka.getCena(), ukupno, stavka.getStopa().getOznaka()));
			}
			if (stavka.getStopa() == PoreskaStopa.OPSTA) {
				ukupnoOpsta += ukupno;
			}
			if (stavka.getStopa() == PoreskaStopa.POSEBNA) {
				ukupnoPosebna += ukupno;
			}
		}
		double porezOpsta = ukupnoOpsta * PoreskaStopa.OPSTA.getStopa() / 100.0;
		double porezPosebna = ukupnoOpsta * PoreskaStopa.POSEBNA.getStopa() / 100.0;
		b.append(String.format("------------------------------------%n"));
		if (ukupnoOpsta > 0.0) {
			b.append(String.format("S%s: %2d.00%%                          %n", PoreskaStopa.OPSTA.getOznaka(), PoreskaStopa.OPSTA.getStopa()));
		}
		if (ukupnoPosebna > 0.0) {
			b.append(String.format("S%s: %2d.00%%                          %n", PoreskaStopa.POSEBNA.getOznaka(), PoreskaStopa.POSEBNA.getStopa()));
		}
		if (ukupnoOpsta > 0.0) {
			b.append(String.format("P%s:             %20.2f%n", PoreskaStopa.OPSTA.getOznaka(), porezOpsta));
		}
		if (ukupnoPosebna > 0.0) {
			b.append(String.format("P%s:             %20.2f%n", PoreskaStopa.POSEBNA.getOznaka(), porezPosebna));
		}
		b.append(String.format("P%s:             %20.2f%n", 'T', porezOpsta + porezPosebna));
		if (ukupnoOpsta > 0.0) {
			b.append(String.format("E%s:             %20.2f%n", PoreskaStopa.OPSTA.getOznaka(), ukupnoOpsta));
		}
		if (ukupnoPosebna > 0.0) {
			b.append(String.format("E%s:             %20.2f%n", PoreskaStopa.POSEBNA.getOznaka(), ukupnoPosebna));
		}
		b.append(String.format("E%s:             %20.2f%n", 'T', ukupnoOpsta + ukupnoPosebna));
		b.append(String.format("------------------------------------%n"));
		b.append(String.format("ZA UPLATU    %23.2f%n", ukupnoOpsta + ukupnoPosebna));
		b.append(String.format("GOTOVINA     %20d.00%n", racun.getUplaceno()));
		b.append(String.format("UPLACENO     %20d.00%n", racun.getUplaceno()));
		b.append(String.format("POVRACAJ     %23.2f%n", racun.getUplaceno() - ukupnoOpsta - ukupnoPosebna));
		b.append(String.format("                       }|{          %n"));
		b.append(String.format("%11s %5s      -+-          %n", racun.getVreme().format(datumFormat), racun.getVreme().format(vremeFormat)));
		b.append(String.format("BI: %06d             }|{          %n", racun.getBroj()));
		b.append(String.format("====================================%n"));
		b.append(String.format("                                    %n"));
		return b.toString();
	}
}
