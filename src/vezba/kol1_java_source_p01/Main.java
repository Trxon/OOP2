package vezba.kol1_java_source_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		
		showVariables1(file);
		System.out.println();
		
		System.out.println("VARIABLES : ");
		showVariables2(file);
		System.out.println();
		
		System.out.println("METHODS : ");
		showMethods(file);
		System.out.println();
	}
	
	
	private static void showMethods(String file) {
		
		Pattern p0 = Pattern.compile("(?sm)\\s(?<method>(public|private)\\s[\\w]*?\\s[\\w\\d]*?\\([\\s\\S]*?\\)\\s[\\s\\S]*?\\{[\\s\\S]*?(\\}\\n\\n))");
		Matcher m0 = p0.matcher(file);
		
		List<String> methods = new ArrayList<String>();
		
		while (m0.find())
			methods.add(m0.group("method").trim());
		
		methods.stream().forEach(System.out::println);
	}
	
	
	private static void showVariables2(String file) {
		
		Pattern p0 = Pattern.compile("\\s(?<var>(private)?\\s?(boolean|char|int|double|String)(\\[\\])?\\s[\\w]*?)(\\s=|;)");
		Matcher m0 = p0.matcher(file);
		
		List<String> variables = new ArrayList<String>();
		
		while (m0.find())
			variables.add(m0.group("var").trim());
		
		variables.stream().forEach(System.out::println);
	}
	
	
	private static void showVariables1(String file) {
		
		Pattern p0 = Pattern.compile("((((private|static|final|int|long|double|char|boolean|String)(\\[\\])?\\s)+)|(this\\.))(?<var>\\w[\\w\\d]*?)(;|\\s=\\s(?<val>[\\s\\S]*?);)");
		Matcher m0 = p0.matcher(file);
		
		Map<String, String> map = new HashMap<String, String>();
		
		while (m0.find())
			map.put(m0.group("var").trim(), m0.group("val") != null ? m0.group("val").trim() : "");
		
		System.out.println("           VARIABLES | VALUES    ");
		System.out.println("---------------------+-----------");
		map.entrySet().stream()
				.map(e -> String.format("%20s | %s", e.getKey(), e.getValue()))
				.forEach(System.out::println);
	}
	
	
	private static void showImports(String file) {
		
		Pattern p0 = Pattern.compile("(?sm)(?<imports>import[\\s\\S]*?)(public|private|class)");
		Matcher m0 = p0.matcher(file);
		
		m0.find();
		
		String importsString = m0.group("imports");
		
		Pattern p1 = Pattern.compile("(?sm)import\\s*?(?<import>[\\s\\S]*?);");
		Matcher m1 = p1.matcher(importsString);
		
		List<String> imports = new ArrayList<String>();
		
		while (m1.find())
			imports.add(m1.group("import").trim());
			
		imports.stream().forEach(System.out::println);
	}

	
	private static void showPackages(String file) {
		
		Pattern p0 = Pattern.compile("(?sm)(?<packages>package[\\s\\S]*?)import");
		Matcher m0 = p0.matcher(file);
		
		m0.find();
		
		String packagesString = m0.group("packages");
		
		Pattern p1 = Pattern.compile("(?sm)package\\s*?(?<package>[\\s\\S]*?);");
		Matcher m1 = p1.matcher(packagesString);
		
		List<String> packages = new ArrayList<String>();
		
		while (m1.find())
			packages.add(m1.group("package").trim());
		
		packages.stream().forEach(System.out::println);
	}


	private static String load(String fileName) {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(fileName)))) {
			
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
