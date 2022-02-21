package teorijske_vezbe.tv05_igra_pogadjanja;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Igra extends Thread {

	private final Socket socket;
	private final BufferedReader in;
	private final PrintWriter out;
	private final RangLista rl;
	private final int broj;
	private String nadimak;
	private int brPokusaja;

	public Igra(Socket klijent, RangLista rl) throws IOException {
		this.socket = klijent;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		this.rl = rl;
		this.broj = (int) (1.0 + Math.random() * 100.0);
		this.brPokusaja = 0;
	}

	public int getBrPokusaja() {
		return brPokusaja;
	}

	@Override
	public void run() {
		try {

			nadimak = in.readLine();
			System.out.println("Zapoceta igra za: " + nadimak);

			String linija = in.readLine();
			boolean kraj = false;
			while (linija != null && !kraj) {
				System.out.println("Primljeno '" + linija + "' od " + nadimak);
				try {

					int pokusaj = Integer.parseInt(linija);
					brPokusaja++;

					if (pokusaj == broj) {
						out.println("=");
						kraj = true;
//						int rang;
//						synchronized (rl) {
//							rang = rl.dodaj(this);
//						}
//						out.println(rang);

					} else if (pokusaj < broj) {
						out.println(">");

					} else {
						out.println("<");
					}

				} catch (NumberFormatException e) {
					out.println("! " + e.getMessage());
				}
				linija = in.readLine();
			}

			System.out.println("Igra za " + nadimak + " je zavrsena");
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
