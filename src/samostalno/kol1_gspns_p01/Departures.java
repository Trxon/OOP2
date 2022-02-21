package samostalno.kol1_gspns_p01;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Departures {
	
	
	private Direction direction;
	private String[] route;
	private List<LocalTime> departures;
	
	
	public Direction direction() 		{ return direction; 	}
	public String[] route()				{ return route;			}
	public List<LocalTime> departures() { return departures; 	}
	
	
	public String routeAsString() {
		return Arrays.stream(route).reduce("", (s0, s1) -> "".equals(s0) ? s1 : s0 + "-" + s1);
	}
	
	
	protected Map<Integer, List<LocalTime>> departuresAsMap() {
		return departures().stream()
				.collect(
						Collectors.groupingBy(
								LocalTime::getHour, 
								Collectors.toList()));
	}
	
	
	public void departuresFormatted() {
		
		Map<Integer, List<LocalTime>> map = departuresAsMap();
		
		map.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.map(e -> {
				
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("    %02d ", e.getKey()));
				sb.append(e.getValue());
				
				return sb.toString();
			}).forEach(System.out::println);
	}
	
	
	public void setDirection(String direction) {
		if (direction == null) return;
		this.direction = Direction.fromString(direction);
	}
	
	
	public void setRoute(String route) {
		if (route == null) return;
		this.route = route.trim().split("\s*-\s*");
	}
	
	
	public void setDepartures(List<LocalTime> departures) {
		if (departures == null) return;
		this.departures = departures;
	}
	
	
	public boolean isComplete() {
		if (direction == null || route == null || departures == null) return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s %s : %s", 
				routeAsString(), 
				direction(), 
				departures() != null ? 
						departures.toString().length() > 150 ? 
								departures.toString().subSequence(0, 147) + "..." : departures.toString()
						: "NEMA");
	}
	
	
	@Override
	public int hashCode() {
		
		if (!isComplete()) return 0;
		
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((departures == null) ? 0 : departures.hashCode());
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null) return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Departures other = (Departures) obj;
		
		if (departures == null) {
			if (other.departures != null)
				return false;
		} else if (!departures.equals(other.departures))
			return false;
		
		if (direction != other.direction)
			return false;
		
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		
		return true;
	}
}
