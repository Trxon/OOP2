package samostalno.kol1_parsiranje_sa_podobjektima_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {

	public static void main(String[] args) throws IOException {
		
		Pattern p0 = Pattern.compile("(?sm)[\\s\\S]*?\\\"speakers\\\"\\>(?<content>[\\s\\S]*?)\\<\\/list\\>");
		Pattern p1 = Pattern.compile("[\\s\\S]id=\\\"(?<id>[\\s\\S]*?)\\\"\\>(?<name>[\\s\\S]*?)\\<\\/item\\>");
		
		String text = load("json.txt");
		
		Matcher m0 = p0.matcher(text);
		m0.find();
		
		A a = new A();
		a.name = "speakers";
		
		Matcher m1 = p1.matcher(m0.group("content"));
		
		a.hm = new HashMap<String, String>();
		
		while (m1.find())
			a.hm.put(m1.group("id").trim(), m1.group("name").trim());
		
//		System.out.println(a.hm);
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
