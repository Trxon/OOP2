package samostalno.kol1_json_parse_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Program {
	
	
	static final Pattern PATTERN_MOVIE = Pattern.compile("(?<start>\\{\\\"im:name\\\":)(?<content>[\\s\\S]*?)(?<end>\\}\\}\\}\\,)");
	
	static final Pattern PATTERN_CONTENT = Pattern.compile(""
			+ "(?:\\{\\\"im:name\\\":\\{\\\"label\\\":\\\")"
			+ "(?<title>[^(\\\"},)]+)[\\s\\S]*?(?:\\\"rights\\\":\\{\\\"label\\\":\\\")"
			+ "(?<rights>[^(\\\"},)]+)[\\s\\S]*?(?:\\\"summary\\\":\\{\\\"label\\\":\\\")"
			+ "(?<sum>.*)(?:\\\"}, \\\"im:rentalPrice\\\":\\{\\\"label\\\":\\\"\\$)"
			+ "(?<rental>[^(\\\", )]+)[\\s\\S]*?(?:\\\"im:price\\\":\\{\\\"label\\\":\\\"\\$)"
			+ "(?<price>[^(\\\", )]+)[\\s\\S]*?(?:\\\"im:artist\\\":\\{\\\"label\\\":\\\")"
			+ "(?<artist>[^(\\\"},)]+)[\\s\\S]*?(?:\\\"term\\\":\\\")"
			+ "(?<genre>[^(\\\", )]+)[\\s\\S]*?(?:\\\"im:releaseDate\\\":\\{\\\"label\\\":\\\")"
			+ "(?<date>[^T]+)[\\s\\S]*");
	

	public static void main(String[] args) throws IOException {
		
		URL url = new URL("https://itunes.apple.com/us/rss/topmovies/limit=25/json");
		String text = readURL(url);
		
		Map<Genre, List<Movie>> movies = readTextFile(text);
		
		movies.entrySet().stream().flatMap(e -> e.getValue().stream()).forEach(System.out::println);;
	}
	
	
	private static Map<Genre, List<Movie>> readTextFile(String text) {

		Matcher matcherMovie = PATTERN_MOVIE.matcher(text);
		
		List<Movie> list = new LinkedList<Movie>();
		
		while (matcherMovie.find()) {
			
			String movie = matcherMovie.group("start") + matcherMovie.group("content") + matcherMovie.group("end");
			
			Matcher matcherContent = PATTERN_CONTENT.matcher(movie);
			
			if (matcherContent.find()) {

				Movie m = new Movie();
				
				m.setTitle		(matcherContent.group("title")	);
				m.setRights		(matcherContent.group("rights")	);
				m.setSummary	(matcherContent.group("sum")	);
				m.setRental		(matcherContent.group("rental")	);
				m.setPrice		(matcherContent.group("price")	);
				m.setArtist		(matcherContent.group("artist")	);
				m.setGenre		(matcherContent.group("genre")	);
				m.setReleaseDate(matcherContent.group("date")	);
				
				list.add(m);
			}
		}
		
		return list.stream().collect(Collectors.groupingBy(Movie::genre, Collectors.toList()));
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
