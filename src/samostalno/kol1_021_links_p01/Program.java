package samostalno.kol1_021_links_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {
	
	
	private static Pattern PATTERN_DATA = Pattern.compile("(?<data><div class=\\\"aTitle\\\">[\\s\\S]*?<\\/div>)");
	
	private static Pattern PATTERN_CONTENT = Pattern.compile("(?:<div class=\\\"aTitle\\\">[\\s\\S]+?<a href=\\\")(?<link>[^(\\\")]*)(?:\\\"\\>[\\s\\S]*?\\<span\\>)\s*(?<naslov>[^\\<]*)");
	
	private static Pattern PATTERN_DESCRIPTION = Pattern.compile("(?:<meta name=\\\"description\\\" content=)\\\"(?<opis>[^\\\"]*)");
	

	public static void main(String[] args) throws IOException {
		
		URL url = new URL("https://www.021.rs");
		String text = readURL(url);
		
		List<Article> clanci = readTextFile(text);
		
		clanci.stream().parallel().sorted(Comparator.naturalOrder()).forEachOrdered(System.out::println);
		System.out.println();
		
		System.out.println("Vučić se u člancima pojavljuje puta : " + 
				clanci.stream().filter(a -> a.naslov().contains("Vučić")).count());
		
		System.out.println("Vulin se u člancima pojavljuje puta : " + 
				clanci.stream().filter(a -> a.naslov().contains("Vulin")).count());
		
		URL url0 = new URL("https://www.021.rs/story/Novi-Sad/Sport/272218/Vojvodina-obezbedila-plasman-u-Evropu.html");
		
		System.out.println(getDescription(url0));
	}
	
	
	private static String getDescription(URL url) throws IOException {
		
		String text = readURL(url);
		Matcher matcherDescription = PATTERN_DESCRIPTION.matcher(text);
		
		if (matcherDescription.find())
			return matcherDescription.group("opis");
		
		return null;
	}


	private static List<Article> readTextFile(String text) {
		
		Matcher dataMatcher = PATTERN_DATA.matcher(text);
		List<Article> list = new LinkedList<Article>();
		
		while (dataMatcher.find()) {
			
			String data = dataMatcher.group("data");
			
			Matcher contentMatcher = PATTERN_CONTENT.matcher(data);
			
			if (contentMatcher.find()) {
				
				Article a = new Article();
				
				a.setUrl(contentMatcher.group("link"));
				a.setNaslov(contentMatcher.group("naslov"));
				
				list.add(a);
			}
		}
		
		return list;
	}


	private static String readURL(URL url) throws IOException {
		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(url.openStream()));
		
		StringBuilder sb = new StringBuilder();
		String line;
		
		while ((line = in.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		
		in.close();
		return sb.toString();
	}
}
