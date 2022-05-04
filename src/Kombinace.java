/**
* Trida ktera je potomkem abstraktni tridy Student
* <p>
* Trida "Kombinace" je potomkem tridy "Student" a prepisuje (implementuje) abstraktni metodu  
* "get_dovednost()" kde pomoci funkci jiz pouzitych ve tridach "Filozof" a "Technik" navratime 
* jak znameni zverokruhu, tak zda se narodil v prestupnem roce.
* </p>* 
* 
* @author Matej Cernohous <xcerno30@vutbr.cz>
* @version 12.6
* @since 27.4.2022
*/
package prj;

public class Kombinace extends Student {
    
    // Konstruktor filozofa
    public Kombinace(String jmeno_1, String prijmeni_1,int rok)
    {
        super(jmeno_1, prijmeni_1, rok);
    }
    
    // Implementace metody get_dovednost
    @Override
    public String get_dovednost()
    {
        String str_technik;
        String str_filozof;
        
        if( !Funkce.datum_je_platne(1, 1, get_narozen()[2]) ) { str_technik = "\n\tNEPLATNY ROK NAROZENI.\n"; } 
        else if ( Funkce.rok_je_prestupny(get_narozen()[2]) ) { str_technik = "\n\tNarodil(a) jsem se v prestupnem roce.\n"; }
        else { str_technik = "\n\tNenarodil(a) jsem se v prestupnem roce.\n"; }
        str_filozof = Funkce.zvire_zverokruhu(get_narozen()[0], get_narozen()[1]);
        
        return (str_technik + "\tMoje znameni je: " + str_filozof);
    }
}
