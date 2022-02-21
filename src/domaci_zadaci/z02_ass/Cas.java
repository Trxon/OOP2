package domaci_zadaci.z02_ass;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Cas implements Comparable<Cas> {

	private Dan dan;
	private LocalTime vremePoc;
	private LocalTime vremeKraj;
	private String predmet;
	private String nastavnik;
	private String tip;
	private String sala;

	public Dan getDan() {
		return dan;
	}

	public void setDan(Dan newValue) {
		if (newValue == null) {
			return;
		}
		if (dan != null) {
			throw new IllegalStateException();
		}
		dan = newValue;
	}

	public void setDan(String newValue) {
		if (newValue ==  null) {
			return;
		}
		setDan(Dan.fromEn(newValue));
	}

	public LocalTime getVremePoc() {
		return vremePoc;
	}

	public void setVremePoc(LocalTime newValue) {
		if (newValue == null) {
			return;
		}
		if (vremePoc != null) {
			throw new IllegalStateException();
		}
		vremePoc = newValue;
	}

	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

	public void setVremePoc(String newValue) {
		if (newValue ==  null) {
			return;
		}
		setVremePoc(LocalTime.parse(newValue, timeFormatter));
	}

	public LocalTime getVremeKraj() {
		return vremeKraj;
	}

	public void setVremeKraj(LocalTime newValue) {
		if (newValue == null) {
			return;
		}
		if (vremeKraj != null) {
			throw new IllegalStateException();
		}
		vremeKraj = newValue;
	}

	public void setVremeKraj(String newValue) {
		if (newValue ==  null) {
			return;
		}
		setVremeKraj(LocalTime.parse(newValue, timeFormatter));
	}

	public String getPredmet() {
		return predmet;
	}

	public void setPredmet(String newValue) {
		if (newValue == null) {
			return;
		}
		if (predmet != null) {
			throw new IllegalStateException();
		}
		predmet = newValue;
	}

	public String getNastavnik() {
		return nastavnik;
	}

	public void setNastavnik(String newValue) {
		if (newValue == null) {
			return;
		}
		if (nastavnik != null) {
			throw new IllegalStateException();
		}
		nastavnik = newValue;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String newValue) {
		if (newValue == null) {
			return;
		}
		if (tip != null) {
			throw new IllegalStateException();
		}
		tip = newValue;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String newValue) {
		if (newValue == null) {
			return;
		}
		if (sala != null) {
			throw new IllegalStateException();
		}
		sala = newValue;
	}

	public boolean isComplete() {
		return dan != null
			&& vremePoc != null
			&& vremeKraj != null
			&& predmet != null
			&& nastavnik != null
			&& tip != null
			&& sala != null;
	}

	@Override
	public int hashCode() {
		if (!isComplete()) {
			return 0;
		}
		final int prime = 31;
		int result = 1;
		result = prime * result + dan.hashCode();
		result = prime * result + vremePoc.hashCode();
		result = prime * result + vremeKraj.hashCode();
		result = prime * result + predmet.hashCode();
		result = prime * result + nastavnik.hashCode();
		result = prime * result + tip.hashCode();
		result = prime * result + sala.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Cas that = (Cas) obj;
		if (!Objects.equals(this.dan,  that.dan)) {
			return false;
		}
		if (!Objects.equals(this.vremePoc,  that.vremePoc)) {
			return false;
		}
		if (!Objects.equals(this.vremeKraj,  that.vremeKraj)) {
			return false;
		}
		if (!Objects.equals(this.predmet,  that.predmet)) {
			return false;
		}
		if (!Objects.equals(this.nastavnik,  that.nastavnik)) {
			return false;
		}
		if (!Objects.equals(this.tip,  that.tip)) {
			return false;
		}
		if (!Objects.equals(this.sala,  that.sala)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Cas that) {
		if (this.vremePoc == null && that.vremePoc == null) {
			return 0;
		}
		if (this.vremePoc == null) {
			return 1;
		}
		if (that.vremePoc == null) {
			return -1;
		}
		return this.vremePoc.compareTo(that.vremePoc);
	}

	@Override
	public String toString() {
		return String.format("%3s %5s-%5s   %-40s %-20s %-10s   (%s)", dan, vremePoc, vremeKraj, predmet, nastavnik, sala, tip);
	}
}
