
package prj;

import java.util.Scanner;

class PRJ {

    public static void main(String[] args)
    {
        
        boolean run = true;
        int volba = 0;
        Scanner sc = new Scanner(System.in);
        
        // databaze studentu
        Databaze studenti = new Databaze();
        
        // pomocne promenne pro sken
        char c;
        String s_1, s_2;
        int i_1, i_2, i_3, i_4;
        
        while(run)
        {
            System.out.println("\nFunkce:");
            System.out.println("1 ... Vlozeni noveho studenta");
            System.out.println("2 ... Zadat studentovi novou znamku");
            System.out.println("3 ... Propusteni studenta z univerzity");
            System.out.println("4 ... Vypis informaci o studentovi");
            System.out.println("5 ... Spustit dovednost studenta");
            System.out.println("6 ... Nastavit studentovi datum narozeni");
            
            System.out.println("\n7 ... Vypis vsech studentu");
            System.out.println("8 ... Vypis obecnych studijnich prumeru");
            System.out.println("9 ... Vypis poctu studentu");
            
            System.out.println("10 .. Ulozeni databaze do souboru");
            System.out.println("11 .. Precteni databaze ze souboru");
            
            System.out.println("12 .. Nacteni z SQL databaze");
            System.out.println("13 .. Ulozeni do SQL databaze");
            
            System.out.println("\n14 .. Ukoncit beh programu");
            
            volba = Funkce.Sken_int(sc, "\n\tVyberte pozadovanou cinnost: ");
            
            switch(volba)
            {
                // 1 ... Vlozeni noveho studenta
                case 1:
                    c = Funkce.Sken_char(sc, "\tZadejte obor (T, F nebo K): ");
                    s_1 = Funkce.Sken_str(sc, "\tZadejte krestni jmeno: ");
                    s_2 = Funkce.Sken_str(sc, "\tZadejte prijmeni: ");
                    i_1 = Funkce.Sken_int(sc, "\tZadejte rok narozeni: ");
                    if(studenti.add_student_auto_id( c, s_1, s_2, i_1))
                        System.out.format("\n\tStudent pridan s ID: %d\n", studenti.get_next_id()-1);
                    else
                        System.out.format("\n\tNevalidni obor \"%c\" nebo rok narozeni \"%d\"\n", c, i_1 );
                    break;
                
                // 2 ... Zadat studentovi novou znamku
                case 2:
                    i_1 = Funkce.Sken_int(sc, "\tZadejte id studenta: ");
                    i_2 = Funkce.Sken_int(sc, "\tZadejte znamku (1 az 5): ");
                    if (i_2<1 || i_2>5)
                        System.out.format("\n\tNevalidni znamka (%d) - znamka musi byt v intervalu <1;5>\n", i_2 );
                    else
                    {
                        studenti.pridat_znamku(i_1, i_2);
                        System.out.format("\n\tStudentovi ID: %d byla pridana znamka: %d", i_1, i_2);
                    }
                    break;
                
                // 3 ... Propusteni studenta z univerzity
                case 3:
                    i_1 = Funkce.Sken_int(sc, "\tZadejte id studenta: ");
                    studenti.odstraneni_studenta(i_1);
                    break;
                
                // 4 ... Vypis informaci o studentovi    
                case 4:
                    i_1 = Funkce.Sken_int(sc, "\tZadejte ID studenta: ");
                    studenti.print_info(i_1);
                    break;
                    
                // 5 ... Spustit dovednost studenta
                case 5:
                    i_1 = Funkce.Sken_int(sc, "\tZadejte ID studenta: ");
                    studenti.dovednost(i_1);
                    break;
                
                // 6 ... Nastavit studentovi datum narozeni
                case 6:
                    i_1 = Funkce.Sken_int(sc, "\tZadejte ID studenta: ");
                    i_2 = Funkce.Sken_int(sc, "\tZadejte den narozeni: ");
                    i_3= Funkce.Sken_int(sc, "\tZadejte mesic narozeni: ");
                    i_4 = Funkce.Sken_int(sc, "\tZadejte rok narozeni: ");
                    studenti.set_narozen(i_1, i_2, i_3, i_4, true);
                    break;
                    
                // 7 ... Vypis vsech studentu
                case 7:
                    studenti.vypis_databaze();
                    break;
                
                // 8 ... Vypis obecnych studijnich prumeru
                case 8:
                    studenti.vypis_prumeru();
                    break;
                 
                // 9 ... Vypis poctu studentu
                case 9:
                    studenti.vypis_poctu_studentu();
                    break;
                
                // 10 .. Ulozeni databaze do souboru    
                case 10:
                    s_1 = Funkce.Sken_str(sc, "\tZadejte cestu/jmeno souboru: ");
                    studenti.soubor_zapis(s_1);
                    break;
                
                // 11 .. Precteni databaze ze souboru
                case 11:
                    s_1 = Funkce.Sken_str(sc, "\tZadejte cestu/jmeno souboru: ");
                    studenti.soubor_cteni(s_1);
                    break;
                
                // 12 .. Nacteni z SQL databaze
                case 12:
                    
                    // Pripojeni k SQL databazi
                    if ( Funkce.connect())
                    {
                        System.out.println("\tDatabaze pripojena ");
                        Funkce.createTable();
                        studenti.nacist_SQL();
                    }
                    else { System.out.println("\tDatabaze neni pripojena "); }
                    
                    break;
                 
                // 13 .. Ulozeni do SQL databaze
                case 13:
                    if (Funkce.get_con() == null)
                    {
                       if ( Funkce.connect())
                        {
                            System.out.println("\tDatabaze pripojena ");
                            Funkce.createTable();
                        }
                        else{ System.out.println("\tDatabaze neni pripojena "); } 
                    }
                    studenti.ulozit_SQL();
                    break;
                
                // 14 .. Ukoncit beh programu
                default:
                    run = false;
                    if (Funkce.get_con() != null)
                            Funkce.disconnect();
            }
        }
    }
    
}
