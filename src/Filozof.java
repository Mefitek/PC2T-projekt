/**
* Trida ktera je potomkem abstraktni tridy Student
* <p>
* Trida "Filozof" je potomkem tridy "Student" a prepisuje (implementuje) abstraktni metodu 
* "get_dovednost()" kde pomoci funkce "zvire_zverokruhu()" ze tridy "Funkce" navrati 
* znameni zverokruhu, ve kterem se student narodil (jako String).
* </p>* 
* 
* @author Matej Cernohous <xcerno30@vutbr.cz>
* @version 12.6
* @since 27.4.2022
*/
package prj;

public class Filozof extends Student{
   
    // Konstruktor Filozofa
    public Filozof(String jmeno_1, String prijmeni_1,int rok)
    {
        super(jmeno_1, prijmeni_1, rok);
    }
    
    // Implementace metody get_dovednost
    @Override
    public String get_dovednost()
    {
        return ("\n\tNarodil(a) jsem se ve znameni: " + Funkce.zvire_zverokruhu( get_narozen()[0], get_narozen()[1]));
    }
}
