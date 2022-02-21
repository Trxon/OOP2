package vezba.kol1_tvprogram_p00;

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
		System.out.println(text);
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
