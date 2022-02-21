package samostalno.kol1_gspns_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program {
	

	public static void main(String[] args) throws IOException {
		
		Lines lines = new Lines();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Linija za koju se prikazuje raspored : ");
		String id = in.readLine().trim();
		lines.showDepartures(id);
	}
}
