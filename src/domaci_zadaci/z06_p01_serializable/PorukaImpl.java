package domaci_zadaci.z06_p01_serializable;

import java.io.Serializable;
import java.rmi.RemoteException;

public class PorukaImpl implements Serializable {
	
	Odgovor odg;
	String recTrenutno;
	
	public PorukaImpl(Odgovor odgovor, String recTrenutno) throws RemoteException {
		this.odg = odgovor;
		this.recTrenutno = recTrenutno;
	}

//	@Override
	public String recTrenutno() {
		return recTrenutno;
	}

//	@Override
	public Odgovor odgovor() {
		return odg;
	}
}
