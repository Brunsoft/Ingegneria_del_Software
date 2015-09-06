package univr.is.tmc.entity;

import univr.is.tmc.database.MyDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class Allarme {
	
	private int id;
	private String targa;
	private int diffVel;
	private int velocita;
	private String data;
	private String ora;

	// ============== COSTRUTTORI ====================================================

	public Allarme(int id, String targa, int diffVel, int velocita, String data, String ora) 
	{
		this.id = id;
		this.targa = targa;
		this.diffVel = diffVel;
		this.velocita = velocita;
		this.data = data;
		this.ora = ora;
	}

	public Allarme(ResultSet rs) throws SQLException 
	{
		this.id = rs.getInt("id");
		this.targa = rs.getString("targa");
		this.diffVel = rs.getInt("dVel");
		this.velocita = rs.getInt("velocita");
		this.data = rs.getDate("data").toString();
		this.ora = rs.getTime("ora").toString();
	}

	public Allarme() { }

	// ============== METODI ==========================================================

	public static List<Allarme> getAllarmi(String targa) 
	{	
		List<Allarme> res = new ArrayList<>();
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "SELECT a.id, a.targa, a.dVel, a.velocita, a.data, a.ora FROM Allarme a JOIN Veicolo v ON a.targa = v.targa "+
							"WHERE a.targa = ? ORDER BY (a.data)";
			Object[] params = new Object[1];
			params[0] = targa;

			ResultSet rs = driver.execute(query, params);

			while (rs.next())
				res.add(new Allarme(rs));
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	// =========== METODI SET/GET =============================================================

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public void setDiffVelocita(int diffVel) {
		this.diffVel = diffVel;
	}

	public int getDiffVelocita() {
		return diffVel;
	}
 
	public void setVelocita(int velocita) {
		this.velocita = velocita;
	}

	public int getVelocita() {
		return velocita;
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
}
