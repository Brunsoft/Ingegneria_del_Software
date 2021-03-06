package univr.is.tmc.entity;

import univr.is.tmc.database.MyDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Posizione {

	private String targa;
	private String latitudine;	
	private String longitudine;
	private String velocita;

	// ============== COSTRUTTORI ====================================================

	public Posizione(String targa, String latitudine, String longitudine, String velocita) 
	{
		this.targa = targa;
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.velocita = velocita;
	}

	public Posizione(ResultSet rs) throws SQLException 
	{
		this.targa = rs.getString("targa");
		this.latitudine = rs.getString("latitudine");
		this.longitudine = rs.getString("longitudine");
		this.velocita = rs.getString("velocita");
	}

	public Posizione() {}

	// ============== METODI ==========================================================

	/**
	 * Ritorno la List Posizione dei Veicoli legati all'utente "email"
	 *
	 * @param emaul Email dell'utente in questione
	 * @return List Posizione dei veicoli legati all'utente in questione
	 */

	public static List<Posizione> getPosizioneVeicoliUtente(String email) 
	{
		List<Posizione> res = new ArrayList<>();
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "SELECT v.targa, v.latitudine, v.longitudine, v.velocita FROM Veicolo v JOIN Gestione g ON v.targa = g.targa "+
							"WHERE g.email = ?";
			Object[] params = new Object[1];
			params[0] = email;
			ResultSet rs = driver.execute(query, params);
			while (rs.next())
				res.add(new Posizione(rs));
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}
	
	/**
	 * Ritorno la List Posizione dei veicoli nel Sistema
	 *
	 * @return List Posizione dei veicoli nel sistema
	 */

	public static List<Posizione> getPosizioneVeicoli() 
	{
		List<Posizione> res = new ArrayList<>();
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "SELECT targa, latitudine, longitudine, velocita FROM Veicolo";
			ResultSet rs = driver.execute(query, null);
			while (rs.next())
				res.add(new Posizione(rs));
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	/**
	 * Modifico la posizione attuale del veicolo in questiome
	 *
	 * @param latitudine Latitudine attuale
	 * @param longitudine Longitudine attuale
	 * @param velocita Velocita' attuale del veicolo
	 * @param data Data ultima localizzazione
	 * @param ora Ora ultima localizzazione
	 * @param targa Targa veicolo in questione
	 * @return boolean che indica il successo dell'azione di UPDATE
	 */

	public static boolean modificaPosizione(double latitudine, double longitudine, int velocita, String data, String ora, String targa) 
	{
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "UPDATE Veicolo SET latitudine = ?, longitudine = ?, velocita = ?, data = ?, ora = ? "+
							"WHERE targa = ?";
			Object[] params = new Object[6];
			params[0] = latitudine;
			params[1] = longitudine;
			params[2] = velocita;
			params[3] = java.sql.Date.valueOf(data);
			params[4] = java.sql.Time.valueOf(ora);
			params[5] = targa;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
			res = false;
			System.out.println("Select faillita! " + e);
		}
		return res;
	}
	// =========== METODI SET/GET =============================================================

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}

	public String getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}

	public String getVelocita() {
		return velocita;
	}

	public void setVelocita(String velocita) {
		this.velocita = velocita;
	}
}
