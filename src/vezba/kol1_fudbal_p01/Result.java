package vezba.kol1_fudbal_p01;

public class Result {

	
	private int team1;
	private int team2;
	
	
	public Result(int team1, int team2) {
		this.team1 = team1;
		this.team2 = team2;
	}
	
	
	public int team1() { return team1; }
	public int team2() { return team2; }
	
	
	public static Result fromString(String s) {
		if (s == null)
			return null;
		else
			return new Result(
				Integer.parseInt(s.split("-")[0].trim()), 
				Integer.parseInt(s.split("-")[1].trim()));
	}
	
	
	public String toString() {
		return team1 + "-" + team2;
	}
	
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o) return true;
		if (o == null) return false;
		
		if (getClass() != o.getClass())
			return false;
		
		Result r = (Result) o;
		
		if (team1 != r.team1 || team2 != r.team2) return false;
		
		return true;
	}
	
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		
		result = prime * result + team1;
		result = prime * result + team2;
		
		return result;
	}
}
