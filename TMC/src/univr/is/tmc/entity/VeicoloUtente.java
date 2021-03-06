package univr.is.tmc.entity;

import univr.is.tmc.database.MyDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeicoloUtente {
	
	private Utente utente;
	private Veicolo veicolo;

	// ============== COSTRUTTORI ====================================================

	public VeicoloUtente(String nome, String cognome, String password, String email, String telefono, char privilegi, 
		String targa, String marca, String modello) 
	{
		this.utente = new Utente(nome, cognome, password, email, telefono, privilegi);
		this.veicolo = new Veicolo(targa, marca, modello);
	}

	public VeicoloUtente(ResultSet rs) throws SQLException 
	{
		this.utente = new Utente( rs );		
		this.veicolo = new Veicolo( rs );
	}

	public VeicoloUtente() { }

	// ============== METODI ==========================================================

	/**
	 * Ritorno List VeicoloUtente rappresentante tutte le associazioni Veicolo-Utente
	 *
	 * @return List VeicoloUtente rappresentante le associazioni Veicolo-Utente
	 */

	public static List<VeicoloUtente> getVeicoliUtenti() 
	{
		List<VeicoloUtente> res = new ArrayList<>();
		
		try {
			MyDriver driver = MyDriver.getInstance();

			String query = "SELECT * FROM Veicolo v JOIN Gestione g ON v.targa = g.targa JOIN Utente u ON u.email = g.email";
			ResultSet rs = driver.execute(query, null);

			while (rs.next())
				res.add(new VeicoloUtente(rs));
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	/**
	 * Ritorno List di email degli utenti che gestiscono il veicolo targato "targa"
	 *
	 * @param targa Targa veicolo in questione
	 * @return List String di email degli utenti abilitati alla gestione del veicolo in questione
	 */

    public static List<String> getUtentiVeicolo(String targa) 
	{
		List<String> res = new ArrayList<>();
		
		try {
			MyDriver driver = MyDriver.getInstance();

			String query = "SELECT g.email FROM Veicolo v JOIN Gestione g ON v.targa = g.targa "+
                            " WHERE v.targa = ?";
            Object[] params = new Object[1];
			params[0] = targa;
			ResultSet rs = driver.execute(query, params);

			while (rs.next())
				res.add(rs.getString("email"));
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	/**
	 * Inserisco una Associazione Veicolo-Utente
	 *
	 * @param targa Targa del veicolo in questione
	 * @param email Email dell'utente in questione
	 * @return boolean che indica il successo dell'azione della INSERT
	 */

	public static boolean inserisciVeicoloUtente(String targa, String email) 
	{
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "INSERT INTO Gestione ( targa, email ) VALUES ( ?, ? )";
			Object[] params = new Object[2];
			params[0] = targa;
			params[1] = email;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
			res = false;
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	/**
	 * Modifico l'associazione Veicolo-Utente
	 *
	 * @param targa Targa del veicolo in questione
	 * @param email Email dell'utente in questione
	 * @return boolean che indica il successo dell'azione dell'UPDATE
	 */

	public static boolean modificaVeicoloUtente(String targa, String email) 
	{
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "UPDATE Gestione SET targa = ?, email = ? WHERE targa = ? AND email = ?";
			Object[] params = new Object[4];
			params[0] = targa;
			params[1] = email;
			params[2] = targa;
			params[3] = email;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
			res = false;
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	/**
	 * Elimino associazione Veicolo-Utente
	 *
	 * @param targa Targa veicolo in questione
	 * @param email Email dell'utente in questione
	 * @return boolean che indica il successo dell'azione di DELETE
	 */

	public static boolean eliminaVeicoloUtente(String targa, String email) {
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "DELETE FROM Gestione WHERE targa = ? AND email = ?";
			Object[] params = new Object[2];
			params[0] = targa;
			params[1] = email;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	/**
	 * Cerca associazione Veicolo-Utente
	 *
	 * @param targa Targa veicolo in questione
	 * @param email Email utente in questione
	 * @return boolean che indica il successo dell'azione della SELECT
	 */

	public static boolean trovaVeicoloUtente(String targa, String email) {
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "SELECT * FROM Gestione WHERE targa = ? AND email = ?";
			Object[] params = new Object[2];
			params[0] = targa;
			params[1] = email;
			// Se modifica 1 riga allora è andato a buon fine
			//if (driver.update(query, params) == 1)
			ResultSet rs = driver.execute(query, params);
			if (rs.next())
				res = true;
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	// =========== METODI SET/GET =============================================================

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Veicolo getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}
}
