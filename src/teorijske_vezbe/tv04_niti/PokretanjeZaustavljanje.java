package teorijske_vezbe.tv04_niti;

class MojaPrvaNit extends Thread {

	@Override
	public void run() {
		for (int i = 0; i < 100 && !interrupted(); i += 2) { // Dodali smo proveru zastavice prekida, kako bismo lepo zavrsili rad ako neko to zatrazi
			System.out.println(getName() + ": " + i);
		}
	}
}

class Nesto { /* ... */ } 

// U Javi nema visestrukog nasledjivanja pa moramo ovako preko interfejsa
class MojaDrugaNit extends Nesto implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 100 && !Thread.interrupted(); i += 2) {
			System.out.println(Thread.currentThread().getName() + ": " + i);
		}
	}
}

public class PokretanjeZaustavljanje {
	
	public static void main(String[] args) throws InterruptedException {

		MojaPrvaNit t = new MojaPrvaNit(); // Kreiranje
		t.start(); // Pokretanje

		MojaDrugaNit r = new MojaDrugaNit();
		Thread t2 = new Thread(r);
		t2.start();

		for (int i = 1; i < 100; i +=2) {
			System.out.println(i);
		}

		t.interrupt(); // Molba za prestanak rada, sama nit odlucuje kada i da li ce zavrsiti rad
		t.join(); // Cekamo da nit zaista zavrsi rad

		System.out.println("Kraj");

	}
}
