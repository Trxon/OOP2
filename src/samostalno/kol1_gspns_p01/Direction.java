package samostalno.kol1_gspns_p01;

public enum Direction {

	A("A"),
	B("B");
	
	private String dir;
	
	private Direction(String dir) {
		this.dir = dir.toUpperCase();
	}
	
	public static Direction fromString(String dir) {
		
		for (Direction d : Direction.values())
			if (d.dir.equalsIgnoreCase(dir))
				return d;
		
		return null;
	}
	
	@Override
	public String toString() {
		return dir;
	}
}
