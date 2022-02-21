package teorijske_vezbe.tv01_regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExample2 {

	public static void main(String[] args) {
		String tekst = "060 123456789 | NS 123-AB | 061/234-56-78 | +381 21 123 456 | 194 | noreply@somedomain.com | studentska@pmf.uns.ac.rs | ime.prezime@domen.rs | 123-456";
		String regex = "(?<=\\s|^)(?:(?<pozivni>\\+[0-9]{1,3} [0-9]{2}|0[0-9]{2})[ /])?(?<osnovni>[0-9]{2,}(?:[ -][0-9]{2,})*)(?=\\s|$)";

//                      (?<=\\s|^)                                                                                                        - Ispred mora ici razmak ili pocetak stringa
//                                                                                                                            (?=\\s|$)   - Iza mora ici razmak ili kraj stringa

//                                                                                     (?<osnovni>[0-9]{2,}(?:[ -][0-9]{2,})*)
//                                                                                     (?<osnovni>                           )            - Osnovni broj je oznacen imenovanom grupom
//                                                                                                [0-9]{2,}                               - Sastoji se od grupe cifara (bar dve)
//                                                                                                            [ -][0-9]{2,}               - I potom jos grupa cifara odvojenih razmakom ili crticom
//                                                                                                         (               )*             - Ovih dodatnih grupa moze bili nula ili vise
//                                                                                                          ?:                            - Ne zelimo da se ova grupa broji

//                                (?:(?<pozivni>\\+[0-9]{1,3} [0-9]{2}|0[0-9]{2})[ /])?
//                                (?:                                                )?                                                   - Deo sa pozivnim brojem nije obavezan
//                                   (?<pozivni>                                )[ /]                                                     - A odvojen je od osnovnog broja razmakom ili kosom crtom
//                                                                    |                                                                   - Takodje postoje dva oblika pozivnog
//                                              \\+[0-9]{1,3} [0-9]{2}                                                                    - Prvi oblik pozivnog: +381 21
//                                                                     0[0-9]{2}                                                          - Drugi oblik pozivnog: 021

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(tekst);
		while (matcher.find()) {
			System.out.println(matcher.group(0));
//			System.out.println("      0: " + matcher.start(0) + "-" + matcher.end(0) + ": " + matcher.group(0));
//			System.out.println("      1: " + matcher.start(1) + "-" + matcher.end(1) + ": " + matcher.group(1));
//			System.out.println("      2: " + matcher.start(2) + "-" + matcher.end(2) + ": " + matcher.group(2));
			System.out.println("pozivni: " + matcher.start("pozivni") + "-" + matcher.end("pozivni") + ": " + matcher.group("pozivni"));
			System.out.println("osnovni: " + matcher.start("osnovni") + "-" + matcher.end("osnovni") + ": " + matcher.group("osnovni"));
			System.out.println();
		}
	}
}
