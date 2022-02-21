package teorijske_vezbe.tv04_niti;

class Brojac {

	private int br = 0;

	public synchronized void inc() {
		br++; // Cak i ovako jednostavna operacija mora da se stiti
	}

	public synchronized int getBr() {
		return br; // Ako jedan pristup polju ide u synchronized blok
		           // onda moraju svi pristupi, pa makar to bilo i samo citanje
	}
}

class IncThread extends Thread {

	private Brojac brojac;

	public IncThread(Brojac brojac) {
		this.brojac = brojac;
	}

	@Override
	public void run() {
		for (int i = 0; i < 1_000_000; i++) {
			brojac.inc();
		}
	}
}

public class Sinhronizacija {

	public static void main(String[] args) throws InterruptedException {

		// Jedan brojac
		Brojac br = new Brojac();

		// Koji koristi 3 niti istovremeno
		Thread t1 = new IncThread(br);
		t1.start();
		Thread t2 = new IncThread(br);
		t2.start();
		Thread t3 = new IncThread(br);
		t3.start();

		// Sacekamo da niti zavrse
		t1.join();
		t2.join();
		t3.join();

		// Ispisemo rezultat (ocekujemo da bude 3 000 000)
		System.out.println(br.getBr());

	}
}
