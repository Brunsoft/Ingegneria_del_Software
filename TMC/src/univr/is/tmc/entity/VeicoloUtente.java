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


	public static List<VeicoloUtente> getVeicoliUtenti() 
	{
		List<VeicoloUtente> res = new ArrayList<>();
		
		try {
			MyDriver driver = MyDriver.getInstance();

			String query = "SELECT * FROM Veicolo v JOIN Gestione g ON v.targa = g.targa JOIN Utente u ON u.mail = g.mail";
			ResultSet rs = driver.execute(query, null);

			while (rs.next())
				res.add(new VeicoloUtente(rs));
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	public static boolean inserisciVeicoloUtente(String targa, String mail) 
	{
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "INSERT INTO Gestione ( targa, mail ) VALUES ( ?, ? )";
			Object[] params = new Object[2];
			params[0] = targa;
			params[1] = mail;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
			res = false;
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	public static boolean modificaVeicoloUtente(String targa, String mail) 
	{
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "UPDATE Gestione SET targa = ?, mail = ? WHERE targa = ? AND mail = ?";
			Object[] params = new Object[4];
			params[0] = targa;
			params[1] = mail;
			params[2] = targa;
			params[3] = mail;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
			res = false;
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	public static boolean eliminaVeicoloUtente(String targa, String mail) {
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "DELETE FROM Gestione WHERE targa = ? AND mail = ?";
			Object[] params = new Object[2];
			params[0] = targa;
			params[1] = mail;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	public static boolean trovaVeicoloUtente(String targa, String mail) {
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "SELECT * FROM Gestione WHERE targa = ? AND mail = ?";
			Object[] params = new Object[2];
			params[0] = targa;
			params[1] = mail;
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
