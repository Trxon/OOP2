package domaci_zadaci.z01_ass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RasporedProgram1 {

	
	public static void main(String[] args) throws IOException {
		
		String[] lines = readFile();
		check(lines);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Unesite naziv predmeta: ");
		String unos = in.readLine();
		print(lines, unos);
	}
	
	
	private static String[] readFile() throws IOException {
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(RasporedProgram1.class.getResourceAsStream("res/raspored.ics")))) {
			
			List<String> lines = new ArrayList<>();
			
			String line;
			while ((line = in.readLine()) != null) lines.add(line);
			
			return lines.toArray(new String[lines.size()]);
		}
	}
	
	
	/*
	 * Vraca ime osobine u datoj liniji, tj. podstring od pocetka do p-
	 * rvog znaka ':' ili ';'
	 */
	private static String getPropertyName(String line) {
		
		int index = line.indexOf(':');
		if (index == -1) return null;
		
		int indexAlt = line.indexOf(';');
		if (indexAlt != -1 && indexAlt < index) index = indexAlt;
		
		return line.substring(0, index);
	}
	
	
	/*
	 * Vraca vrednost osobine u datoj liniji, tj. podstring prvog znaka
	 * ':' do kraja linije.
	 */
	private static String getPropertyValue(String line) {
		
		int index = line.indexOf(':');
		if (index == -1) return null;
		
		return line.substring(index + 1);
	}
	
	
	/*
	 * Proverava ceo fajl...
	 */
	private static void check(String[] lines) {
		

		/*
		 * Proveravamo samo osobine unutar dogadjaja - pomocu ove promenlj-
		 * ive znamo da li smo izmedju 'BEGIN:VEVENT' i 'END:VEVENT'
		 */
		boolean inEvent = false;
		
		for (int i = 0; i < lines.length; i++) {
			
			/*
			 * Podelimo liniju i izvucemo ime i vrednost osobine dok se atribu-
			 * ti ignorisu...
			 */
			String propertyName = getPropertyName(lines[i]);
			String propertyValue = getPropertyValue(lines[i]);

			/*
			 * Postavljamo kontrolnu promenljivu da znamo da li smo trenutno u-
			 * nutar ili izvan dogadjaja...
			 */
			if ("BEGIN".equalsIgnoreCase(propertyName) && "VEVENT".equalsIgnoreCase(propertyValue))
				inEvent = true;
			else if ("END".equalsIgnoreCase(propertyName) && "VEVENT".equalsIgnoreCase(propertyValue))
				inEvent = false;
			
			/*
			 * Ostale osobine ignorisemo ako su van dogadjaja...
			 */
			if (!inEvent) continue;
			
			if ("DTSTART".equalsIgnoreCase(propertyName) && !checkTime(propertyValue))
				reportError("Vreme pocetka nije dobro", i, lines[i]);
			else if ("DTEND".equalsIgnoreCase(propertyName) && !checkTime(propertyValue))
				reportError("Vreme kraja nije dobro", i, lines[i]);
			else if ("RRULE".equalsIgnoreCase(propertyName) && !checkDay(propertyValue))
				reportError("Dan u nedelji nije dobar", i, lines[i]);
			else if ("SUMMARY".equalsIgnoreCase(propertyName) && !checkData(propertyValue))
				reportError("Nepotpuni podaci o predmetu", i, lines[i]);
		}
	}
	
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
	
	
	private static boolean checkTime(String value) {
		
		try {
			if (value.charAt(8) != 'T') 
				return false;
			
			StringBuilder builder = new StringBuilder(value);
			builder.setCharAt(8, ' ');
			format.parse(builder.toString());
			
			return true;
		} catch (IndexOutOfBoundsException e) {
			
			/*
			 * Ako je string kraci pa charAt() i setCharAt() bace izuzetak...
			 */
			return false;
		} catch (ParseException e) {
			
			/*
			 * Ako SimpleDateFormat ne uspe da prepozna string...
			 */
			return false;
		}
	}
	
	
	private static boolean checkDay(String value) {
		
		/*
		 * 'BYDAY' moze biti bilo gde u liniji, ne samo na kraju, pa se mo-
		 * raju proveriti svi delovi...
		 */
		String byDay = null;
		String[] values = value.split(";");
		
		for (String v : values)
			if (v.toUpperCase().startsWith("BYDAY="))
				byDay = v.substring(6);
		
		return Dan.fromEn(byDay) != null;
	}
	
	
	private static boolean checkData(String value) {
		
		/*
		 * Linija mora da sadrzi 4 podatka: naziv predmeta, ime nastavnika,
		 * tip i salu. Izuzetak su dogadjaji kao npr. 'Aprilski rok' buduc-
		 * i da oni ne sadrze zareze...
		 */
		String[] values = value.split("\\\\,", -1);
		return values.length == 4 || values.length == 1;
	}
	
	
	private static void reportError(String message, int lineNumber, String line) {
		
		/*
		 * Ispis greske u nesto lepsem formatu...
		 */ 
		System.err.printf("%s, linija %d: %s%n", message, lineNumber, line);
	}
	
	
	private static void print(String[] lines, String name) {
		
		/*
		 * Podaci koje trazimo...
		 */ 
		String 	casPoc 		= null;
		String 	casKraj 	= null;
		Dan 	casDan 		= null;
		String 	casPredmet 	= null;
		String 	casSala 	= null;
		
		/*
		 * Idemo redom po svim linijama... Kad naidjemo na novi dogadjaj, tj. 'BEG-
		 * IN:VEVENT', brisemo sve podatke (posto se oni odnose na prethodni dogja-
		 * djaj). Kad naidjemo na neku osobinu koja nas zanima, tada izvlacimo pot-
		 * rebne podatke. Kad naidjemo na kraj dogadjaja, tj. 'END:VEVENT', gledamo
		 * da li je to cas predavanja koji trazimo i stampamo ga ako jeste...
		 */
		for (String line : lines) {
			
			String propertyName = getPropertyName(line);
			String propertyValue = getPropertyValue(line);
			
			/*
			 * Brisanje podataka kad se naidje na novi dogadjaj...
			 */
			if ("BEGIN".equalsIgnoreCase(propertyName) && "VEVENT".equalsIgnoreCase(propertyValue)) {
				
				casPoc 		= null;
				casKraj 	= null;
				casDan 		= null;
				casPredmet 	= null;
				casSala 	= null;
			
			/*
			 * Stampanje casa, ali samo ako su podaci potpuni...
			 */
			} else if ("END".equalsIgnoreCase(propertyName) && "VEVENT".equalsIgnoreCase(propertyValue)) {
				
				if (casDan != null && casSala != null)
					System.out.printf("%s %s-%s: %s (%s)%n", casDan, casPoc, casKraj, casPredmet, casSala);
				
			/*
			 * Izvlacimo vreme pocetka samo ako je linija prosla proveru i sve je ok...
			 */
			} else if ("DTSTART".equalsIgnoreCase(propertyName) && checkTime(propertyValue)) {
				
				int n = propertyValue.length();
				
				String sat = propertyValue.substring(n - 6, n - 4);
				String min = propertyValue.substring(n - 4, n - 2);
				
				StringBuilder builder = new StringBuilder(5);
				builder.append(sat);
				builder.append('.');
				builder.append(min);
				
				casPoc = builder.toString();
				
			/*
			 * Izvlacimo vreme pocetka samo ako je linija prosla proveru i sve je ok...
			 */
			} else if ("DTEND".equalsIgnoreCase(propertyName) && checkTime(propertyValue)) {
				
				int n = propertyValue.length();
				
				String sat = propertyValue.substring(n - 6, n - 4);
				String min = propertyValue.substring(n - 4, n - 2);
				
				StringBuilder builder = new StringBuilder(5);
				builder.append(sat);
				builder.append('.');
				builder.append(min);
				
				casKraj = builder.toString();
				
			/*
			 * Izvlacimo dan u nedelji samo ako je linija ok...
			 */
			} else if ("RRULE".equalsIgnoreCase(propertyName) && checkDay(propertyValue)) {
				
				String[] values = propertyValue.split(";");
				
				for (String v : values)
					if (v.startsWith("BYDAY="))
						casDan = Dan.fromEn(v.substring(6));
			} else if ("SUMMARY".equalsIgnoreCase(propertyName) && checkData(propertyValue)) {
				
				String[] values = propertyValue.split("\\\\,", -1);
				
				if (values.length == 4 && values[0].equalsIgnoreCase(name) && values[2].trim().equalsIgnoreCase("(P)")) {
					casPredmet = values[0].trim();
					casSala = values[3].trim();
				}
			}
		}
	}
}
