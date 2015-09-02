package coord.database;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.sql.Connection;  



public class DbAccessor {

	// ATTRIBUTI
	Connection dbConnection;
	protected String connectionString = "jdbc:postgresql://dbserver.scienze.univr.it/dblab23";
	
	// METODI ACCESSORI
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	public String getConnectionString() {
		return connectionString;
	}
	
	// COSTRUTTORI
	public DbAccessor() {
		
	}
	
	public DbAccessor(String p_connectionString) {
		connectionString = p_connectionString;
	}
	
	
	// METODI 
	public void openConnectionToDB() {
		
		try {
			// allocazione del driver
			Class.forName ("org.postgresql.Driver").newInstance();

			// creazione della connessione
			dbConnection = DriverManager.getConnection(
					connectionString,
					"userlab23",
					"ventitreDF");
			
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("Driver not found!");
			System.out.println(cnfe.getMessage());
		}		
		catch (SQLException sqle) {
			System.out.println("Conessione al DB non riuscita!");
			System.out.println(sqle.getMessage());
		}
		catch (Exception e) {
			// gestire errore insolito
			System.out.println(e.getMessage());
		}
	} // end method
	
	public void closeConnectionToDB() {
		try {
			dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // end method
	
	// METODI PER LA RICERCA
	public ResultSet selectAll(String p_sqlSelect) {
				
		// dichiara un resultSet
		ResultSet rs = null;

		try {
			// crea lo statement associato alla query			
			PreparedStatement comando = dbConnection.prepareStatement(p_sqlSelect);
			
			// esegue la query
			rs = comando.executeQuery();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ANALISI DELL'ERRORE");
			System.out.println(e.getMessage());
			System.out.println("FINE ANALISI DELL'ERRORE");
			//e.printStackTrace();
			
		}
		
		// ritorna il risultato
		return rs;
	} // end method
	




	public ResultSet selectWithParam(String p_sqlSelect, ArrayList<Object> p_paramArray) {

		ResultSet rs = null;			// dichiara un resultSet
		try {		
			PreparedStatement comando = dbConnection.prepareStatement(p_sqlSelect);
			
			if((p_paramArray != null) && (p_paramArray.size() > 0))
				addParameterToStatement(comando, p_paramArray);

			rs = comando.executeQuery();			
			
		} catch (SQLException e) {
			System.out.println("ANALISI DELL'ERRORE");
			System.out.println(e.getMessage());
			System.out.println("FINE ANALISI DELL'ERRORE");
		}
		return rs;
	}

	public boolean execute(String p_sqlSelect, ArrayList<Object> p_paramArray) {

		try {		
			PreparedStatement comando = dbConnection.prepareStatement(p_sqlSelect);
			
			if((p_paramArray != null) && (p_paramArray.size() > 0))
				addParameterToStatement(comando, p_paramArray);

			comando.executeQuery();			
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	public ResultSet selectByPrimaryKey(String p_sqlSelect, int p_ID) {
				
		// dichiara un resultSet
		ResultSet rs = null;

		try {
			// crea lo statement associato alla query			
			PreparedStatement comando = dbConnection.prepareStatement(p_sqlSelect);
			
			// inserisce il parametro
			comando.setInt(1, p_ID);
			
			// esegue la query
			rs = comando.executeQuery();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ANALISI DELL'ERRORE");
			System.out.println(e.getMessage());
			System.out.println("FINE ANALISI DELL'ERRORE");
			//e.printStackTrace();
			
		}
		
		// ritorna il risultato
		return rs;
	} // end method
	
	public int getMaxValueOfIntKey(String p_sqlSelectMax) {
		
		// valore di ritorno
		int resInt = 0;
				
		// dichiara un resultSet
		ResultSet rs = null;

		try {
			// crea lo statement associato alla query			
			PreparedStatement comando = dbConnection.prepareStatement(p_sqlSelectMax);
			
			// esegue la query
			rs = comando.executeQuery();
			
			if (rs != null) {
				while(rs.next()) {
					resInt = rs.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ANALISI DELL'ERRORE");
			System.out.println(e.getMessage());
			System.out.println("FINE ANALISI DELL'ERRORE");
			//e.printStackTrace();
			
		}
				
		// ritorna il risultato
		return resInt;
	} // end method

	
	// METODI DML 
	public int executeDmlStatement(String p_sqlDml, ArrayList p_paramArray) {

		int resInt = 0;
		
		try {
			// crea lo statement associato alla query			
			PreparedStatement comando = dbConnection.prepareStatement(p_sqlDml);
		
			// se l'array dei parametri passati e' non nullo e neanche vuoto
			if((p_paramArray != null) && (p_paramArray.size() > 0)) {
				// inserisce i parametri passati
				this.addParameterToStatement(comando, p_paramArray);
			}
			
			// esegue la query
			 resInt = comando.executeUpdate();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ANALISI DELL'ERRORE");
			System.out.println(e.getMessage());
			System.out.println("FINE ANALISI DELL'ERRORE");
			//e.printStackTrace();
			
		}
		
		return resInt;
	}
	
	
	// METODI DI SERVIZIO PRIVATI
	private void addParameterToStatement(PreparedStatement p_statement, ArrayList <Object> p_paramArray) {
		try {
			for(int i = 0; i<p_paramArray.size(); i++) {
				// estrae  l'oggetto i-simo
				Object parametroGenerico = p_paramArray.get(i);
				
				// ne legge il tipo di dato
				Class tipoDato = parametroGenerico.getClass();
				
				// definisce cosa fare per i tipi dati previsti
				if ((tipoDato.getName()).equalsIgnoreCase("java.lang.String")) {
					p_statement.setString(i+1, parametroGenerico.toString());
				}
				
				if ((tipoDato.getName()).equalsIgnoreCase("java.util.Date")) {
					p_statement.setDate(i+1, (Date)parametroGenerico);
				}
				
				if ((tipoDato.getName()).equalsIgnoreCase("java.lang.Integer")) {
					p_statement.setInt(i+1, ((Integer)parametroGenerico).intValue());
				}
				
				if ((tipoDato.getName()).equalsIgnoreCase("java.lang.Boolean")) {
					p_statement.setBoolean(i+1, ((Boolean)parametroGenerico).booleanValue());
				}
				
			}
		}
		catch (Exception e) {
			// gestire errore
		}
	} // end method
	
} // end class
