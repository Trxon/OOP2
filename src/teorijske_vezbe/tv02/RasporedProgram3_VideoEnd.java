package teorijske_vezbe.tv02;
//package oop2.v03.lambda;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import oop2.r01.raspored.RasporedProgram1;
//import oop2.r02.raspored.Cas;
//import oop2.r02.raspored.Raspored;
//import oop2.r02.raspored.RasporedProgram2;
//
//public class RasporedProgram3 {
//
//	public static void main(String[] args) throws IOException {
//		String text = readFile();
//		Raspored raspored = RasporedProgram2.readRaspored(text);
//		List<Cas> casovi = raspored.getList();
//		stampaj(
//				casovi,
//				cas -> true,
//				(cas1, cas2) -> cas1.getPredmet().compareTo(cas2.getPredmet()),
//				cas -> System.out.println(cas)
//		);
//	}
//
//	public interface Kriterijum {
//		public boolean zadovoljava(Cas cas);
//	}
//	
//	public interface Akcija {
//		public void uradi(Cas cas);
//	}
//
//	public static void stampaj(List<Cas> casovi, Kriterijum kriterijum, Comparator<Cas> redosled, Akcija akcija) {
//		List<Cas> pom = new ArrayList<>(casovi);
//		Collections.sort(pom, redosled);
//		for (Cas cas : pom) {
//			if (kriterijum.zadovoljava(cas)) {
//				akcija.uradi(cas);
//			}
//		}
//	}
//
//	public static String readFile() throws IOException {
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
//}
