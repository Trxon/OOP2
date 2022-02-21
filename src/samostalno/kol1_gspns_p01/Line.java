package samostalno.kol1_gspns_p01;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Line {
	
	
	private static Pattern PATTERN_ROUTE	= Pattern.compile("(:?\\<th\\>Smer\\s)(?<pravac>\\w)(:?\\:\\s*?)(?<smer>[\\s\\S]*?)(:?\\<\\/th\\>)");
	private static Pattern PATTERN_HOUR		= Pattern.compile("^(?:<b>)(?<sat>\\d{1,})(?:<\\/b>)[\\s\\S]*?(?:<sup>)(?<minute>[\\s\\S]*?)$");
	private static Pattern PATTERN_MIN		= Pattern.compile("(:?[\\s\\S]*?span[\\s\\S]*?)(?<minut>\\d{1,})(:?[\\s\\S]*?<\\/span>)");

	
	private String id;
	private String[] name;
	private Departures depA, depB;
	
	
	public String id() 			{ return id; 	}
	public String[] name() 		{ return name; 	}
	public Departures depA() 	{ return depA; 	}
	public Departures depB() 	{ return depB; 	}
	
	
	public String nameAsString() {
		return Arrays.stream(name).reduce("", (s0, s1) -> "".equals(s0) ? s1 : s0 + " - " + s1);
	}
	
	
	public Map<Integer, List<LocalTime>> depAAsMap() {
		if (depA == null) return null;
		return depA.departuresAsMap();
	}
	
	
	public Map<Integer, List<LocalTime>> depBAsMap() {
		if (depB == null) return null;
		return depB.departuresAsMap();
	}
	
	
	public void setId(String id) {
		if (id == null) return;
		this.id = id;
	}
	
	
	public void setName(String name) {
		if (name == null) return;
		this.name = name.trim().split("\s*-\s*");
	}
	
	
	public void setDepA(Departures depA) {
		if (depA == null) return;
		this.depA = depA;
	}
	
	
	public void setDepB(Departures depB) {
		if (depB == null) return;
		this.depB = depB;
	}
	
	
	public void parseDirections(String routeRaw, String depARaw, String depBRaw) {
		
		Matcher matcherDir = PATTERN_ROUTE.matcher(routeRaw);
		
		List<Departures> out = new ArrayList<Departures>();
		
		while (matcherDir.find()) {
			
			Departures d = new Departures();
			
			d.setDirection(matcherDir.group("pravac"));
			d.setRoute(matcherDir.group("smer"));
			
			out.add(d);
		}
		
		if (out.size() >= 1) {
			out.get(0).setDepartures(parseDepartures(depARaw));
			setDepA(out.get(0));
		}
		
		if (out.size() >= 2) {
			out.get(1).setDepartures(parseDepartures(depBRaw));
			setDepB(out.get(1));
		}
	}


	private static List<LocalTime> parseDepartures(String in) {
		
		String[] lines = in.split("\\<br\\/\\>");
		List<LocalTime> departures = new ArrayList<LocalTime>();
		
		for (String s : lines) {
			
			Matcher matcherHour = PATTERN_HOUR.matcher(s);
			
			if (matcherHour.find()) {
				
				String hour = matcherHour.group("sat");
				String minutes = matcherHour.group("minute");
				
				Matcher matcherMin = PATTERN_MIN.matcher(minutes);
				
				while (matcherMin.find())
					departures.add(LocalTime.of(
							Integer.parseInt(hour.trim()), 
							Integer.parseInt(matcherMin.group("minut").trim())));
			}
		}
		
		return departures;
	}
	
	
	public boolean isComplete() {
		if (id == null || name == null || depA == null || depB == null) return false;
		return true;
	}
	
	
	public void showDepartures() {
		
		System.out.printf("%s %s %n", id(), nameAsString());
		
		System.out.printf("  Smer A : %s%n", depA != null ? depA.routeAsString() : "-");
		if (depA != null) depA.departuresFormatted();
		
		System.out.printf("  Smer B : %s%n", depB != null ? depB.routeAsString() : "-");
		if (depB != null) depB.departuresFormatted();
	}
	
	
	@Override
	public String toString() {
		return String.format("%s %s : %s / %s %n", 
				id(), 
				nameAsString(), 
				depA != null ? depA.routeAsString() : "-", 
				depB != null ? depB.routeAsString() : "-");
	}
	
	
	@Override
	public int hashCode() {
		
		if (!isComplete()) return 0;
		
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((depA == null) ? 0 : depA.hashCode());
		result = prime * result + ((depB == null) ? 0 : depB.hashCode());
		result = prime * result + ((id == null)   ? 0 : id.hashCode())  ;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null) return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Line other = (Line) obj;
		
		if (depA == null) {
			if (other.depA != null)
				return false;
		} else if (!depA.equals(other.depA))
			return false;
		
		if (depB == null) {
			if (other.depB != null)
				return false;
		} else if (!depB.equals(other.depB))
			return false;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	}
}
