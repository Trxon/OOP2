package vezba.sandbox;

import java.io.PrintWriter;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException {
		
		PrintWriter pw = new PrintWriter(System.out, true);
		
		pw.print("Linija 1");
		System.out.println("Linija 2");
		
		pw.print("Linija 3\n");
		pw.print("Linija 5");
		System.out.println("Linija 4");
		pw.flush();
	}
}
