package samostalno.kol1_parsiranje_sa_podobjektima_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {

	public static void main(String[] args) throws IOException {
		
		Pattern p0 = Pattern.compile("(?sm)(?<desc>[\\s\\S]*?)\\<list[\\s\\S]*?\\>(?<content>[\\s\\S]*?)\\<\\/list\\>");
		Pattern p1 = Pattern.compile("(?sm)\\<label\\>\\((?<int>\\d*?)\\)[\\s\\S]*?\\<item\\>[\\s\\S]*?(?<msg>[\\s\\S]*?)\\<\\/item\\>");
		
		String text = load("json.txt");
		
		Matcher m0 = p0.matcher(text);
		m0.find();
		
		A a = new A();
		a.desc = m0.group("desc").trim();
		
		a.hm = new HashMap<Integer, String>();
		
		Matcher m1 = p1.matcher(m0.group("content"));
		
		while (m1.find())
			a.hm.put(Integer.parseInt(m1.group("int").trim()), m1.group("msg"));
		
		System.out.println(a.desc);
		System.out.println(a.hm);
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
