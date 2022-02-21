package domaci_zadaci.z02_ass;

/*
 * Napisati program koji ispisuje raspored casova.
 *
 * 1) Napraviti klasu "Cas" koja predstavlja jedan cas. Klasa od o-
 *    sobina treba da ima bar naziv predmeta, ime predavaca, tip c-
 *    asa, vreme i mesto odrzavanja. Takodje, ova klasa implementi-
 *    ra interfejs Comparable kao i metode equals(), hashCode(), t-
 *    oString().
 *
 * 2) Napraviti nabrojivi tip "Dan" koji predstavlja dane u nedelji.
 *
 * 3) Napraviti klasu Raspored koja sadrzi mapu ciji su kljucevi da-
 *    ni u nedelji a vrednosti liste casova koji se odrzavaju tog d-
 *    ana. Ove liste su sortirane po vremenu pocetka casa, a takodje
 *    implementirati i potrebne metode za pristup i izmenu podataka.
 *
 * 4) Pitati korisnika za koju godinu IT smera zeli raspored. Ident-
 *    ifikatori Google kalendara za odgovarajuce godine su:
 *        1 godina: g3khre7jrsih1idp5b5ahgm1f8
 *        2 godina: hu93vkklcv692mikqvm17scnv4
 *        3 godina: 6ovsf0fqb19q10s271b9e59ttc
 *        4 godina: a730slcmbr9c6dii94j9pbdir4
 *
 * 5) Ucitati odgovarajuci raspored casova, izvuci neophodne podatke
 *    u instance klase "Cas" i smestiti ih u jednu instancu klase R-
 *    aspored.
 *
 * 6) Pitati korisnika za koji dan u nedelji zeli raspored i ispisa-
 *    ti casove koji se odrzavaju tog dana.
 *
 * Metod za ucitavanje rasporeda sa interneta je vec dat, kao i kako
 * treba da izgleda URL za Google kalendar sa datim identifikatorom. 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RasporedProgram2 {

	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Za koju godinu zelite raspored?");
		int godina = Integer.parseInt(in.readLine());
		String id = Id.getId(godina);

		if (id == null) {
			System.out.println("Nema kalendara za datu godinu.");
			return;
		}

		URL url = new URL("https://calendar.google.com/calendar/ical/" + id + "%40group.calendar.google.com/public/basic.ics");
		String text = readURL(url);

		Raspored raspored = readRaspored(text);

		System.out.println("Za koji dan zelite raspored?");
		String unos = in.readLine();
		Dan dan = Dan.valueOf(unos.toUpperCase());

		List<Cas> casovi = raspored.getList(dan);
		for (Cas cas : casovi) {
			System.out.println(cas);
		}

	}

	private static String readURL(URL url) throws IOException {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
			StringBuilder text = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
			return text.toString();
		}
	}

	public static Raspored readRaspored(String text) {

		// Spajamo u jednu liniju sve osobine koje se nalaze u vise linija
		text = text.replaceAll("\n\\s", "");

		// Jednostavni regularni izrazi za pojedine linije od interesa
		String regexDtstart = "DTSTART(?:;.+?)*:(?<godinapoc>\\d{4})\\d{4}T(?<vremepoc>\\d{4})\\d{2}\n";
		String regexDtend = "DTEND(?:;.+?)*:(?<godinakraj>\\d{4})\\d{4}T(?<vremekraj>\\d{4})\\d{2}\n";
		String regexRrule = "RRULE(?:;.+?)*:.*?BYDAY=(?<dan>[A-Z]{2}).*?\n";
		String regexSummary = "SUMMARY(?:;.+?)*:\\s*(?<predmet>.*?)\\s*(?:\\\\,\\s*(?<nastavnik>.*?)\\s*(?:\\\\,\\s*\\((?<tip>.*?)\\)\\s*(:?\\\\,\\s*(?<sala>.*?)\\s*?)?)?)?\n";
		String regexIgnore = ".*?\n";
		String regexBeginEvent = "BEGIN:VEVENT\n";
		String regexEndEvent = "END:VEVENT\n";

		// Pravimo veliki i komplikovan regularni izraz pomocu vise manjih i jednostavnijih
		// Umesto ovoga, mogli smo da idemo for petljom po svim linijama i
		// da svaku liniju zasebno obradjujemo jednostavnijim regularnim izrazima
		String regexEvent = regexBeginEvent + "(" + regexDtstart + "|" + regexDtend + "|" + regexRrule + "|" + regexSummary + "|" + regexIgnore + ")*?" + regexEndEvent;
		Pattern eventPattern = Pattern.compile(regexEvent);
		Matcher eventMatcher = eventPattern.matcher(text);

		// Ucitavamo raspored pomocu regularnog izraza
		Raspored raspored = new Raspored();
		while (eventMatcher.find()) {

			// Razmatramo samo casove iz tekuceg semestra
			if (!"2020".equals(eventMatcher.group("godinapoc"))) {
				continue;
			}

			// Pravimo cas
			Cas cas = new Cas();
			cas.setDan(eventMatcher.group("dan"));
			cas.setVremePoc(eventMatcher.group("vremepoc"));
			cas.setVremeKraj(eventMatcher.group("vremekraj"));
			cas.setPredmet(eventMatcher.group("predmet"));
			cas.setNastavnik(eventMatcher.group("nastavnik"));
			cas.setTip(eventMatcher.group("tip"));
			cas.setSala(eventMatcher.group("sala"));

			// Dodajemo samo casove koji sadrze sve podatke
			if (cas.isComplete()) {
				raspored.add(cas);
			} else {
				System.err.println("Nepotpun dogadjaj: " + cas);
			}

		}

		// Vracamo ucitane casove
		return raspored;
	}
}
