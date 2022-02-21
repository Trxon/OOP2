package teorijske_vezbe.tv05_program;

import static java.lang.System.out;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Glavni prozor mrezne igre IksOks.
 * 
 * @author Dejan Mitrovic
 * @author Ivan Pribela
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	private static final int NUM_BUTTONS = 9;

	// Server (za serversku stranu) i konekcija sa protivnikom
	private Server server;
	private Socket opponent;

	// Za slanje podataka protivniku
	private PrintWriter socketOut;
	private JButton[] buttons;
	private boolean myMove; // Da li sam ja na redu?
	private ImageIcon iconIks;
	private ImageIcon iconOks;
	private boolean iks; // Sa li sam ja iks?
	private boolean closing;

	public MainFrame() {
		iconIks = new ImageIcon(getClass().getResource("iks.png"));
		iconOks = new ImageIcon(getClass().getResource("oks.png"));
		initGui();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// Kada korisnik zatvori prozor, "cistimo" resurse
				closing = true;
				if (server != null) {
					server.close();
					server = null;
				}
				if (opponent != null) {
					try {
						opponent.close();
					} catch (IOException ex) {
					}
					opponent = null;
				}
			}
		});
	}

	private void initGui() {

		JPanel top = new JPanel();
		final JButton btnServer = new JButton("Server");
		top.add(btnServer);
		final JButton btnClient = new JButton("Client");
		top.add(btnClient);

		btnServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String val = JOptionPane.showInputDialog(MainFrame.this, "Server port:", "6060");
				if (val == null || val.length() == 0)
					return;
				try {
					int port = Integer.parseInt(val);
					// Pokreni server na ovom portu
					server = new Server(MainFrame.this, port);
					server.start();
					getContentPane().removeAll();
					add(new JLabel("Waiting for a client..."));
					validate(); // Kada menjamo komponente u prozoru,
					repaint();  // moramo pozvati ove metode da bi se izmene videle
				} catch (Exception ex) {
					out.println("Error while starting the server: " + ex.getMessage());
					JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String val = JOptionPane.showInputDialog(MainFrame.this, "Server address and port:", "localhost:6060");
				if ((val == null) || (val.length() == 0)) {
					return;
				}
				try {
					int n = val.indexOf(':');
					String host = val.substring(0, n);
					int port = Integer.parseInt(val.substring(n + 1));
					// Konektuj se na zadatu adresu i otvori komunikaciju
					Socket client = new Socket(host, port);
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					PrintWriter out = new PrintWriter(client.getOutputStream(), true);
					// Klijent je uvek iks i igra prvi
					iks = true;
					myMove = true;
					onConnectionEstablished(client, in, out);
				} catch (Exception ex) {
					out.println("Error while starting the server: " + ex.getMessage());
					JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Error",	JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		add(top, BorderLayout.NORTH);

	}

	// Metod koji se poziva kada ja kliknem na dugme
	@Override
	public void actionPerformed(ActionEvent e) {

		// Nisam ja na redu
		if (!myMove) {
			return;
		}

		// Koje dugme je kliknuto?
		JButton button = (JButton) e.getSource();
		String move = button.getActionCommand();

		// Disable-ujemo dugme i postavljamo ikonicu
		button.setEnabled(false);
		button.setIcon(iks ? iconIks : iconOks);

		// Potez saljemo preko mreze
		socketOut.println(move);

		// Da li je neko pobedio?
		checkState();

		// Protivnik je sada na redu
		myMove = false;

	}

	// Metod koji se poziva kada protivnik klikne na dugme
	public void onRemoteMove(String move) {
		// Nije on na redu
		if (myMove) {
			return;
		}

		// Pronadji dugme na koje je protivnik kliknuo
		for (JButton button : buttons) {
			if (button.getActionCommand().equals(move)) {

				// Disable-ujemo dugme i postavljamo ikonicu
				button.setEnabled(false);
				button.setIcon(iks ? iconOks : iconIks);
				
				// Da li je neko pobedio?
				checkState();

				// Ja sam sada na redu
				myMove = true;

				// Ne moramo vise traziti dugme
				break;

			}
		}

	}

	public void onConnectionEstablished(Socket client, BufferedReader in, PrintWriter out) {

		this.opponent = client;
		this.socketOut = out;
		new Reader(this, in).start();
		setTitle("IksOks - " + (iks ? "Iks" : "Oks"));

		getContentPane().removeAll();
		setLayout(new GridLayout(3, 3, 4, 4));
		setBackground(Color.BLACK);

		// Inicijalizacija dugmica
		buttons = new JButton[NUM_BUTTONS];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				JButton button = new JButton();
				// Za svako dugme cuvamo koordinate koje cemo kasnije slati preko mreze
				button.setActionCommand(i + ":" + j);
				button.addActionListener(this);
				add(button);
				buttons[i * 3 + j] = button;
			}

		validate();
		repaint();

	}

	public void onConnectionLost() {
		if (!closing) {
			JOptionPane.showMessageDialog(this, "Connection lost, the game will now exit");
			dispose();
		}
	}

	private void checkState() {
		// Prvi red
		if (equal(0, 0, 0, 1, 0, 2))
			checkWinner(0, 0);
		// Drugi red
		else if (equal(1, 0, 1, 1, 1, 2))
			checkWinner(1, 0);
		// Treci red
		else if (equal(2, 0, 2, 1, 2, 2))
			checkWinner(2, 0);
		// Prva kolina
		else if (equal(0, 0, 1, 0, 2, 0))
			checkWinner(0, 0);
		// Druga kolina
		else if (equal(0, 1, 1, 1, 2, 1))
			checkWinner(0, 1);
		// Treca kolina
		else if (equal(0, 2, 1, 2, 2, 2))
			checkWinner(0, 2);
		// Glavna dijagonala
		else if (equal(0, 0, 1, 1, 2, 2))
			checkWinner(0, 0);
		// Sporedna dijagonala
		else if (equal(2, 0, 1, 1, 0, 2))
			checkWinner(2, 0);
	}

	private boolean equal(int i, int j, int m, int n, int p, int q) {
		JButton a = buttons[i * 3 + j];
		JButton b = buttons[m * 3 + n];
		JButton c = buttons[p * 3 + q];
		return (a.getIcon() != null) && (a.getIcon() == b.getIcon()) && (b.getIcon() == c.getIcon());
	}

	private void checkWinner(int i, int j) {
		JButton a = buttons[i * 3 + j];
		boolean won = (iks && (a.getIcon() == iconIks)) || (!iks && (a.getIcon() == iconOks));
		String str = won ? "You won! :)" : "You lost! :(";
		closing = true;
		JOptionPane.showMessageDialog(this, str + "\nThe game will now exit.");
		dispose();
	}

	public static void main(String[] args) {
		JFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(256, 256);
		frame.setResizable(false);
		frame.setTitle("IksOks");
		frame.setVisible(true);
	}
}
