package domaci_zadaci.z02_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class RasporedProgram2 {

	
	public static void main(String[] args) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Godina za koju se trazi raspored : ");
		int godina = Integer.parseInt(in.readLine());
		String id = Id.getId(godina);
		
		if (id == null) {
			System.out.println("Nema kalendara za datu godinu.");
			return;
		}
		
		URL url = new URL("https://calendar.google.com/calendar/ical/" + id + "%40group.calendar.google.com/public/basic.ics");
		String text = readURL(url);
		
		Raspored raspored = readRaspored(text);
	}

	
	private static Raspored readRaspored(String text) {
		
		// Spajamo u jednu liniju sve osobine koje se nalaze u vise linija
		text = text.replaceAll("\n\\s", "");
		
		// Jednostavni regularni izrazi za pojedina linije od interesa
		String regexDtstart = "(?sm)^DTSTART.*?:(?<godinapoc>\\d{4})\\d*T(?<vremepoc>\\d{4})\\d{2}$";
		// String regexDtstart = "DTSTART(?:;.+?)*:(?<godinapoc>\\d{4})\\d{4}T(?<vremepoc>\\d{4})\\d{2}\n"; // Pribela
		
		String regexDtend = "(?sm)^DTEND.*?:(?<godinapoc>\\d{4})\\d*T(?<vremepoc>\\d{4})\\d{2}$";
		// String regexDtend = "DTEND(?:;.+?)*:(?<godinakraj>\\d{4})\\d{4}T(?<vremekraj>\\d{4})\\d{2}\n"; // Pribela
		
		String regexRrule = "(?sm)^RRULE.*?BYDAY=(?<dan>\\[A-Z]{2})$";
		// String regexRrule = "RRULE(?:;.+?)*:.*?BYDAY=(?<dan>[A-Z]{2}).*?\n"; // Pribela
		
		String regexSummary = "(?sm)^SUMMARY:.*?(?<naziv>[a-zA-Z ]*)\\\\\\,\\s*(?<nastavnik>[a-zA-Z .]*)\\\\\\,\\s*\\((?<tip>.*)\\)\\\\\\,\\s*(?<sala>.*?)\\s";
		// String regexSummary = "SUMMARY(?:;.+?)*:\\s*(?<predmet>.*?)\\s*(?:\\\\,\\s*(?<nastavnik>.*?)\\s*(?:\\\\,\\s*\\((?<tip>.*?)\\)\\s*(:?\\\\,\\s*(?<sala>.*?)\\s*?)?)?)?\n"; // Pribela
	
		String regexIgnore = ".*?\n";
		String regexBeginEvent = "BEGIN:VEVENT\n";
		String regexEndEvent = "END:VEVENT\n";
		
		return null;
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
}
