package samostalno.kol1_parsiranje_sa_podobjektima_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {

	public static void main(String[] args) throws IOException {
		
		Pattern p0 = Pattern.compile("(?sm).*?\\\"(?<name>[\\s\\S]*?)\\\"(.*)\\[\\{(?<content>[\\s\\S]*?)\\}\\]");
		Pattern p1 = Pattern.compile("(?sm)\\\"(?<name>[\\s\\S]*?)\\\"\\:\\\"(?<value>[\\s\\S]*?)\\\"");
		
		String text = load("json.txt");
		System.out.println(text);
		
		Matcher m0 = p0.matcher(text);
		m0.find();
		
		A a = new A();
		a.name = m0.group("name");
		a.hm = new HashMap<String, String>();
		
		Matcher m1 = p1.matcher(m0.group("content"));
		
		while (m1.find()) {
			
			String name = m1.group("name");
			String value = m1.group("value");
			
			a.hm.put(name, value);
		}
		
		System.out.println(a.name + " " + a.hm);
	}

	
	private static String load(String file) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream(file)));
		
		String line;
		StringBuilder sb = new StringBuilder();
		
		while ((line = in.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
