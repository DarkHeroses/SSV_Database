package dbcontroller;

import java.sql.*;

public class DBController {
    private static DBController instance = new DBController();

    static DBController getInstance() {
        return instance;
    }

    private Connection connection;
    private Statement stmt;


    private DBController() {
        connection = null;
        try {
            Class.forName(Params.JDBC);
            connection = DriverManager.getConnection(Params.DB_ConnectionName);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");

    }

    public ResultSet loadPerson() {
        try {
            System.out.println("Loading Person");
            stmt = connection.createStatement();
            String query = "SELECT * FROM Person";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Loaded Person successfully");
            return rs;
        } catch (Exception e) {
            // stuff
        }
        return null;
    }

    public static void main( String args[] )
	  {
	    DBController dbController = DBController.getInstance();

	    Statement stmt = null;
	    try {
	      stmt = dbController.connection.createStatement();
	      String sql = "DROP TABLE IF EXISTS Person;"
	      		+ "CREATE TABLE Person " +
	                   "(ID INTEGER PRIMARY KEY     AUTOINCREMENT, " +
	                   " Name           TEXT    NOT NULL, " + 
	                   " Geburtstag     DATE,             " + 
	                   " Strasse        CHAR(50),         " + 
	                   " Ort            CHAR(50),         " +
	                   " Telefon        CHAR(15),         " +
	                   " Mobil          CHAR(15),         " +
	                   " Mail           CHAR(50))         "; 
	      stmt.executeUpdate(sql);
	      sql =   "DROP TABLE IF EXISTS Fachwart;"
	      		+ "CREATE TABLE Fachwart "
	    		  + "(Fachbereich CHAR(30) PRIMARY KEY NOT NULL, " 
	    		  + " Fachwart INT, "
	    		  + " FOREIGN KEY (Fachwart) REFERENCES Person(ID))";
	      stmt.executeUpdate(sql);
	      sql =   "DROP TABLE IF EXISTS Vereinsadresse;"
	      		+ "CREATE TABLE Vereinsadresse "
	    		  + "(Verein CHAR(50) PRIMARY KEY NOT NULL, "
	    		  + " Fachbereich CHAR(30),"
	    		  + " Vorsitzender INT,"
	    		  + " alternative_Mail CHAR(50),"
	    		  + " Jugendwart CHAR(80),"
	    		  + " Jugendwart_Mail CHAR(50),"
	    		  + " Homepage CHAR(50),"
	    		  + " Mitglieder INT,"
	    		  + "FOREIGN KEY (Fachbereich) REFERENCES Fachwart(Fachbereich),"
	    		  + "FOREIGN KEY (Vorsitzender) REFERENCES Person(ID))";
	      stmt.executeUpdate(sql);
	      
	      // Fill Tables with data
	      sql = "INSERT INTO Person(Name, Geburtstag, Strasse, Ort, Telefon, Mobil, Mail) VALUES"
	      		+ "('Herbert', '1990-01-30 00:00:00.000', 'Lindenweg 170', '41768 Fundlingen', '0213 - 234586', null, 'herbert@schule-fundlingen.fd');";
	      stmt.executeUpdate(sql);
	      sql = "INSERT INTO Person(Name, Geburtstag, Strasse, Ort, Telefon, Mobil, Mail) VALUES"
		      		+ "('Groeni', '1970-01-31 02:00:00.000', 'Lindenweg 170a', '41768 Fundlingen', '0213 - 234586', null, 'herb@schule-fundlingen.fd');";
		      stmt.executeUpdate(sql);
		      
		  // Ausgabe
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM Person;" );
	      while ( rs.next() ) {
	         int id = rs.getInt("ID");
	         String  name = rs.getString("Name");
	         Date age  = rs.getDate("Geburtstag");
	         String  address = rs.getString("Strasse");
	         String ort = rs.getString("Ort");
	         String tel = rs.getString("Telefon");
	         String mobil = rs.getString("Mobil");
	         String mail = rs.getString("Mail");
	         System.out.println( "ID = " + id );
	         System.out.println( "Name = " + name );
	         System.out.println( "Geburtstag = " + age );
	         System.out.println( "Strasse = " + address );
	         System.out.println( "Ort = " + ort );
	         System.out.println( "Telefon = " + tel );
	         System.out.println( "Mobil = " + mobil );
	         System.out.println( "Mail = " + mail );
	         System.out.println();
	      }
	      rs.close();
	      
	      stmt.close();
	      dbController.connection.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	  }
}
