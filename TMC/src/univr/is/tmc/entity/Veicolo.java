package univr.is.tmc.entity;

import univr.is.tmc.database.MyDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Veicolo {
	
	private String targa;
	private String marca;
	private String modello;
	private int limite;
	private String data;
	private String ora;
	private String latitudine;
	private String longitudine;

	// ============== COSTRUTTORI ====================================================

	public Veicolo(String targa, String marca, String modello) 
	{
		this.targa = targa;
		this.marca = marca;
		this.modello = modello;
	}

	public Veicolo(ResultSet rs) throws SQLException 
	{
		this.targa = rs.getString("targa");
		this.marca = rs.getString("marca");
		this.modello = rs.getString("modello");
		this.limite = rs.getInt("limite");
		this.data = rs.getString("data");
		this.ora = rs.getString("ora");
		this.latitudine = ""+rs.getBigDecimal("latitudine");
		this.longitudine = ""+rs.getBigDecimal("longitudine");
	}

	public Veicolo() { }

	// ============== METODI ==========================================================

	public static Veicolo getVeicoloData(String targa) 
	{
		Veicolo res = null;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "SELECT * FROM Veicolo WHERE targa = ?";
			Object[] params = new Object[1];
			params[0] = targa;
			ResultSet rs = driver.execute(query, params);
			if (rs.next())
				res = new Veicolo(rs);
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	public static List<Veicolo> getVeicoliUser(String mail) 
	{
		List<Veicolo> res = new ArrayList<>();
		char privilegi = new Utente().getUserData(mail).getPrivilegi();
		String query = "";
		
		try {
			MyDriver driver = MyDriver.getInstance();
			ResultSet rs;
			// se l'utente loggato è un user può visualizzare solo le proprie autovetture
			if( privilegi == 'U'){
				query = "SELECT v.targa, v.marca, v.modello, v.limite, v.data, v.ora, v.latitudine, v.longitudine FROM Veicolo v "+
						"JOIN Gestione g ON v.targa = g.targa "+
						"JOIN Utente u ON g.mail = u.mail WHERE u.mail = ? ";
				Object[] params = new Object[1];
				params[0] = mail;
				rs = driver.execute(query, params);
			// se l'utente loggato è un super user le può vedere tutte
			}else
				query = "SELECT DISTINCT(v.targa), v.marca, v.modello, v.limite, v.data, v.ora, v.latitudine, v.longitudine "+
						"FROM Veicolo v "+
						"JOIN Gestione g ON v.targa = g.targa "+
						"JOIN Utente u ON g.mail = u.mail ";	
				rs = driver.execute(query, null);

			while (rs.next())
				res.add(new Veicolo(rs));
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	public static List<Veicolo> getVeicoli() 
	{
		List<Veicolo> res = new ArrayList<>();
		
		try {
			MyDriver driver = MyDriver.getInstance();
			// se l'utente loggato è un user può visualizzare solo le proprie autovetture
			String query = "SELECT targa, marca, modello, limite, data, ora, latitudine, longitudine FROM Veicolo ";
			ResultSet rs = driver.execute(query, null);
			// se l'utente loggato è un super user le può vedere tutte
			
			while (rs.next())
				res.add(new Veicolo(rs));
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	public static boolean inserisciVeicolo(String targa, String marca, String modello) 
	{
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "INSERT INTO Veicolo ( targa, marca, modello ) VALUES ( ?, ?, ?)";
			Object[] params = new Object[3];
			params[0] = targa;
			params[1] = marca;
			params[2] = modello;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
			res = false;
			System.out.println("Select faillita! " + e);
		}
		return res;
	}

	public static boolean modificaVeicolo(String targa, String marca, String modello) 
	{
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "UPDATE Veicolo SET marca = ?, modello = ? WHERE targa = ?";
			Object[] params = new Object[3];
			params[0] = marca;
			params[1] = modello;
			params[2] = targa;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
			res = false;
			System.out.println("Select faillita! " + e);
		}
		return res;
	}

	public static boolean eliminaVeicolo(String targa) {
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "DELETE FROM Veicolo WHERE targa = ?";
			Object[] params = new Object[1];
			params[0] = targa;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
			System.out.println("Select faillita! " + e);
		}
		return res;
	}

	public static boolean trovaVeicolo(String targa) {
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "SELECT * FROM Veicolo WHERE targa = ?";
			Object[] params = new Object[1];
			params[0] = targa;
			// Se modifica 1 riga allora è andato a buon fine
			//if (driver.update(query, params) == 1)
			ResultSet rs = driver.execute(query, params);
			if (rs.next())
				res = true;
		} catch (SQLException e) {
			System.out.println("Select faillita! " + e);
		}
		return res;
	}

	public static boolean impostaLimite(String targa, int limite) {
		boolean res = false;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "UPDATE Veicolo SET limite = ? WHERE targa = ?";
			Object[] params = new Object[2];
			params[0] = limite;
			params[1] = targa;
			// Se modifica 1 riga allora è andato a buon fine
			if (driver.update(query, params) == 1)
				res = true;
		} catch (SQLException e) {
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

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
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
}
