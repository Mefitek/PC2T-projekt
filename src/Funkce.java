
package prj;

import java.util.Scanner;

// vsechny knihovny pro praci s SQL
import java.sql.*;
// Pro praci s SQL databazi pouzito SQLite - musi byt naimportovan balicek SQLiteJDBC package

public class Funkce {
    
    static boolean rok_je_prestupny(int rok)
    {
        if ((rok % 4) == 0)
        {
            if((rok % 100) == 0)
            {
                return ((rok % 400) == 0);
            }
             return true;
        }
        return false;
    }
    
    static boolean rok_je_platny(int rok)
    {
      return !(rok>2022 || rok<1903);
    }
    
    
    static boolean datum_je_platne(int den, int mesic, int rok)
    {
        if(!rok_je_platny(rok)) { return false; }
        if(rok>2022 || rok<1903) { return false; }
        if(mesic>12 || mesic<1) { return false; }
        if(den>31 || den<1) { return false; }
        
        if(den == 31 && (mesic==2 || mesic==4 || mesic==6 || mesic==9 || mesic==11)) { return false; }
        if(den == 30 && mesic == 2) { return false; }
        if(den == 29 && mesic == 2 && !Funkce.rok_je_prestupny(rok)) { return false; }
        
        return true;
    }
    
    static String zvire_zverokruhu(int den, int mesic)
    {
        if(!datum_je_platne(den, mesic, 2000)) return "NEPLATNE DATUM";
        
        switch(mesic)
        {
            // breaky vynechat diky returnu
            case 1:
                if(den<=20) return "Kozoroh";
                else return "Vodnar";
            case 2:
                if(den<=20) return "Vodnar";
                else return "Ryby";
            case 3:
                if(den<=20) return "Ryby";
                else return "Beran";
            case 4:
                if(den<=20) return "Beran";
                else return "Byk";
            case 5:
                if(den<=21) return "Byk";
                else return "Blizenci";
            case 6:
                if(den<=21) return "Blizenci";
                else return "Rak";
            case 7:
                if(den<=22) return "Rak";
                else return "Lev";
            case 8:
                if(den<=22) return "Lev";
                else return "Panna";
            case 9:
                if(den<=22) return "Panna";
                else return "Vahy";
            case 10:
                if(den<=23) return "Vahy";
                else return "Stir";
            case 11:
                if(den<=22) return "Stir";
                else return "Strelec";
            case 12:
                if(den<=21) return "Strelec";
                else return "Kozoroh";
            default:
                return "NEPLATNE DATUM"; // nemuze nastat diky fci datum_je_platne, ale kompilator to nevi
          }
    }
    
    static int Sken_int(Scanner sc, String zprava)
    {
        int i;
        while(true)
        {
            System.out.print(zprava);
            if (sc.hasNextInt())
                break;
            sc.next();
        }
        i = sc.nextInt();
        sc.nextLine(); // aby nezustal /n znak v bufferu
        return i;
    }
    
    static String Sken_str(Scanner sc, String zprava)
    {
        while(true)
        {
            System.out.print(zprava);
            if (sc.hasNextLine()) break;
            sc.next();
        }
        return sc.nextLine();
    }
    
    static char Sken_char(Scanner sc, String zprava)
    {
        char c;
        while(true)
        {
            System.out.print(zprava);
            if (sc.hasNextLine()) break;
            sc.next();
        }
        c = sc.next().charAt(0);
        sc.nextLine(); // aby nezustal /n znak v bufferu
        return c;
    }
   
    
    // metody pro praci s SQL databazi
    
    private static String sql;
    private static Connection con = null;
    
    public static String get_sql() { return sql; }
    public static Connection get_con() { return con; }
    
    // pripojeni k SQL databazi
    public static boolean connect()
    {
        sql = "jdbc:sqlite:Databaze.db";
	try
        {
            con = DriverManager.getConnection(sql);
	}
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
	}
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
	}
	return true;
    }

    // odpojeni od databaze
    public static void disconnect()
    {
        if (con != null)
        {
            try
            {
                con.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

   // vytvoreni tabulky
   public static Boolean createTable()
   {
	if ( con == null)
            return false;

	sql = "CREATE TABLE IF NOT EXISTS data (" + "id int PRIMARY KEY, " + "obor char, "
              + "jmeno varchar(30), " + "prijmeni varchar(30), " + "den int NOT NULL, " + "mesic int, "
              + "rok int, " + "suma_znamek int, " +  "pocet_znamek int" + ");";
	try
        {
            Statement stmnt = con.createStatement();
            stmnt.execute(sql);
            return true;
	}
        catch (SQLException e)
        {
            e.printStackTrace();
        }
	return false;
    }
   
    // odstraneni zaznamu
    public static void delete(int id)
    {
         sql = "DELETE FROM data WHERE id = ?";

         try
         {
             PreparedStatement pstmt = con.prepareStatement(sql);
             pstmt.setInt(1, id);
             // execute the delete statement
             pstmt.executeUpdate();

         }
         catch (SQLException e)
         {
             System.out.println(e.getMessage());
         }
     }

    public static void deletAll()
    {
        sql = "SELECT id FROM data";
        try
        {
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next())
            {
                delete(rs.getInt("id"));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
   
   
    // vlozeni zaznamu
    public static void insertRecord( int id, char obor, String jmeno, String prijmeni, int den, int mesic, int rok, int suma_znamek, int pocet_znamek)
    {
      sql = "INSERT INTO data(id,obor,jmeno,prijmeni,den,mesic,rok,suma_znamek,pocet_znamek) VALUES(?,?,?,?,?,?,?,?,?)";
      try
      {
            PreparedStatement pstmt = con.prepareStatement(sql); 
            pstmt.setInt(1, id);
            pstmt.setString(2, String.valueOf(obor));
            pstmt.setString(3, jmeno);
            pstmt.setString(4, prijmeni);
            pstmt.setInt(5, den);
            pstmt.setInt(6, mesic);
            pstmt.setInt(7, rok);
            pstmt.setInt(8, suma_znamek);
            pstmt.setInt(9, pocet_znamek);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
     
}
