package vezba.kol1_fudbal_p01;

import java.util.Arrays;

public class Scorer {

	
	private String name;
	private String time;
	private boolean own;
	private boolean pen;
	
	
	public Scorer(String name, String time, boolean own, boolean pen) {
		this.name = name;
		this.time = time;
		this.own  = own ;
		this.pen  = pen ;
	}


	public String name() 	{ return name; 	}
	public String time() 	{ return time; 	}
	public boolean isOwn() 	{ return own; 	}
	public boolean isPen() 	{ return pen; 	}
	
	
	public int timeAsInt() {
		
		String[] tokens = time.split("\\+");
		
		return Arrays.stream(tokens)
			.map(s -> Integer.parseInt(s))
			.reduce(0, (a, b) -> a + b);
	}
	
	
	@Override
	public String toString() {
		return name + " " + time + "'" + (own ? " (o.g.)" : "") + (pen ? " (pen.)" : ""); 
	}
	
	
	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null) return false;
		
		if (getClass() != o.getClass())
			return false;
		
		Scorer s = (Scorer) o;
		
		if (!name.equals(s.name) || !time.equals(s.time) || own != s.own || pen != s.pen)
			return false;
		
		return true;
	}
	
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		
		result = prime * result + name.hashCode();
		result = prime * result + time.hashCode();
		result = prime * result + (own ? 17 : 23);
		result = prime * result + (pen ? 43 : 79);
		
		return result;
	}
}
