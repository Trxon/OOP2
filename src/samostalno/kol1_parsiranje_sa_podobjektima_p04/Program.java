package samostalno.kol1_parsiranje_sa_podobjektima_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {

	public static void main(String[] args) throws IOException {
		
		Pattern p0 = Pattern.compile("(?sm)\\<food\\>(?<content>[\\s\\S]*?)\\<\\/food\\>");
		Pattern p1 = Pattern.compile("(?sm)\\<name\\>(?<name>[\\s\\S]*?)\\<\\/name\\>\\n<price\\>(?<price>\\$\\d*?\\.\\d*?)\\<\\/price\\>\\n\\<description\\>\\s*?(?<desc>[\\s\\S]*?)\\<\\/description\\>\\s*\\<calories\\>(?<cal>[\\s\\S]*?)\\<");
		
		String text = load("json.txt");
		
		Matcher m0 = p0.matcher(text);
//		m0.find();
		
		List<Food> l = new ArrayList<Food>();
		
		while (m0.find()) {
			
			Matcher m1 = p1.matcher(m0.group("content"));
			m1.find();
			
			Food f = new Food();
			
			f.name = m1.group("name").trim();
			f.price = Double.parseDouble(m1.group("price").trim().substring(1));
			f.desc = m1.group("desc");
			f.cal = Integer.parseInt(m1.group("cal"));
			
			l.add(f);
		}
		
		for (Food f : l) System.out.println(f.name + " " + f.price + " " + f.desc + " " + f.cal);
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
