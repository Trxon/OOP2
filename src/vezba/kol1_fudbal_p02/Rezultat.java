package vezba.kol1_fudbal_p02;

public class Rezultat {

	public final int timA;
	public final int timB;
	
	public Rezultat(int timA, int timB) {
		this.timA = timA;
		this.timB = timB;
	}
	
	@Override
	public String toString() {
		return timA + "-" + timB;
	}
}
