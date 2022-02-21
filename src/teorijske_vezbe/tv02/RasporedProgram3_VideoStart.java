package teorijske_vezbe.tv02;
//package teorijske_vezbe.tv03;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
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
//		stampajSve(casovi);
//	}
//
//	public static void stampajSve(List<Cas> casovi) {
//		for (Cas cas : casovi) {
//			System.out.println(cas);
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