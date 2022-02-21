package domaci_zadaci.z02_p02.time;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domaci_zadaci.z02_p02.raspored.Cas;
import domaci_zadaci.z02_p02.raspored.Raspored;

public class ICalendar {


	private static final String ERROR_LOG_PATH = "res//errorLog.txt";
	
	
	public static Raspored parseCalendar(String lines) {
		
		Map<Dani, TreeSet<Cas>> out = new HashMap<Dani, TreeSet<Cas>>();
		
		StringJoiner errorLog 	= new StringJoiner("");
		
		Pattern eventPattern 	= Pattern.compile(
				"(?sm)^BEGIN:VEVENT$(?<sadrzaj>.*?)^END:VEVENT$");
		Pattern dtstartPattern 	= Pattern.compile(
				"(?sm)^DTSTART(?:;.*?)*:(?:.*?)*T(?<sat>\\d{2})(?<minut>\\d{2})(?:.*?)$");
		Pattern dtendPattern 	= Pattern.compile(
				"(?sm)^DTEND(?:;.*?)*:(?:.*?)*T(?<sat>\\d{2})(?<minut>\\d{2})(?:.*?)$");
		Pattern rrulePattern 	= Pattern.compile(
				"(?sm)^RRULE(?:\\:.*?;BYDAY=)(?<dan>\\w{2})\\s*?$");
		Pattern summaryPattern 	= Pattern.compile(
				"(?sm)^SUMMARY:\\s*?(?<predmet>.*?)\\s*?\\\\,\\s*?(?<nastavnik>.*?)\\\\,\\s*?\\((?<tip>.*?)\\)\\s*?\\\\,\\s*?(?<sala>.*?)\\s*?$");
		
		Matcher eventMatcher	= eventPattern.matcher(lines);
		
		while (eventMatcher.find()) {
			
			String dogadjaj 	= eventMatcher.group("sadrzaj");
			
			Time 	pocetak		= null;
			Time 	kraj		= null;
			Dani	dan			= null;
			String	predmet		= null;
			String	nastavnik 	= null;
			String	tip			= null;
			String	sala		= null;
			
			Matcher dtstartMatcher 	= dtstartPattern.matcher(dogadjaj);
			if (dtstartMatcher.find())
				pocetak = new Time(
						Integer.parseInt(dtstartMatcher.group("sat")   ), 
						Integer.parseInt(dtstartMatcher.group("minut")));

			Matcher dtendMatcher 	= dtendPattern.matcher(dogadjaj);
			if (dtendMatcher.find())
				kraj 	= new Time(
						Integer.parseInt(dtendMatcher.group("sat")	 ), 
						Integer.parseInt(dtendMatcher.group("minut")));
			
			Matcher rruleMatcher	= rrulePattern.matcher(dogadjaj);
			if (rruleMatcher.find())
				dan		= Dani.naSrpskom(rruleMatcher.group("dan").toUpperCase());
			
			Matcher summaryMatcher	= summaryPattern.matcher(dogadjaj);
			if (summaryMatcher.find()) {
				predmet		= summaryMatcher.group("predmet").trim();
				nastavnik	= summaryMatcher.group("nastavnik").trim();
				tip			= summaryMatcher.group("tip").trim();
				sala		= summaryMatcher.group("sala").trim();
			}
			
			Cas cas = null;
			
			if (predmet == null || nastavnik == null || tip == null || sala == null)
				errorLog.add(reportError("Nepotpuni podaci o predmetu", eventMatcher));
			else if (pocetak == null)
				errorLog.add(reportError("Vreme pocetka nije dobro", eventMatcher));
			else if (kraj == null)
				errorLog.add(reportError("Vreme kraja nije dobro", eventMatcher));
			else if (dan == null)
				errorLog.add(reportError("Dan u nedelji nije dobar", eventMatcher));
			else
				cas = new Cas(pocetak, kraj, dan, predmet, nastavnik, tip, sala);
			
			if (cas != null) {
				if (!out.containsKey(cas.dan())) 
					out.put(cas.dan(), new TreeSet<Cas>());

				out.get(cas.dan()).add(cas);
			}
		}
		
		if (errorLog.length() != 0) {
			System.err.println("Prosledjeni tekst sadrzi greske. Pokusaj pisanja log fajla...");
			try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(ERROR_LOG_PATH)))) {
				pw.println(errorLog.toString());
				System.err.println("Log fajl uspesno kreiran -> " + ERROR_LOG_PATH);
			} catch (IOException e) {
				System.err.println("Greska prilikom pisanja log fajla -> " + e.getMessage());
			}
		}
		
		return Raspored.hm(out);
	}
	
	
	private static String reportError(String message, Matcher matcher) {
		
		Pattern idPattern = Pattern.compile("(?sm)^UID:\\s*(?<id>.*?)\\s*$");
		Matcher idMatcher = idPattern.matcher(matcher.group(1));
		String id = idMatcher.find() ? idMatcher.group("id") : "???";
		
		Pattern infoPattern = Pattern.compile("(?sm)^SUMMARY:\\s*?(?<info>.*?)\\s*?$");
		Matcher infoMatcher = infoPattern.matcher(matcher.group(1));
		String info = infoMatcher.find() ? infoMatcher.group("info") : "???";
		
		return String.format("%s, dogadjaj %s: %s%n ", message, id, info);
	}
}
