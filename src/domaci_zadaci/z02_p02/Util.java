package domaci_zadaci.z02_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import domaci_zadaci.z02_p02.time.Dani;

public class Util {

	
	protected static Map<Integer, String> identifikatori() {
		
		Map<Integer, String> identifikatori = new HashMap<Integer, String>();
		
		identifikatori.put(1, "g3khre7jrsih1idp5b5ahgm1f8");
		identifikatori.put(2, "hu93vkklcv692mikqvm17scnv4");
		identifikatori.put(3, "6ovsf0fqb19q10s271b9e59ttc");
		identifikatori.put(4, "a730slcmbr9c6dii94j9pbdir4");
		
		return identifikatori;
	}
	
	
	protected static Map<Integer, Set<String>> moguciOdgovori() {
		
		Map<Integer, Set<String>> moguciOdgovori = new HashMap<Integer, Set<String>>();
		
		moguciOdgovori.put(1, new HashSet<String>());
		moguciOdgovori.get(1).addAll(Arrays.asList(new String[] { "1", "1.", "i", "prva", "jedan" }));
		
		moguciOdgovori.put(2, new HashSet<String>());
		moguciOdgovori.get(2).addAll(Arrays.asList(new String[] { "2", "2.", "ii", "druga", "dva" }));
		
		moguciOdgovori.put(3, new HashSet<String>());
		moguciOdgovori.get(3).addAll(Arrays.asList(new String[] { "3", "4.", "iii", "treca", "tri" }));
		
		moguciOdgovori.put(4, new HashSet<String>());
		moguciOdgovori.get(4).addAll(Arrays.asList(new String[] { "4", "4.", "iv", "cetvrta", "cetiri" }));
		
		return moguciOdgovori;
	}
	
	
	protected static String getId(Scanner sc) {
		
		String identifikator = null;
		
		do {
			System.out.print("Godina za koju zelite raspored : ");
			String op = sc.nextLine();
			
			for (int i = 1; i < 5; i++) 
				if (moguciOdgovori().get(i).contains(op.toLowerCase()))
					identifikator = identifikatori().get(i);
			
			if (identifikator == null) System.out.println("Pogresan unos!");
		} while (identifikatori() == null);
		
		return identifikator;
	}
	
	
	protected static String getCalendar(String id) throws IOException {
		return readURL(new URL(
				"https://calendar.google.com/calendar/ical/" + id +
				"%40group.calendar.google.com/public/basic.ics") );
	}
	

	protected static String readURL(URL url) throws IOException {
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
	
	
	protected static Dani getDan(Scanner sc) {
		
		int op = 0;
		
		do {
			System.out.print("Redni broj dana (1 - 7) za koji zelite raspored : ");
			op = sc.nextInt();
			
			if (!(1 <= op && op <= 7)) 
				System.out.println("Pogresan unos!");
		} while (!(1 <= op && op <= 7));
		
		switch (op) {
			case 1:  return Dani.PON;
			case 2:  return Dani.UTO;
			case 3:  return Dani.SRE;
			case 4:  return Dani.CET;
			case 5:  return Dani.PET;
			case 6:  return Dani.SUB;
			case 7:  return Dani.NED;
			default: return Dani.PON;
		}
	}
}
