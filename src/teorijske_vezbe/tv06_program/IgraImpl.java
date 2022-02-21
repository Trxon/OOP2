package teorijske_vezbe.tv06_program;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IgraImpl extends UnicastRemoteObject implements Igra {

	private final int broj;
	private int zivoti;

	public IgraImpl(int broj, int zivoti) throws RemoteException {
		this.broj = broj;
		this.zivoti = zivoti;
	}

	@Override
	public Odgovor pogadjaj(int pokusaj) {
		System.out.println("Igra " + this + ": pokusaj " + pokusaj);
		if (zivoti <= 0) {
			return Odgovor.KRAJ;
		}
		zivoti--;
		if (broj > pokusaj) {
			return Odgovor.VECI;
		}
		if (broj < pokusaj) {
			return Odgovor.MANJI;
		}
		return Odgovor.POGODAK;
	}

	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
