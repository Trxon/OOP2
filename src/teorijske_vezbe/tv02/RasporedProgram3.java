package teorijske_vezbe.tv02;
//package teorijske_vezbe.tv03;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import oop2.r01.raspored.RasporedProgram1;
//import oop2.r02.raspored.Cas;
//import oop2.r02.raspored.Dan;
//import oop2.r02.raspored.Raspored;
//
//public class RasporedProgram3 {
//
//	public static void main(String[] args) throws IOException {
//		String text = readFile();
//		Raspored raspored = readRaspored(text);
//		List<Cas> casovi = raspored.getList();
//		uradiNesto(
//				casovi,
//				Cas::isComplete,
//				(x, y) -> x.getPredmet().compareTo(y.getPredmet()),
//				System.out::println
//				
//		);
//	}
//
//	public static void printAsXml(Cas cas) {
//		System.out.println("<cas>");
//		System.out.println("  <predmet>" + cas.getPredmet() + "</predmet>");
//		System.out.println("  <nastavnik>" + cas.getNastavnik() + "</nastavnik>");
//		System.out.println("  <sala>" + cas.getSala() + "</sala>");
//		System.out.println("</cas>");
//	}
//	
//	
//	private static void stampajSve(List<Cas> casovi) {
//		for (Cas cas : casovi) {
//			System.out.println(cas);
//		}
//	}
//
//	private static void stampajSredom(List<Cas> casovi) {
//		stampajDanom(casovi, Dan.SREDA);
//	}
//
//	private static void stampajPetkom(List<Cas> casovi) {
//		stampajDanom(casovi, Dan.PETAK);
//	}
//
//	private static void stampajDanom(List<Cas> casovi, Dan dan) {
//		for (Cas cas : casovi) {
//			if (cas.getDan() == dan) {
//				System.out.println(cas);
//			}
//		}
//	}
//
//	private static void stampaj2(List<Cas> casovi, Dan dan) {
//		List<Cas> temp = new ArrayList<>(casovi);
//		Collections.sort(temp, new ComparatorPoPredmetu());
//		for (Cas cas : temp) {
//			if (cas.getDan() == dan) {
//				if (cas.getVremePoc().compareTo(LocalTime.of(12, 0)) >= 0) {
//					System.out.println(cas);
//				}
//			}
//		}
//	}
//
//	public interface Kriterijum {
//		public boolean zadovoljava(Cas cas);
//	}
//
//	public static class ImenovaniKriterijumStoStampaSve implements Kriterijum {
//
//		@Override
//		public boolean zadovoljava(Cas cas) {
//			return true;
//		}
//	}
//
//	public static class ComparatorPoPredmetu implements Comparator<Cas> {
//
//		@Override
//		public int compare(Cas o1, Cas o2) {
//			System.out.println(this.getClass());
//			return o1.getPredmet().compareTo(o2.getPredmet());
//		}
//	}
//
//	private static void stampaj(List<Cas> casovi, Kriterijum k, Comparator<Cas> comparator) {
//		List<Cas> temp = new ArrayList<>(casovi);
//		Collections.sort(temp, comparator);
//		for (Cas cas : temp) {
//			if (k.zadovoljava(cas)) {
//				System.out.println(cas);
//			}
//		}
//	}
//
//	public static interface Akcija {
//		public void uradi(Cas cas);
//	}
//
//	private static void uradiNesto(List<Cas> casovi, Kriterijum k, Comparator<Cas> comparator, Akcija a) {
//		List<Cas> temp = new ArrayList<>(casovi);
//		Collections.sort(temp, comparator);
//		for (Cas cas : temp) {
//			if (k.zadovoljava(cas)) {
//				a.uradi(cas);
//			}
//		}
//	}
//
//	private static String readFile() throws IOException {
//		try (BufferedReader in = new BufferedReader(new InputStreamReader(
//				RasporedProgram1.class.getResourceAsStream("raspored.ics")))) {
//			StringBuilder text = new StringBuilder();
//			String line;
//			while ((line = in.readLine()) != null) {
//				text.append(line);
//				text.append('\n');
//			}
//			return text.toString();
//		}
//	}
//
//	private static Raspored readRaspored(String text) {
//
//		// Spajamo u jednu liniju sve osobine koje se nalaze u vise linija
//		text = text.replaceAll("\n\\s", "");
//
//		// Jednostavni regularni izrazi za pojedine linije od interesa
//		String regexDtstart = "DTSTART(?:;.+?)*:(?<godinapoc>\\d{4})\\d{4}T(?<vremepoc>\\d{4})\\d{2}\n";
//		String regexDtend = "DTEND(?:;.+?)*:(?<godinakraj>\\d{4})\\d{4}T(?<vremekraj>\\d{4})\\d{2}\n";
//		String regexRrule = "RRULE(?:;.+?)*:.*?BYDAY=(?<dan>[A-Z]{2}).*?\n";
//		String regexSummary = "SUMMARY(?:;.+?)*:\\s*(?<predmet>.*?)\\s*(?:\\\\,\\s*(?<nastavnik>.*?)\\s*(?:\\\\,\\s*\\((?<tip>.*?)\\)\\s*(:?\\\\,\\s*(?<sala>.*?)\\s*?)?)?)?\n";
//		String regexIgnore = ".*?\n";
//		String regexBeginEvent = "BEGIN:VEVENT\n";
//		String regexEndEvent = "END:VEVENT\n";
//
//		// Pravimo veliki i komplikovan regularni izraz pomocu vise manjih i jednostavnijih
//		// Umesto ovoga, mogli smo da idemo for petljom po svim linijama i
//		// da svaku liniju zasebno obradjujemo jednostavnijim regularnim izrazima
//		String regexEvent = regexBeginEvent + "(" + regexDtstart + "|" + regexDtend + "|" + regexRrule + "|" + regexSummary + "|" + regexIgnore + ")*?" + regexEndEvent;
//		Pattern eventPattern = Pattern.compile(regexEvent);
//		Matcher eventMatcher = eventPattern.matcher(text);
//
//		// Ucitavamo raspored pomocu regularnog izraza
//		Raspored raspored = new Raspored();
//		while (eventMatcher.find()) {
//
//			// Razmatramo samo casove iz tekuceg semestra
//			if (!"2019".equals(eventMatcher.group("godinapoc"))) {
//				continue;
//			}
//
//			// Pravimo cas
//			Cas cas = new Cas();
//			cas.setDan(eventMatcher.group("dan"));
//			cas.setVremePoc(eventMatcher.group("vremepoc"));
//			cas.setVremeKraj(eventMatcher.group("vremekraj"));
//			cas.setPredmet(eventMatcher.group("predmet"));
//			cas.setNastavnik(eventMatcher.group("nastavnik"));
//			cas.setTip(eventMatcher.group("tip"));
//			cas.setSala(eventMatcher.group("sala"));
//
//			// Dodajemo samo casove koji sadrze sve podatke
//			if (cas.isComplete()) {
//				raspored.add(cas);
//			} else {
//				System.err.println("Nepotpun dogadjaj: " + cas);
//			}
//
//		}
//
//		// Vracamo ucitane casove
//		return raspored;
//
//	}
//}
