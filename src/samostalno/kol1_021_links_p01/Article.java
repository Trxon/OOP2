package samostalno.kol1_021_links_p01;

import java.net.MalformedURLException;
import java.net.URL;

public class Article implements Comparable<Article> {

	
	private URL url;
	private String naslov;
	
	
	public URL url() { return url; }
	public String naslov() { return naslov; }
	
	
	public void setUrl(String url) {
		
		if (url == null) return;
		
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void setNaslov(String naslov) {
		if (naslov == null) return;
		this.naslov = naslov;
	}
	
	
	@Override
	public String toString() {
		return String.format("%-55s (link: %s)", 
				naslov.subSequence(0, naslov.length() >= 50 ? 50 : naslov.length()) + (naslov.length() >= 50 ? "..." : ""), 
				url.toString());
	}
	
	
	@Override
	public int compareTo(Article o) {
		return naslov.compareTo(o.naslov);
	}
}
