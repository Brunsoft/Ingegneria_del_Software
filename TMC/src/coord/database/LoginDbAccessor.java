package coord.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class LoginDbAccessor extends DbAccessor {

	// COSTRUTTORI
	public LoginDbAccessor() {
		super();
	}
	
	public LoginDbAccessor(String p_connectionString) {
		super(p_connectionString);
	}
	
	public char Login(String nome, String pass) {
		char result = '\u0000';
		ArrayList<Object> parametri = new ArrayList<Object>();
		parametri.add(nome);
		parametri.add(pass);
		String query = "SELECT tipo FROM Utente WHERE nome = ? and pwd = ? ";
		ResultSet rs = this.selectWithParam(query, parametri);

		try {
			if(rs.next())
				result = rs.getString(1).charAt(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	// METODI CHE CONVERTONO IL RISULTATO IN ARRAYLIST
	/**
	 * SELEZIONE GLOBALE
	 
	public ArrayList<Persona> selectAllPersona() {
		// stringa SQL per l'interrogazione
		String sql_selectAll = "SELECT * FROM PERSONA";
		
		// alloca la lista vuota per gli oggetti Persona
		ArrayList<Persona> listaElenco = new ArrayList<Persona>();
		
		// carico i dati in memoria entro il ResultSet
		ResultSet rs = this.selectAll(sql_selectAll);
		
		try {
			while (rs.next()) {
				// crea oggetto con dentro i dati del record corrente
				Persona tempElenco =
					new Persona(
							rs.getInt("ID"), 
							rs.getString("Cognome"), 
							rs.getString("Nome"), 
							rs.getString("Codice"));
				
				// aggiunge oggetto creato in fondo alla lista
				listaElenco.add(tempElenco);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaElenco;
	} // end method
	
	/** 
	 * LETTURA MASSIMO VALORE DELLA CHIAVE
	 * @return il massimo della chiave
	 
	public int getMaxID() {
		
		// imposta la query
		String sql_selectMaxID = "SELECT MAX(ID) FROM PERSONA";

		// esegue la query
		return this.getMaxValueOfIntKey(sql_selectMaxID);
		
	} // end method

	/**
	 * SELEZIONE IN BASE ALLA CHIAVE
	 * @param p_ID
	 * @return Persona selezionata
	 
	public Persona selectByIdFromElenco(int p_ID) {
		
		// imposta la query con il nome del campo usato come chiave
		String sql_selectByID = "SELECT * FROM PERSONA WHERE (ID = ?)";  
		
		// risultato da tornare, inizialnente persona vuota
		Persona resPersona = new Persona();
		
		ResultSet rs = this.selectByPrimaryKey(sql_selectByID, p_ID);
		
		try {
			while (rs.next()) {
				// crea oggetto con dentro i dati del record corrente
				resPersona =
					new Persona(
							rs.getInt("ID"), 
							rs.getString("Cognome"), 
							rs.getString("Nome"), 
							rs.getString("Codice"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resPersona;

	} // end method

	/**
	 * SELEZIONA UNA LISTA DI PERSONE IN BASE AI VALORI DI RICERCA
	 * @param p_nomeChiave
	 * @param p_valoreChiave
	 * @return
	 
	public ArrayList<Persona> selectByKeyFromElenco(String p_nomeChiave, String p_valoreChiave) {
		
		// imposta la query con il nome del campo usato come chiave
		String sql_selectByKey = "SELECT * FROM PERSONA WHERE ("+p_nomeChiave+" = ?)";
		
		// alloca la lista vuota per gli oggetti Persona
		ArrayList<Persona> listaElenco = new ArrayList<Persona>();
		
		// crea la lista dei parametri generici
		ArrayList listaParametri = new ArrayList();
		
		// vi aggiunge il valore della chiave
		listaParametri.add(p_valoreChiave);
		
		// carica i dati in memoria entro il ResultSet
		ResultSet rs = this.selectWithParam(sql_selectByKey, listaParametri);

		// spacchetta il resultset costruendo gli oggetti entita' con i dati caricati
		try {
			while (rs.next()) {
				// crea oggetto con dentro i dati del record corrente
				Persona tempElenco =
					new Persona(
							rs.getInt("ID"), 
							rs.getString("Cognome"), 
							rs.getString("Nome"), 
							rs.getString("Codice"));
				
				// aggiunge oggetto creato in fondo alla lista
				listaElenco.add(tempElenco);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaElenco;
	} // end method

	// METODI DML
	/**
	 * INSERIMENTO DI NUOVO RECORD
	 
	public int insertNewRecord(Persona p_elenco) {
		// imposta la query con il nome del campo usato come chiave
		String sql_insert = 
			"INSERT INTO PERSONA (ID, COGNOME, NOME, CODICE) VALUES (?,?,?,?)";
		
		int resInt = 0;
		
		
		
		try {
			// crea la lista di parametri da passare
			ArrayList listaParametri = new ArrayList();
			
			// inserisce i parametri
			listaParametri.add(new Integer(p_elenco.getID()));
			listaParametri.add(p_elenco.getCognome());
			listaParametri.add(p_elenco.getNome());
			listaParametri.add(p_elenco.getCodice());
			
			
			// esegue la query
			 resInt = this.executeDmlStatement(sql_insert, listaParametri);			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ANALISI DELL'ERRORE");
			System.out.println(e.getMessage());
			System.out.println("FINE ANALISI DELL'ERRORE");
			//e.printStackTrace();
			
		}
		
		return resInt;
	} // end method */
	
} // END CLASS
