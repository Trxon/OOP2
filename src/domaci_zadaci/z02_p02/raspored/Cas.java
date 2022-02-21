package domaci_zadaci.z02_p02.raspored;

import java.util.Objects;

import domaci_zadaci.z02_p02.time.Dani;
import domaci_zadaci.z02_p02.time.Time;

public class Cas implements Comparable<Cas> {
	
	
	private Time pocetak;
	private Time kraj;
	private Dani dan;
	private String predmet;
	private String nastavnik;
	private String tip;
	private String sala;
	
	
	public Cas() { }
	
	
	public Cas(Time pocetak, Time kraj, Dani dan, String predmet, String nastavnik, String tip, String sala) {
		this.pocetak 	= pocetak;
		this.kraj 		= kraj;
		this.dan		= dan;
		this.predmet 	= predmet;
		this.nastavnik 	= nastavnik;
		this.tip 		= tip;
		this.sala 		= sala;
	}


	public Time pocetak() 		{ return pocetak; 	}
	public Time kraj() 			{ return kraj; 		}
	public Dani dan() 			{ return dan; 		}
	public String predmet() 	{ return predmet; 	}
	public String nastavnik() 	{ return nastavnik; }
	public String tip() 		{ return tip; 		}
	public String sala() 		{ return sala; 		}
	
	
	public void setPocetak(Time t)		{ this.pocetak = t; 	}
	public void setKraj(Time t)			{ this.kraj = t; 		}
	public void setDan(Dani d)			{ this.dan = d;			}
	public void setPredmet(String p) 	{ this.predmet = p; 	}
	public void setNastavnik(String n) 	{ this.nastavnik = n; 	}
	public void setTip(String t) 		{ this.tip = t; 		}
	public void setSala(String s) 		{ this.sala = s; 		}


	public String toString() {
		return String.format("%50s %30s %5s %20s %3s [ %02d:%02d - %02d:%02d ]", 
				predmet, nastavnik, tip, sala, dan, pocetak.h(), pocetak.m(), kraj.h(), kraj.m());
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (this.getClass() != o.getClass())
			return false;
		
		Cas i = (Cas) o;
		
		if (!Objects.equals(pocetak(), i.pocetak()) 	||
			!Objects.equals(kraj(), i.kraj())			||
			!Objects.equals(dan(), i.dan())				||
			!Objects.equals(predmet(), i.predmet())		||
			!Objects.equals(nastavnik(), i.nastavnik())	||
			!Objects.equals(tip(), i.tip())				||
			!Objects.equals(sala(), i.sala())			 )
			return false;
		else
			return true;
	}
	
	@Override
	public int hashCode() {
		
		return 	7  * toMinutes(pocetak()) 	+ 
				11 * toMinutes(kraj()) 		+ 
				13 * dan().ordinal() 		+
				17 * predmet().hashCode() 	+ 
				19 * nastavnik().hashCode() + 
				23 * tip().hashCode()		+
				29 * sala().hashCode()		;
	}


	private int toMinutes(Time t) {
		return t.h() * 60 + t.m();
	}


	@Override
	public int compareTo(Cas c) {
		return pocetak().compareTo(c.pocetak());
	}
}
