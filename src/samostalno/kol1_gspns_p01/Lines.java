package samostalno.kol1_gspns_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lines {
	
	
	private static Pattern PATTERN_CONTENT	= Pattern.compile("(?:<div class=table-title align=center>\\s*Linija\\:\\s*)(?<linbr>[^\\s]+)\\s*(?<linnaziv>[^\\<]*?)(?:<\\/div>)(?:[\\s\\S]*?)(?<smerovi>\\<tr\\>[\\s\\S]*?)(?:\\<\\/tr\\>)(?:[\\s\\S]+?\\<\\!--smer A--\\>)(?<spisaka>[\\s\\S]+?)(\\<\\!--smer B--\\>)(?<spisakb>[\\s\\S]+?)(?:<\\/tr>)");
	
	private static String urlStem = "http://www.gspns.rs/red-voznje/ispis-polazaka?rv=rvg&vaziod=2021-04-01&dan=R&linija[]=";
	
	private static String[] linije = {
			"20", "18B", "18A", "17*", "15", "14", "13", "12.", "11B.", "11A.", 
			"10", "9.", "8", "7B.", "7A.", "6", "5N.", "5", "4*", "3.", "2.", "1*"
	};

	
	private LinkedList<Line> lines;
	
	
	public Lines() throws IOException {
		
		lines = new LinkedList<Line>();
		
		for (String id : linije) {
			
			URL url = new URL(urlStem + id);
			Line l = parseLine(readURL(url));
			
			lines.add(l);			
		}
	}
	
	
	private static Line parseLine(String text) {
		
		Matcher matcherContent = PATTERN_CONTENT.matcher(text);
		
		Line l = new Line();

		if (matcherContent.find()) {
			
			l.setId(matcherContent.group("linbr"));
			l.setName(matcherContent.group("linnaziv").trim());
			
			l.parseDirections(
					matcherContent.group("smerovi"),
					matcherContent.group("spisaka"),
					matcherContent.group("spisakb"));
		}
		
		return l;
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
	
	
	public void showDepartures(String id) {
		for (Line l : lines) if (l.id().equalsIgnoreCase(id)) l.showDepartures();
	}
}
