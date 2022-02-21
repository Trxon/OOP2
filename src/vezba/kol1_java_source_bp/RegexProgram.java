package vezba.kol1_java_source_bp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vezba.kol1_java_source_p01.Main;

public class RegexProgram {

	public static void main(String[] args) throws IOException {
		String fajl = readFile("IgraVesanjaImpl.java.txt");
		showVariables(fajl);
		showMethods(fajl);
		showImports(fajl);
		showPackages(fajl);
	}
	
	private static void showPackages(String text) {
		System.out.println("Show packages - class IgraVesanjaImpl");
		Pattern p = Pattern.compile("(?<package>\\s*package\\s+\\w+(\\.(\\w+|\\*))*\\s*;)");
		Matcher m = p.matcher(text);
		while (m.find()) {
			System.out.println(m.group("package"));
		}
		System.out.println();
	}
	
	private static void showImports(String text) {
		System.out.println("Show imports - class IgraVesanjaImpl");
		Pattern p = Pattern.compile("(?<import>import\\s+\\w+(\\.(\\w+|\\*))*\\s*;)");
		Matcher m = p.matcher(text);
		while (m.find()) {
			System.out.println(m.group("import"));
		}
		System.out.println();
	}
	
	private static void showVariables(String text) {
		System.out.println("Show variables - class IgraVesanjaImpl");
		Pattern p = Pattern.compile("(?<variable>(private|public|protected)(\\s+static\\s+|\\s+)(final\\s+)?(\\w+\\s+\\w+\\s*)(=\\s*(.*))?\\s*;)");
		Matcher m = p.matcher(text);
		while (m.find()) {
			System.out.println(m.group("variable"));
		}
		System.out.println();
	}
	
	private static void showMethods(String text) {
		System.out.println("Show methods - class IgraVesanjaImpl");
		Pattern p = Pattern.compile("(?<method>\\t(@Override\n)?(private|public)(\\s+|\\s+static\\s+)(final\\s+)?(void|int|String|long|boolean|Object|float|double|StatusIgre)\\s+\\w+\\s*\\(.*\\)\\s+(throws .+)?\\{(\n)?[\\s\\w\\W\\S]*)\\}");
		Matcher m = p.matcher(text);
		while (m.find()) {
			System.out.println(m.group("method"));
		}
		System.out.println();
	}
	
	private static String readFile(String file) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(file)));
		StringBuilder sb = new StringBuilder();
		String s;
		while ((s = in.readLine()) != null) {
			sb.append(s);
			sb.append("\n");
		}
		in.close();
		return sb.toString();
	}
}
