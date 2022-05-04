/**
* Abstraktni trida popisujici vlastnosti studenta a praci s nimi
* <p>
* Trida je abstraktni, nebot je pouzita pouze jako predek pro dalsi tridy 
* (Filozof, Technik, Kombinace), ktere prepisuji abstraktni funkci get_dovednost().
* </p>
* 
* <p>
* Vlastnosti studenta jsou: id, jmeno, prijmeni, datum narozeni a znamky. Promenne 
* stejneho typu ktere spolu uzce souvisely byly ulozeny do pole stejneho datoveho typu. 
* Pro id byl zvolen datovy typ int, pro jmeno+prijmeni 2prvkove pole Stringu, pro 
* datum narozeni 3prvkove pole intu, pro znamky 2prvkove pole intu (kde 1. prvek 
* znazornuje sumu vsech znamek, 2. prvek pocet vsech znamek - potreba pro vypocet prumeru).
* </p>
* 
* <p>
* Dale obsahuje trida standardni gettery a settery privatnich promennych a navic funkci 
* "get_prumer" ktera pole "znamky" vypocte prumer studenta a funkci "add_znamka" ktera do 
* pole "znamky" prida studentovi znamku.
* </p>
* 
* @author Matej Cernohous <xcerno30@vutbr.cz>
* @version 12.6
* @since 27.4.2022
*/

package prj;

public abstract class Student {
    
    // Vlastnosti studenta
    private int id;
    private String[] jmeno = new String[2]; // format: [jmeno][prijmeni]
    private int[] narozen = new int[3]; // format: [den][mesic][rok]
    private int[] znamky = new int[2];  // format: [suma znamek][pocet znamek]
    
    // Konstruktor studenta
    public Student(String jmeno_1, String prijmeni, int rok)
    {
        jmeno[0] = jmeno_1;
        jmeno[1] = prijmeni;
        narozen[2] = rok;
    }
    
    // Gettery
    public int get_id() { return id; }
    public String[] get_jmeno() { return jmeno; }
    // potreba ziskat POUZE prijmeni pro Comparator.comparing() (metoda vypis_databaze() tridy Databaze)
    public String get_prijmeni() { return jmeno[1]; }
    public int[] get_narozen() { return narozen; }
    public int[] get_znamky() { return znamky; }
    public double get_prumer()
    {
        if( znamky[1] <= 0 ) {  return 0.0; }
        return ((double)znamky[0]/(double)znamky[1]); 
    }
    
    // Settery
    public void set_id (int id_1) { id = id_1; }
    public void set_jmeno(String jmeno_1) { jmeno[0] = jmeno_1; }
    public void set_prijmeni(String prijmeni) { jmeno[1] = prijmeni; }
    public void set_narozen(int den, int mesic, int rok)
    {
        narozen[0] = den;
        narozen[1] = mesic;
        narozen[2] = rok;
    }
    public void set_znamky (int suma_znamek, int pocet_znamek)
    {
        znamky[0] = suma_znamek;
        znamky[1] = pocet_znamek;
    }
    
    public void add_znamka(int znamka)
    {
        if(znamka<1 || znamka>5){}
        else
        {
         znamky[0]+=znamka; // pricteni znamky do sumy znamek
         znamky[1]++; // zvyseni poctu znamek
        }
    }
    
    // Abstraktni metoda get_dovednost
    public abstract String get_dovednost();
}
