package teorijske_vezbe.tv05_program;

import java.awt.EventQueue;
import java.io.BufferedReader;

/**
 * Pomocna nit koja ucitava poteze protivnika i salje ih glavnom frejmu.
 * 
 * @author Dejan Mitrovic
 * @author Ivan Pribela
 */
public class Reader extends Thread {

	private BufferedReader in;
	private MainFrame frame;

	public Reader(MainFrame frame, BufferedReader in) {
		this.frame = frame;
		this.in = in;
	}

	@Override
	public void run() {
		try {
			while (!interrupted()) {
				final String line = in.readLine();
				if (line == null) { // Protivnik je prekinuo igru
					break;
				}

				// Ovo moramo izvrsiti na EDT-u
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						frame.onRemoteMove(line);
					}
				});

			}
		} catch (Exception ex) {
			// Nista
		} finally {

			// Javljamo glavnom frejmu da je protivnik prekinuo igru
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					frame.onConnectionLost();
				}
			});

		}
	}
}
