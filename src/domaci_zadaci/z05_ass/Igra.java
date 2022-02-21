package domaci_zadaci.z05_ass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Igra extends Thread {

	// Standardna podesavanja za novu igru
	private static final int ZIVOTI = 10;
	private static final String[] RECI = {
			"avion",
			"vetar",
			"jabuka",
			"kupus",
			"lisica",
			"olovka",
			"papir",
			"plavo",
			"ptica",
			"telefon",
	};

	// Komunikacija sa igracem
	private final Socket socket;
	private final BufferedReader in;
	private final PrintWriter out;

	// Stanje igre
	private final String rec;
	private final StringBuilder otkriveno;
	private int zivoti;

	// Kreira novu igru sa standardnim podesavanjima
	public Igra(Socket socket) throws IOException {
		this(socket, RECI[(int) (RECI.length * Math.random())], ZIVOTI);
	}

	// Kreira novu igru sa zeljenim podesavanjima
	public Igra(Socket socket, String rec, int zivoti) throws IOException {

		// Otvorimo tokove za komunikaciju sa igracem
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

		// Postavimo pocetno stanje igre
		this.rec = rec.toUpperCase();
		this.otkriveno = new StringBuilder(rec.length());
		for (int i = 0; i < rec.length(); i++) {
			this.otkriveno.append('-');
		}
		this.zivoti = zivoti;

	}

	@Override
	public void run() {

		System.out.println("Zapoceta nova igra: " + this);

		// Glavna petlja igre se odvija dok igrac ima zivota ili dok ne pogodi rec
		try {
			boolean pogodak;
			do {
				out.println(otkriveno.toString());
				out.println(Integer.toString(zivoti));
				char slovo = in.readLine().charAt(0);
				pogodak = pogadjanje(slovo);
			} while (zivoti > 0 && !pogodak);
			out.println(otkriveno.toString());
			out.println(Integer.toString(zivoti));

		// Takodje, ako se prekine konekcija izlazimo iz petlje sa izuzetkom
		} catch (IOException e) {
			System.out.println("Igra " + this + ": prekinula se konekcija");
			e.printStackTrace();

		// Svakako zatvaramo tokove i soket bez obzira da li je igra zavrsena regularno
		// ili je pukla konekcija
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				System.err.println("Neuspelo zatvaranje konekcije");
			}
		}

		System.out.println("Zavrsena igra: " + this);

	}

	// Logika igre
	public boolean pogadjanje(char slovo) {
		slovo = Character.toUpperCase(slovo);
		System.out.println("Igra " + this + ": pokusaj " + slovo);
		boolean pogodak = true;
		boolean promasaj = true;
		for (int i = 0; i < rec.length(); i++) {
			if (rec.charAt(i) == slovo) {
				promasaj = false;
				otkriveno.setCharAt(i, slovo);
			}
			if (otkriveno.charAt(i) == '-') {
				pogodak = false;
			}
		}
		if (promasaj) {
			zivoti--;
		}
		return pogodak;
	}

	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
