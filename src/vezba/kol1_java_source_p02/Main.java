package vezba.kol1_java_source_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		
		String file = load("IgraVesanjaImpl.java.txt");
		
		System.out.println("PACKAGES : ");
		showPackages(file);
		System.out.println();
		
		System.out.println("IMPORTS : ");
		showImports(file);
		System.out.println();
		
		System.out.println("VARIABLES : ");
		showVariables1(file);
		System.out.println();
				
		System.out.println("METHODS : ");
		showMethods(file);
		System.out.println();
		
		System.out.println("METHOD HEADERS : ");
		showMethodHeaders(file);
		System.out.println();
	}
	

	private static void showMethodHeaders(String file) {
		
		Pattern p0 = Pattern.compile("(?sm)(?<method>(public|private)\\s(boolean|int|String|StatusIgre)?\\s*?(\\w*?)\\(.*?\\).*?)\\{");
		Matcher m0 = p0.matcher(file);
		
		while (m0.find())
			System.out.println("  " + m0.group("method").trim());
	}
	

	private static void showMethods(String file) {
		
		Pattern p0 = Pattern.compile("(?sm)(?<method>(@Override\\s*?)?(public|private)\\s(boolean|int|String|StatusIgre)?\\s*?(\\w*?)\\(.*?\\).*?\\{.*?\\}\\n\\n)");
		Matcher m0 = p0.matcher(file);
		
		while (m0.find())
			System.out.println("  " + m0.group("method").trim());
	}


	private static void showVariables1(String file) {
		
		Pattern p0 = Pattern.compile("(?sm)(?<var>(private\\s|public\\s)(static\\s)?(final\\s)?(long\\s|int\\s|String\\s|)(?:[a-zA-Z0-9]*?)(\\s*?=\\s*?(.*?))?);");
		Matcher m0 = p0.matcher(file);
		
		while (m0.find())
			System.out.println("  " + m0.group("var"));
	}


	private static void showImports(String file) {
		
		Pattern p0 = Pattern.compile("(?sm)import\\s*?(?<import>.*?)\\;\\s*?");
		Matcher m0 = p0.matcher(file);
		
		while (m0.find())
			System.out.println("  " + m0.group("import").trim());
	}


	private static void showPackages(String file) {
		
		Pattern p0 = Pattern.compile("(?sm)package\\s*?(?<package>.*?)\\;\\s*?");
		Matcher m0 = p0.matcher(file);
		
		while (m0.find())
			System.out.println("  " + m0.group("package").trim());
	}


	private static String load(String path) {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(path)))) {
			
			StringBuilder sb = new StringBuilder();
			String line;
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			return sb.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
