package domaci_zadaci.z01_ass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RasporedProgram1Regex {

	
	public static void main(String[] args) throws IOException {
		String lines = readFile();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Unesite naziv predmeta: ");
		String unos = in.readLine();
		print(lines, unos);
	}
	
	
	private static String readFile() throws IOException {
		
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(RasporedProgram1.class.getResourceAsStream("res/raspored.ics")))) {
			
			StringJoiner lines = new StringJoiner("\n");
			
			String line;
			while ((line = in.readLine()) != null) lines.add(line);
			
			return lines.toString();
		}
	}
	
	
	private static void print(String lines, String name) {
		
		Pattern eventPattern = Pattern.compile("(?sm)^BEGIN:VEVENT$(?<sadrzaj>.*?)^END:VEVENT$");
		Pattern dtstartPattern = Pattern.compile("(?sm)^DTSTART(?:;.*?)*:.*?T(?<sat>\\d{2})(?<minut>\\d{2})\\d{2}$");
		Pattern dtendPattern = Pattern.compile("(?sm)^DTEND(?:;.*?)*:.*?T(?<sat>\\d{2})(?<minut>\\d{2})\\d{2}$");
		Pattern rrulePattern = Pattern.compile("(?sm)^RRULE:(?:.*?;)*BYDAY=(?<dan>.*?)$");
		Pattern summaryPattern = Pattern.compile("(?sm)^SUMMARY:\\s*(?<predmet>.*?)\\s*\\\\,\\s*(?<nastavnik>.*?)\\s*\\\\,\\s*\\((?<tip>.*?)\\)\\s*\\\\,\\s*(?<sala>.*?)\\s*$");
		
		Matcher eventMatcher = eventPattern.matcher(lines);
		
		while (eventMatcher.find()) {
			
			String dogadjaj = eventMatcher.group("sadrzaj");
			
			String 	casPoc 			= null;
			String 	casKraj 		= null;
			Dan 	casDan 			= null;
			String 	casPredmet 		= null;
			String 	casNastavnik 	= null;
			String 	casTip 			= null;
			String 	casSala 		= null;
			
			Matcher dtstartMatcher = dtstartPattern.matcher(dogadjaj);
			if (dtstartMatcher.find())
				casPoc = dtstartMatcher.group("sat") + ":" + dtstartMatcher.group("minut");
			
			Matcher dtendMatcher = dtendPattern.matcher(dogadjaj);
			if (dtendMatcher.find())
				casKraj = dtendMatcher.group("sat") + ":" + dtendMatcher.group("minut");
			
			Matcher rruleMatcher = rrulePattern.matcher(dogadjaj);
			if (rruleMatcher.find())
				casDan = Dan.fromEn(rruleMatcher.group("dan"));
			
			Matcher summaryMatcher = summaryPattern.matcher(dogadjaj);
			if (summaryMatcher.find()) {
				casPredmet = summaryMatcher.group("predmet");
				casNastavnik = summaryMatcher.group("nastavnik");
				casTip = summaryMatcher.group("tip");
				casSala = summaryMatcher.group("sala");
			}
			
			if (casPredmet == null || casNastavnik == null || casTip == null || casSala == null)
				reportError("Nepotpuni podaci o predmetu", eventMatcher);
			else if (casPoc == null)
				reportError("Vreme pocetka nije dobro", eventMatcher);
			else if (casKraj == null)
				reportError("Vreme kraja nije dobro", eventMatcher);
			else if (casDan == null)
				reportError("Dan u nedelji nije dobar", eventMatcher);
			
			if ("P".equalsIgnoreCase(casTip) && name.equalsIgnoreCase(casPredmet))
				System.out.printf("%s %s-%s: %s (%s)%n", casDan, casPoc, casKraj, casPredmet, casSala);
		}
	}
	
	
	private static void reportError(String message, Matcher matcher) {
		
		Pattern idPattern = Pattern.compile("(?sm)^UID:\\s*(?<id>.*?)\\s*$");
		Matcher idMatcher = idPattern.matcher(matcher.group(1));
		String id = idMatcher.find() ? idMatcher.group("id") : "???";
		
		Pattern infoPattern = Pattern.compile("(?sm)^SUMMARY:\\s*(?<info>.*?)\\s*$");
		Matcher infoMatcher = infoPattern.matcher(matcher.group(1));
		String info = infoMatcher.find() ? infoMatcher.group("info") : "???";
		
		System.err.printf("%s, dogadjaj %s: %s%n \n", message, id, info); 
	}
}
