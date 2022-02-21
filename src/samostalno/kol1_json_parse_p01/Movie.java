package samostalno.kol1_json_parse_p01;

import java.time.LocalDate;

public class Movie {
	
	
	private String 		title, 
						rights, 
						summary, 
						artist; 
	private Genre		genre;
	private double 		rental, 
						price;
	private LocalDate 	releaseDate;
	
	
	public String title() { return title; }
	public String rights() { return rights; }
	public String summary() { return summary; }
	public String artist() { return artist; }
	public Genre genre() { return genre; }
	public double rental() { return rental; }
	public double price() { return price; }
	public LocalDate releaseDate() { return releaseDate; }
	
	
	public void setTitle(String title) {
		if (title == null) return;
		this.title = title;
	}
	
	
	public void setRights(String rights) {
		if (rights == null) return;
		this.rights = rights;
	}
	
	
	public void setSummary(String summary) {
		if (summary == null) return;
		this.summary = summary;
	}
	
	
	public void setArtist(String artist) {
		if (artist == null) return;
		this.artist = artist;
	}
	
	
	public void setGenre(String genre) {
		if (genre == null) return;
		this.genre = Genre.fromString(genre);
	}
	
	
	public void setRental(String rental) {
		if (rental == null) return;
		this.rental = Double.parseDouble(rental.trim());
	}
	
	
	public void setPrice(String price) {
		if (price == null) return;
		this.price = Double.parseDouble(price.trim());
	}
	
	
	public void setReleaseDate(String releaseDate) {
		if (releaseDate == null) return;
		this.releaseDate = LocalDate.parse(releaseDate);
	}
	
	
	public String formattedPrint() {
		return String.format("%s (%d), directed by %s. %s %n  [ %-9s] %s", 
				title.toUpperCase(), 
				releaseDate.getYear(), 
				artist(), 
				rights(), 
				genre().toString().toLowerCase(),
				summary().subSequence(0, summary.length() >= 125 ? 125 : 100) + "...");
	}
	
	
	@Override
	public String toString() {
		return String.format("%s (%d), directed by %s. %s", 
				title.toUpperCase(), 
				releaseDate.getYear(), 
				artist(), 
				rights());
	}
	
	
	public boolean isComplete() {
		
		if (title		== null ||
			rights		== null ||
			summary		== null ||
			artist		== null ||
			genre		== null ||
			rental		== 0	||
			price		== 0	||
			releaseDate	== null ){
			return false;		
		}
		
		return true;
	}
	
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o) return true;
		if (o == null) return false;
		
		if (getClass() != o.getClass())
			return false;
		
		Movie m = (Movie) o;
		
		if (!title.equalsIgnoreCase(m.title) 		|| 
			!rights.equalsIgnoreCase(m.rights)		||
			!summary.equalsIgnoreCase(m.summary)	||
			!artist.equalsIgnoreCase(m.artist)		||
			genre != m.genre						||
			rental != m.rental						||
			price != m.price						||
			!releaseDate.equals(m.releaseDate)	    ){
			return false;		
		}
		
		return true;
	}
	
	
	@Override
	public int hashCode() {
		
		if (!isComplete()) return 0;
		
		int res = 1;
		final int prime = 31;
		final int anotherPrime = 53;
		
		res = res * prime + title.hashCode()		;
		res = res * prime + rights.hashCode()		;
		res = res * prime + summary.hashCode()		;
		res = res * prime + artist.hashCode()		;
		res = res * prime + genre.hashCode()		;
		res = res * prime + (int) (anotherPrime * rental);
		res = res * prime + (int) (anotherPrime * price );
		res = res * prime + releaseDate.hashCode()	;
		
		return res;
	}
}
