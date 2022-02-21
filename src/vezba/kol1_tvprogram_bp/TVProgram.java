package vezba.kol1_tvprogram_bp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TVProgram {
	
	/*
	 * Napisati program koji ispisuje sve emisije na nekom kanalu sortirane po
	 * vremenu. Napraviti klasu TVEmisija koja predstavlja emisiju. Ova klasa od
	 * osobina ima vreme i naziv. Takođe, ova klasa implementira interfejs
	 * Comparable kao i metode equals(), hashCode() i toString(). Napraviti klasu
	 * TVRaspored koja sadrži mapu čiji su ključevi kanali, a vrednosti liste svih
	 * emisija na tom kanalu sortirane po vremenu početka emisije. Takođe, potrebno
	 * je implementirati metod za dodavanje nove emisije u mapu. Učitati
	 * odgovarajući raspored, izvući potrebne podatke u instance klase TVEmisija i
	 * smestiti ih u jednu instancu klase TVRaspored. Pitati korisnika za koji kanal
	 * želi raspored i ispisati emisije. Metod za učitavanje rasporeda sa interneta
	 * je već dat.
	 */

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		URL url = new URL("https://www.tvprogram.rs");
		String tekst = readURL(url);
		TVRaspored raspored = rasporedPrikazivanja(tekst);
		raspored.mapa.entrySet().forEach(System.out::println);
		System.out.print("Unesite za koji kanal želite raspored: ");
		String kanal = scanner.nextLine();
		if (raspored.listaEmisija(kanal) != null) {
			raspored.listaEmisija(kanal).stream()
				.forEach(System.out::println);
		} else {
			System.err.println("Kanal " + kanal + " ne postoji u rasporedu.");
		}
		scanner.close();
	}
	
	private static String readURL(URL url) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		in.close();
		return sb.toString();
	}
	
	private static TVRaspored rasporedPrikazivanja(String tekst) {
		TVRaspored raspored = new TVRaspored();
		LinkedList<String> listaKanala = new LinkedList<String>();

		String regexKanal = "(<ul class=\"channel-names\">\n)((\\s*<li>.*</li>)*)\n\\s*(</ul>)";
		
		Pattern p1 = Pattern.compile(regexKanal);
		Matcher m1 = p1.matcher(tekst);

		while (m1.find()) {
			String kanali = m1.group(2);
			Pattern p2 = Pattern.compile("\\s*<li>(?<kanal>.*)</li>\\s*");
			Matcher m2 = p2.matcher(kanali);
			while (m2.find()) {
				listaKanala.addLast(m2.group("kanal"));
			}
		}
		
		String naziv, vreme;
		String emisijeRegex = "<!-- 1st line -->\n" + 
				"[\\s]*(<ul class=\"tv-satnica clearfix\">\n)" +
				"(?<sve>[.\\s\\S]*?)(</ul>)";
		
		Pattern p3 = Pattern.compile(emisijeRegex);
		Matcher m3 = p3.matcher(tekst);

		while (m3.find()) {
			String emisijaRegex = "(?<vreme>\\d{2}:\\d{2})[\\w\\s\\S]*?<p>(<a[.\\s\\S]*?>)?(?<emisija>.*?)(</a>)?</p>";
			Pattern p4 = Pattern.compile(emisijaRegex);
			Matcher m4 = p4.matcher(m3.group("sve"));
			String kanalStr = listaKanala.poll();
			
			while (m4.find()) {
				TVEmisija emisija = new TVEmisija();
				naziv = m4.group("emisija");
				vreme = m4.group("vreme");
				emisija.setNaziv(naziv);
				emisija.setVreme(vreme);
				if (emisija.jeOk()) {
					raspored.dodajEmisiju(kanalStr, emisija);
				}
			}
		}
		return raspored;
	}

}
