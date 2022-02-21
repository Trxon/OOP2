package teorijske_vezbe.tv05_igra_pogadjanja;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Klijent {

	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 1234);
			System.out.println("Uspostavili smo konekciju sa serverom");
			try {

				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

				System.out.println("Kako se zoves?");
				String nadimak = stdIn.readLine();
				out.println(nadimak);

				boolean kraj = false;
				while (!kraj) {

					System.out.println("Unesi broj");
					String pokusaj = stdIn.readLine();
					out.println(pokusaj);

					String odgovor = in.readLine();
					if (odgovor == null || odgovor.equals("=")) {
						kraj = true;
					}
					System.out.println(odgovor);

				}
//				String mojRang = in.readLine();
//				System.out.println("Rang: " + mojRang);

			} finally {
				s.close();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
