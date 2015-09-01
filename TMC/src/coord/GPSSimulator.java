package coord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.lang.*;

/**
 * Questa classe simula il veicolo che trasmette la sua posizione in tempo reale con il GPS.
 */

public class GPSSimulator {

	public static void main(String args[])  throws InterruptedException  {
		GPSSimulator gps = new GPSSimulator();
	}

	// === Variables =============================================================

	private int id_furto, i = 0;
	private Calendar data = Calendar.getInstance();
	private String dataFurto = "", oraFurto = "";
	private String anno = "", mese = "", giorno = "";
	private String ora = "", minuti = "", secondi = "";

	

	double listLAT[] = {45.404597, 45.405372, 45.404958, 45.404762, 45.405418, 45.406013, 45.406359, 45.406835, 45.407234, 45.407837, 45.407693, 45.406922, 45.404632, 45.403186, 45.401796};

	double listLNG[] = {10.997138, 10.997439, 10.998329, 10.999219, 10.998801, 10.998426, 10.998200, 10.998430, 10.999203, 11.000834, 11.001022, 11.001774, 11.003965, 11.005323, 11.006741};

	// === Properties ==-==========================================================

	private final String user = "userlab23";
	private final String passwd = "ventitreDF";
	private final String url = "jdbc:postgresql://dbserver.scienze.univr.it/dblab23";
	private final String driver = "org.postgresql.Driver";

	// === Constructor ===========================================================

	public GPSSimulator()  throws InterruptedException {
		anno = ""+data.get(Calendar.YEAR);								// ANNO

		if ( (data.get(Calendar.MONTH)+1) < 10 )					// MESE
			mese = "0" + (data.get(Calendar.MONTH)+1);
		else 
			mese = "" + (data.get(Calendar.MONTH)+1);

		if ( data.get(Calendar.DAY_OF_MONTH) < 10 )					// GIORNO
			giorno = "0" + data.get(Calendar.DAY_OF_MONTH);
		else 
			giorno = "" + data.get(Calendar.DAY_OF_MONTH);

		if ( data.get(Calendar.HOUR_OF_DAY) < 10 )					// ORA
			ora = "0" + data.get(Calendar.HOUR_OF_DAY);
		else
			ora = "" + data.get(Calendar.HOUR_OF_DAY);

		if ( data.get(Calendar.MINUTE) < 10 )						// MINUTI
			minuti = "0" + data.get(Calendar.MINUTE);
		else
			minuti = "" + data.get(Calendar.MINUTE);

		if ( data.get(Calendar.SECOND) < 10 )						// SECONDI
			secondi = "0" + data.get(Calendar.SECOND);
		else
			secondi = "" + data.get(Calendar.SECOND);

		dataFurto = anno + "-" + mese + "-" + giorno;

		oraFurto = ora + ":" + minuti + ":" + secondi;
		inserisciFurto();
		ricavaID();
		trasmetti();
	}

	// === Methods ===============================================================

	public boolean inserisciFurto() throws InterruptedException {

		try {
			// create a psql database connection
			System.out.println(dataFurto+ "\n"+ oraFurto);

			
			Class.forName(driver);
			Connection conn = DriverManager.getConnection( url, user, passwd );
			String query = " insert into Furto (data, ora)" + " values (?, ?) ";
		 	// create the psql insert preparedstatement
		    PreparedStatement preparedStmt = conn.prepareStatement(query);
		    preparedStmt.setString (1, dataFurto);
		    preparedStmt.setString (2, oraFurto);
		    // execute the preparedstatement
		  	preparedStmt.execute();
		   	conn.close();
		}
		catch (Exception e){
			return false;
		}
		return true;
	}

	public boolean ricavaID() {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

    	try {
            Class.forName(driver);
            con = DriverManager.getConnection( url, user, passwd );
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Furto where data = '"+dataFurto+"' AND ora = '"+oraFurto+"' ");
            while( rs.next() ) {
        		id_furto = rs.getInt(1);
				System.out.println(id_furto);
			}
		}
		catch(Exception e ) {
			return false;
		}
		finally {
			try {
				con.close();
			} catch( SQLException sqle1 ) {
				sqle1.printStackTrace();
			}
		}
		return true;
	}

	public void trasmetti() throws InterruptedException {
		while (i < listLAT.length) {
			inserisciPosizione();
			i++;
			Thread.sleep(5000);
		}
	}

	public boolean inserisciPosizione() {

		String ora = "", minuti = "", secondi = "";

		if ( data.get(Calendar.HOUR_OF_DAY) < 10 )					// ORA
			ora = "0" + data.get(Calendar.HOUR_OF_DAY);
		else
			ora = "" + data.get(Calendar.HOUR_OF_DAY);

		if ( data.get(Calendar.MINUTE) < 10 )						// MINUTI
			minuti = "0" + data.get(Calendar.MINUTE);
		else
			minuti = "" + data.get(Calendar.MINUTE);

		if ( data.get(Calendar.SECOND) < 10 )						// SECONDI
			secondi = "0" + data.get(Calendar.SECOND);
		else
			secondi = "" + data.get(Calendar.SECOND);

		String oraCorrente = ora + ":" + minuti + ":" + secondi;

		try {
			// create a psql database connection
			Class.forName(driver);
			Connection conn = DriverManager.getConnection( url, user, passwd );
			String query = " insert into Posizione (id_furto, latitudine, longitudine, ora)" + " values (?, ?, ?, ?)";
		 	// create the psql insert preparedstatement
		    PreparedStatement preparedStmt = conn.prepareStatement(query);
		    preparedStmt.setInt (1, id_furto);
		    preparedStmt.setString (2, ""+listLAT[i]);
		    preparedStmt.setString (3, ""+listLNG[i]);
		    preparedStmt.setString (4, oraCorrente);
	 		// execute the preparedstatement
		  	preparedStmt.execute();
		   	conn.close();
		}
		catch (Exception e){
			return false;
		}
		return true;
	}
}
