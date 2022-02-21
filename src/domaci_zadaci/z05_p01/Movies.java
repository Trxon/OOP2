package domaci_zadaci.z05_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Movies {
	
	
	public static Movie getRandomMovie() throws IOException {
		
		List<Movie> movies = getMovies();
		return movies.get((int) (Math.random() * movies.size()));
	}
	

	@SuppressWarnings("unused")
	public static List<Movie> getMovies() throws IOException {
		
		// rekli ste da se nadate da necemo zamrzeti regexe i tokove zbog prvog kolokvijuma :) evo, ja nisam... za sada...
		
		Pattern p0 = Pattern.compile("(?sm)\\<div id=\\'row-index(?<content>[\\s\\S]*?)\\<\\/div\\>\\<br \\/\\>\\s*?");
		Matcher m0 = p0.matcher(readURL(new URL("https://editorial.rottentomatoes.com/guide/best-sci-fi-movies-of-all-time/")));
		
		List<Movie> movies = new LinkedList<Movie>();
		
		while (m0.find()) {
			
			String content = m0.group("content");
			
			Pattern p1 = Pattern.compile("(?sm)[\\s\\S]*?\\'article_movie_title\\'[\\s\\S]*?www.rottentomatoes[\\s\\S]*?\\/\\'\\>(?<title>[\\s\\S]*?)\\<\\/a\\>[\\s\\S]*?Critics Consensus\\:\\<\\/span\\>(?<consensus>[\\s\\S]*?)\\<\\/div\\>[\\s\\S]*?");
			Matcher m1 = p1.matcher(content);
			
			m1.find();
			
			movies.add(new Movie(m1.group("title"), m1.group("consensus")));
		}
		
		return movies;
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
