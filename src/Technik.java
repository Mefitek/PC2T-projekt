/**
* Trida ktera je potomkem abstraktni tridy Student
* <p>
* Trida "Technik" je potomkem tridy "Student" a prepisuje (implementuje) abstraktni metodu 
* "get_dovednost()" kde pomoci funkce "rok_je_prestupny()" ze tridy "Funkce" navrati 
* zda se student narodil v prestupnem roce (+ osetreno neplatne datum narozeni funkci 
* "datum_je_platne()" z tridy "Funkce").
* </p>* 
* 
* @author Matej Cernohous <xcerno30@vutbr.cz>
* @version 12.6
* @since 27.4.2022
*/
package prj;

public class Technik extends Student{
    
    // Konstruktor technika
    public Technik(String jmeno_1, String prijmeni_1, int rok)
    {
        super(jmeno_1, prijmeni_1, rok);
    }
    
    // Implementace metody get_dovednost
    @Override
    public String get_dovednost()
    {
        if(!Funkce.datum_je_platne(1, 1, get_narozen()[2])) { return "\n\tNEPLATNY ROK NAROZENI"; } // otestovani platnosti roku narozeni
        
        if(Funkce.rok_je_prestupny(get_narozen()[2])) { return "\n\tNarodil jsem se v prestupnem roce"; }
        
        return "\n\tNenarodil jsem se v prestupnem roce";
    }
}
