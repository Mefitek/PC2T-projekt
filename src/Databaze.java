/**
* Trida ktera dynamicky spravuje databazi studentu
* <p>
* Trida vyuziva HastMapy pro ukladani jednotlivych studentu. Obsahuje metody pro 
* pridavani a mazani studentu, pridani/astaveni znamky/znamek, nastaveni data narozeni, 
* vypisu informaci o studentovi/vsech studentech, spusteni studentovy dovednosti, 
* ulozeni cele databaze do souboru a SQL databaze - splneni vsech bodu zadani (trida "Zadani").
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

// Importy - Hash mapa, ArrayLilstu (a jeho trideni)
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

// Importy - cteni a zapis do souboru (pres Buffer)
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

// Importy - vyjimky
import java.io.IOException;

// Importy - SQL
import java.sql.*;

public class Databaze
{
    
    private Map<Integer,Student> prvky_databaze; // pro klic potreba pouzit obalovy typ Integer
    private static int next_id; // promenna indikujici dalsi volne id
    
    // konstruktor
    public Databaze()
    {
        prvky_databaze = new HashMap<Integer,Student>();
    }
    
    // getter a setter pro: next_id
    public static int get_next_id()
    {
        return next_id;
    }
    
    public static void set_next_id(int next_id_1)
    {
        if(next_id_1>next_id)
            next_id = next_id_1;
    }
    

    // metoda pro pridani studenta do databaze (se zadanym ID)
    // pouziti funkce put() pro vlozeni prvku do HashMapy
    public boolean add_student(char skupina, int id, String jmeno, String prijmeni, int rok)
    {
        if(student_existuje(id)) { return false; } // zamezeni duplicitnimu ID
        if ( !Funkce.rok_je_platny(rok)) { return false; } // kontrola validniho roku narozeni
        
        switch (skupina)
        {
            case 'T':
            case 't':
                prvky_databaze.put(id, new Technik(jmeno, prijmeni, rok)); // pouziti ID jako klice
                break;
            case 'F':
            case 'f':
            case 'H':
            case 'h':
                prvky_databaze.put(id, new Filozof(jmeno, prijmeni, rok));
                break;
            case 'K':
            case 'k':
                prvky_databaze.put(id, new Kombinace(jmeno, prijmeni, rok));
                break;
            default:
                return false;
        }
        (prvky_databaze.get(id)).set_id(id); // nastaveni parametru ID Studenta aby se shodovalo s klicem
        return true;
    }
    
    // pridani studenta (jmeno, prijmeni, rok narozeni) s automaticky prirazenym ID
    // vrati false pokud byla spatne zvolena skupina
    public boolean add_student_auto_id(char skupina, String jmeno, String prijmeni, int rok)
    {
        if(add_student(skupina, next_id, jmeno, prijmeni, rok))
        {
            next_id++; // inkrementace dalsiho volneho id
            return true;
        }
        return false;
    }
    
    
    

    // pomocna funkce ktera pouze vola funkci containsKey nad Hashmapou (pro prehlednost kodu)
    public boolean student_existuje(int id)
    {
        return (prvky_databaze.containsKey(id));
    }
        
    // zadan studentovi novou znamku (id, znamka)
    public void pridat_znamku(int id, int znamka)
    {
        if(student_existuje(id))
        {
            (prvky_databaze.get(id)).add_znamka(znamka);
        }
    }
    
    // pomocna funkce pro zadani sumy a poctu znamek studenta
    public void nastavit_znamky(int id, int suma_znamek, int pocet_znamek)
    {
        if(student_existuje(id))
        {
            (prvky_databaze.get(id)).set_znamky(suma_znamek, pocet_znamek);
        }
    }
    
    // nastavit studentovi datum narozeni, parametr vypis: zda chceme vypsat status zmeny narozeni
    public void set_narozen(int id, int den, int mesic, int rok, boolean vypis)
    {
        if(student_existuje(id))
        {
            if(Funkce.datum_je_platne(den, mesic, rok))
            {
              (prvky_databaze.get(id)).set_narozen(den, mesic, rok);
              if(vypis)
                  System.out.format("\tDatum narozeni studenta ID: %d nastaveno na %d.%d.%d\n", id, den, mesic, rok);
            }
            else
            {
                if(vypis)
                    System.out.format("\tDatum narozeni %d.%d.%d neni platne\n", den, mesic, rok);
            }
        }
        else
        {
            if(vypis)
                System.out.format("\tStudent ID: %d neexistuje\n", id);
        }
    }
    
    // vymazani studenta (id)
    public void odstraneni_studenta(int id)
    {
        if(student_existuje(id))
        {
            prvky_databaze.remove(id);
            System.out.format("\tStudent ID: %d odstranen z databaze\n", id);
        }
        else
            System.out.format("\tStudent ID: %d neexistuje\n", id);
    }
    
    // nalezeni studenta
    public Student get_student(int id)
    {
        if(student_existuje(id))
        {
            return prvky_databaze.get(id);
        }
        return null; 
    }
    
    //vypis informaci studenta - ID, jmeno, prijmeni, rok narozeni, prumer (vstupni parametr student)
    public void print_student(Student s)
    {
            int id = s.get_id();
            String jmeno = s.get_jmeno()[0];
            String prijmeni = s.get_jmeno()[1];
            int den = s.get_narozen()[0];
            int mesic = s.get_narozen()[1];
            int rok = s.get_narozen()[2];
            double prumer = s.get_prumer();
            System.out.format("\t%5d |  %-17s|  %-12s  |  %2d.%2d.%4d  |  %-1.1f\n", id, prijmeni, jmeno, den, mesic, rok, prumer);
    }
    
    // vypis informaci studenta (vstupni parametr id studenta)
    public void print_info(int id)
    {
       System.out.println("\n\t  ID  |      PRIJMENI     |      JMENO     |    NAROZEN   | PRUMER");
       Student info = get_student(id); 
       if(info != null)
       {
        print_student(info); 
       }
       else
       {
           System.out.println("\n\tStudent ID:" +id+ " neexistuje");
       }
    }
    
    // spusti dovednost studenta (id)
    public void dovednost(int id)
    {
       if(student_existuje(id))
       {
            System.out.println((prvky_databaze.get(id)).get_dovednost()); 
       }
       else
       {
            System.out.format("\tStudent ID: %d neexistuje\n", id); 
       }
            
    }
              
    // abecedne vypis studentu () - podle prijmeni V JEDNOLIVYCH SKUPINACH - ID, jmeno, prijmeni, rok narozeni, prumer
    // prevedeni do ArrayListu
    public void vypis_databaze()
    {
        List<Student> arr_list = new ArrayList<>(prvky_databaze.values());
        
        // kontrola zda je list prazdny
        if(arr_list.isEmpty())
        {
            System.out.println("\tSeznam studentu je PRAZDNY");
        }
        else
        {
            Collections.sort(arr_list, Comparator.comparing(Student::get_prijmeni));
            
            System.out.println("\n\tTechnicky obor:");
            System.out.println("\t  ID  |      PRIJMENI     |      JMENO     |    NAROZEN   | PRUMER");
            System.out.println("\t      |                   |                |              |");

            for (Student s : arr_list)
            {
                if(s instanceof Technik)
                    print_student(s);
            }

            System.out.println("\n\tHumanitni obor:");
            System.out.println("\t  ID  |      PRIJMENI     |      JMENO     |    NAROZEN   | PRUMER");
            System.out.println("\t      |                   |                |              |");
            for (Student s : arr_list)
            {
                if(s instanceof Filozof)
                    print_student(s);
            }

            System.out.println("\n\tKombinovany obor:");
            System.out.println("\t  ID  |      PRIJMENI     |      JMENO     |    NAROZEN   | PRUMER");
            System.out.println("\t      |                   |                |              |");
            for (Student s : arr_list)
            {
                if(s instanceof Kombinace)
                    print_student(s);
            }
        }
    }
    
    // vypis obecneho studijniho prumeru Techniku a Filozofu
    public void vypis_prumeru()
    {
        // vytvoreni Setu klicu pro prochazeni databaze
        Set <Integer> klice = prvky_databaze.keySet();
        double prumery_t = 0; 
        double prumery_f = 0;
        int pocet_t = 0;
        int pocet_f = 0;
        double help;
           
        for(Integer klic:klice)
        {
            if ( (prvky_databaze.get(klic)) instanceof Technik)
            {
                help = (prvky_databaze.get(klic)).get_prumer();
                prumery_t += help;
                if(help != 0.0) // pokud je student klasifikovany
                    pocet_t++;
            }
            
            else if ( (prvky_databaze.get(klic)) instanceof Filozof)
            {
                help = (prvky_databaze.get(klic)).get_prumer();
                prumery_f += help;
                if(help != 0.0) // pokud je student klasifikovany
                    pocet_f++;
            }
        }
        
        System.out.print("\n\tObecny studijni prumer:\n");
        System.out.format("\t\tTechnicky obor: %1.2f\n", (prumery_t/(double)pocet_t));
        System.out.format("\t\tHumanitni obor: %1.2f\n", (prumery_f/(double)pocet_f));
    }
    
    // vypis poctu studentu v jednotlivych skupinach
    public void vypis_poctu_studentu()
    {
        Set <Integer> klice = prvky_databaze.keySet();
        int t = 0;
        int f = 0;
        int k = 0;
        
        for(Integer klic:klice)
        {
            if ( (prvky_databaze.get(klic)) instanceof Technik) t++;
            else if ( (prvky_databaze.get(klic)) instanceof Filozof) f++;
            else k++;
        }
        
        System.out.print("\n\tPocet studentu v jednotlivych skupinach:\n");
        System.out.format("\t\tTechnicky obor:\t%d\n", t);
        System.out.format("\t\tHumanitni obor:\t%d\n", f);
        System.out.format("\t\tKombinovany obor:\t%d\n", k);
    }
    
    // soubory
    public void soubor_zapis(String soubor)
    {
        FileWriter fw = null;
        BufferedWriter out = null;
        
        try
        {
           fw = new FileWriter(soubor, false);
           out = new BufferedWriter(fw); 
           
           // docasne promenne pro zapis atributu do souboru
           char obor;
           int id;
           String[] jmeno; // format: [jmeno][prijmeni]
           int[] narozen; // format: [den][mesic][rok]
           int[] znamky; // format: [suma znamek][pocet znamek]
           
           // Set klicu pro prochazeni databaze
           Set <Integer> klice = prvky_databaze.keySet();
           
           // prochazeni vsech prvku databaze
           for(Integer klic:klice)
           {
              if ( (prvky_databaze.get(klic)) instanceof Technik)
                  obor = 'T';
              else if ( (prvky_databaze.get(klic)) instanceof Filozof)
                  obor = 'F';
              else obor = 'K';
             
              id = (prvky_databaze.get(klic)).get_id();
              jmeno = (prvky_databaze.get(klic)).get_jmeno();
              narozen = (prvky_databaze.get(klic)).get_narozen();
              znamky = (prvky_databaze.get(klic)).get_znamky();
              
              out.write(obor +"_"+ id +"_"+ jmeno[0] +"_"+ jmeno[1] +"_");
              out.write(narozen[0] +"_"+ narozen[1] +"_"+ narozen[2] +"_"+ znamky[0] +"_"+ znamky[1] +"\n");
              // kvuli \n bude na konci soubory prazdny radek
              // to je ale osetreno ve funkci pro cteni souboru
           }
        }
        catch(IOException e) { System.out.print("\n\tVYJIMKA (OTEVRENI souboru - zapis): " + e.toString() + "\n");}
        catch(Exception e) { System.out.print("\n\tVYJIMKA: " + e.toString() + "\n");}
        
        try
        {
            out.close();
            fw.close();
        }
        catch (IOException e) { System.out.println("\n\tVYJIMKA (ZAVRENI souboru - zapis): " + e.toString() + "\n");  }
        catch (Exception e) { System.out.print("\n\tVYJIMKA: " + e.toString() + "\n");}
    }
    
    public void soubor_cteni(String soubor)
    {
        FileReader fr = null;
        BufferedReader in = null;
        
        try
        {
            fr = new FileReader(soubor);
            in = new BufferedReader(fr);
            
            // docasne pomocne promenne pro cteni textu ze souboru
            String radek = " ";
            String[] casti_radku;
            char obor;
            int id;
            String[] jmeno = new String[2];
            int[] narozen = new int[3];
            int[] znamky = new int[2];
            
            // pomocne promenne pro vypis poctu nactenych studentu a chybmych radku
            int nacteno_studentu = 0;
            int aktualni_radek = 0;
            
            // reset databaze pred ctenim ze souboru
            prvky_databaze = new HashMap<Integer,Student>();
            
            // while(true) osetren breakem
            while (true)
            {
               radek = in.readLine();
               if(radek == null)
               {
                   System.out.println("\tZe souboru \"" +soubor+ "\" bylo nacteno " +nacteno_studentu+ " studentu");
                   break;
               }
               
               aktualni_radek++;
               // rozdeleni nacteneho radku na casti metodou split()
               casti_radku = radek.split("_");
               
               // kontrola zda radek vyhovuje pozadovanemu formatu
               if(casti_radku.length == 9) // obor, id, jmeno, prijmeni, narozeni(3), suma znamek, pocet znamek = 9 casti
               {
                  // ulozeni casti radku do pomocnych promennych (+ prevedeni na spravny datovy typ)
                  obor = casti_radku[0].charAt(0);
                  id = Integer.parseInt(casti_radku[1]);
                  jmeno[0] = casti_radku[2];
                  jmeno[1] = casti_radku[3];
                  narozen[0] = Integer.parseInt(casti_radku[4]);
                  narozen[1] = Integer.parseInt(casti_radku[5]);
                  narozen[2] = Integer.parseInt(casti_radku[6]);
                  znamky[0] = Integer.parseInt(casti_radku[7]);
                  znamky[1] = Integer.parseInt(casti_radku[8]);
                  
                  // pridani studenta databaze jiz drive vytvorenou funkci 
                  if (add_student( obor, id, jmeno[0], jmeno[1], narozen[2]))
                  {
                    // aktualizace dalsiho volneho id
                    if(id >= next_id) { next_id = id + 1; }
                    
                    // nastaveni zbylych parametru studenta
                    (prvky_databaze.get(id)).set_narozen(narozen[0], narozen[1], narozen[2]);
                    (prvky_databaze.get(id)).set_znamky(znamky[0], znamky[1]);
                    
                    nacteno_studentu++;
                  }
               }
               else {  System.out.println("\tRadek cislo " + aktualni_radek + " neodpovida formatu, nebyl nacten"); }
            }    
        }
        catch(IOException e) { System.out.print("\n\tVYJIMKA (OTEVRENI souboru - cteni): " + e.toString() + "\n");}
        catch(Exception e) { System.out.print("\n\tVYJIMKA: " + e.toString() + "\n");}
        
        try
        {
            in.close();
            fr.close();
        }
        catch (IOException e) { System.out.println("\n\tVYJIMKA (ZAVRENI souboru - cteni): " + e.toString() + "\n");  }
        catch (Exception e) { System.out.print("\n\tVYJIMKA: " + e.toString() + "\n");}
    }
    
    
    // SQL metody nad databazi
    
    // vlozeni do SQL databaze
    public void ulozit_SQL()
    {
        // vycisteni SQL databaze pred ulozenim nove databaze
        Funkce.deletAll();
        // docasne promenne pro zapis atributu do databaze
        char obor;
        int id;
        String[] jmeno; // format: [jmeno][prijmeni]
        int[] narozen; // format: [den][mesic][rok]
        int[] znamky; // format: [suma znamek][pocet znamek]
           
        // Set klicu pro prochazeni databaze
        Set <Integer> klice = prvky_databaze.keySet();
        
        int pocet_studentu = 0;
        
        // prochazeni vsech prvku databaze
        for(Integer klic:klice)
        {
            if ( (prvky_databaze.get(klic)) instanceof Technik)
                obor = 'T';
            else if ( (prvky_databaze.get(klic)) instanceof Filozof)
                obor = 'F';
            else obor = 'K';
             
            id = (prvky_databaze.get(klic)).get_id();
            jmeno = (prvky_databaze.get(klic)).get_jmeno();
            narozen = (prvky_databaze.get(klic)).get_narozen();
            znamky = (prvky_databaze.get(klic)).get_znamky();
            
            // zapis do SQL databaze
            Funkce.insertRecord( id, obor, jmeno[0], jmeno[1], narozen[0], narozen[1], narozen[2], znamky[0], znamky[1]);
            pocet_studentu++;
        }
        System.out.println("\tUlozena SQL databaze obsahujici " +pocet_studentu+ " studentu");
    }
    
    // nacteni z SQL databaze
    public void nacist_SQL()
    {
        // reset databaze pred ctenim z databaze
        prvky_databaze = new HashMap<Integer,Student>();
        
        String sql = "SELECT id,obor,jmeno,prijmeni,den,mesic,rok,suma_znamek,pocet_znamek FROM data";
        Connection con = Funkce.get_con();
        try
        {
            java.sql.Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            
            int nacteno_studentu = 0;
            while (rs.next())
            {
                // kontrola zda se podarilo studenta pridat
                if( add_student( rs.getString("obor").charAt(0), rs.getInt("id"), rs.getString("jmeno"), rs.getString("prijmeni"), rs.getInt("rok") ) )
                {
                    nacteno_studentu++;
                    // nastaveni narozeni
                    set_narozen( rs.getInt("id"), rs.getInt("den"), rs.getInt("mesic"), rs.getInt("rok"), false);
                    // public void nastavit_znamky(int id, int suma_znamek, int pocet_znamek)
                    nastavit_znamky( rs.getInt("id"), rs.getInt("suma_znamek"), rs.getInt("pocet_znamek"));
                    // aktualizace next_id pro pripadne pridavani dalsich studentu
                    if( rs.getInt("id") >= next_id)
                    {
                        next_id = rs.getInt("id")+1;
                    }
                }
                else
                {
                    System.out.println("\tStudenta id" + rs.getInt("id") + " se nepodarilo pridat do databaze");
                }
            }
            System.out.println("\tz SQL databaze nacteno " +nacteno_studentu+ " studentu");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
