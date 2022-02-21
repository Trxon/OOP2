package domaci_zadaci.z04_p00;

/*
 * Dat je tok osoba u vidu metoda Osobe::osobeStream. Uz pomocu da-
 * tog metoda, lambda izraza i operacija nad tokovima podataka imp-
 * lementirati sledece metode i pozvati ih iz glavnog programa. Ko-
 * ristiti iskljucivo terminalnu operaciju .collect().
 *
 * List<Osoba> opadajuceSortiraniPoDatumuRodjenja()
 * Set<Osoba> saVecimPrimanjimaOd(int)
 * double prosecnaPrimanjaUMestu(String)
 * Map<String, Double> prosecnaPrimanjaPoMestu() 
 * 		// Kljuc mape je ime mesta
 * Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad() 
 * 		// Kljuc mape je ime mesta
 * Map<Integer, List<Osoba>> razvrstaniPoBrojuDece() 
 * 		// Kljuc mape je broj dece
 * Map<String, Map<Integer, List<Osoba>>> poMestuIBrojuDece()  
 * 		// Kljuc glavne mape je ime mesta, a unutrasnje broj dece
 * String gradSaNajviseDoseljenika()
 * String gradSaNajviseStarosedelaca()
 * String najbogatijiGrad()
 * String najpopularnijeMuskoIme()
 * String najpopularnijeZenskoIme()
 * 
 * U glavnom programu ispisati sledece podatke koriscenjem redukci-
 * ja tokova podataka (terminalna operacija .reduce()).
 *
 * Najbogatija osoba
 * -----------------
 * 01234567 Pera Peric
 *
 * Muska imena
 * -----------
 * Aca, Arsa, Bora, Vlada, ...
 *
 * Godina kada je rodjena najstarija osoba
 * ---------------------------------------
 * 1940
 *
 * U glavnom programu ispisati sledece podatke na zadati nacin. Ra-
 * cunanje podataka realizovati pomocu operacije .collect(). Na po-
 * cetku podatke skupiti u mapu a potom iz mape pustiti novi tok p-
 * odataka i formatirati ispis pomocu metoda String::format. Obrat-
 * iti paznju na format ispisa, velika, mala slova i broj decimala. 
 *
 * Grad       | Broj ljudi | Prosecna primanja
 * -----------+------------+------------------
 * NOVI SAD   |        234 |          49867.56
 * BEOGRAD    |        322 |          50072.33
 * KRAGUJEVAC |        225 |          49215.45
 * ...
 *
 * Ime        | Br roditelja | Broj muske dece | Broj zenske dece
 * -----------+--------------+-----------------+-----------------
 * Pera       |          234 |             356 |              297
 * Mika       |          322 |             442 |              443
 * Jelena     |          225 |             295 |              312
 * ...
 *
 * Godine | Primanja
 * zivota | Najnize   | Najvise   | Ukupno     | Prosek    | Devijacija 
 * -------+-----------+-----------+------------+-----------+-----------
 * ...
 * 22     |  12600.00 | 102400.00 | 7652300.00 | 503476.12 |     132.66
 * 23     |  29600.00 |  99700.00 | 6843500.00 | 496456.26 |      98.32
 * 24     |  23400.00 | 123400.00 | 8134800.00 | 512388.43 |     253.01
 * ...  
 */

public class OsobeProgram2 {
	
	public static void main(String[] args) {

	}
}

