package vezba.kol1_tvprogram_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TVProgram {
	
/*
 * Napisati program koji ispisuje sve emisije na nekom kanalu sort-
 * irane po vremenu. Napraviti klasu TVEmisija koja predstavlja em-
 * isiju. Ova klasa od osobina ima vreme i naziv. Takođe, ova klasa
 * implementira interfejs Comparable kao i metode equals(), hashCo-
 * de() i toString(). Takodje napraviti klasu TVRaspored koja sadr-
 * ži mapu čiji su ključevi kanali, dok su vrednosti liste svih em-
 * isija na tom kanalu sortirane po vremenu početka emisije. Takođe
 * potrebno je implementirati metod za dodavanje nove emisije u ma-
 * pu. Učitati odgovarajući raspored, izvući potrebne podatke u in-
 * stance klase TVEmisija i smestiti ih u jednu instancu klase TVR-
 * aspored. Pitati korisnika za koji kanal želi raspored i ispisati
 * emisije. Metod za učitavanje rasporeda sa interneta je već dat.
 */

	public static void main(String[] args) throws IOException {
		
		URL url = new URL("https://www.tvprogram.rs");
		String text = readURL(url);
		
		TVRaspored raspored = readTextFiles(text);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Program za koji zelite raspored : ");
		String kanal = in.readLine();
		
		raspored.stampajZaKanal(kanal);
	}
	
	
	private static TVRaspored readTextFiles(String text) {
		
		Pattern patternListaKanala = Pattern.compile(
				"(<ul class=\\\"channel-names\\\">\\n)(?<lista>(\\s*<li>.*<\\/li>)*)\\n(\\s*<\\/ul>)");
		Matcher matcherListaKanala = patternListaKanala.matcher(text);
		
		LinkedList<String> kanali = new LinkedList<String>();
		
		while (matcherListaKanala.find()) {
			
			Pattern patternKanal = Pattern.compile("\\s(?:<li>)(?<kanal>.*)(?:<\\/li>)\\n*");
			Matcher matcherKanal = patternKanal.matcher(matcherListaKanala.group("lista"));
			
			while (matcherKanal.find())
				kanali.addLast(matcherKanal.group("kanal"));
		}
		
		TVRaspored raspored = new TVRaspored();
		
		Pattern patternSadrzaj = Pattern.compile(
				"(<!\\-\\- 1st line \\-\\->\\n)\\s*(<ul class=\\\"tv\\-satnica clearfix\\\">\\n)(?<sve>[.\\s\\S]*?)(<\\/ul>)");
		Matcher matcherSadrzaj = patternSadrzaj.matcher(text);
		
		while (matcherSadrzaj.find()) {
			
			String sve = matcherSadrzaj.group("sve");
			
			Pattern patternEmisija = Pattern.compile("(?<vreme>\\d{2}\\:\\d{2})([\\s\\S]*?)((<p>)(<.*?>)?(?<naziv>[^<]+)(<.*?>)?)");
			Matcher matcherEmisija = patternEmisija.matcher(sve);
			
			String trenutniKanal = kanali.poll();
			raspored.dodajKanal(trenutniKanal);
			
			while (matcherEmisija.find())
				raspored.dodajEmisiju(trenutniKanal, matcherEmisija.group("vreme"), matcherEmisija.group("naziv"));
		}
		
		return raspored;
	}
	

	private static String readURL(URL url) throws IOException {
		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(url.openStream()));
		
		StringBuilder sb = new StringBuilder();
		String line;
		
		while ((line = in.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		
		in.close();
		return sb.toString();
	}
}
