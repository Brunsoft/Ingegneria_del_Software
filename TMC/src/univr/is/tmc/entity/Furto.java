package univr.is.tmc.entity;

import univr.is.tmc.database.MyDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class Furto {
	
	private int id;
	private String targa;
	private String data;
	private String ora;
	private List<Posizione> posizioni = new ArrayList<>();
	private static int TEMP = 60;

	// ============== COSTRUTTORI ====================================================

	public Furto(int id, String targa, String data, String ora) 
	{
		this.id = id;
		this.targa = targa;
		this.data = data;
		this.ora = ora;
	}

	public Furto(ResultSet rs) throws SQLException 
	{
		this.id = rs.getInt("id");
		this.targa = rs.getString("targa");
		this.data = rs.getString("data");
		this.ora = rs.getTime("ora").toString();
	}

	public Furto() { }

	// ============== METODI ==========================================================

	public static List<Furto> getFurtiAttiviUtente(String email) 
	{	
		List<Furto> res = new ArrayList<>();
		List<Veicolo> veicoli = Veicolo.getVeicoliUser( email );
		String data = getLocalData();
		String ora = getLocalOra();
		try {
			MyDriver driver = MyDriver.getInstance();
			
			for (Veicolo v : veicoli){
				// se l'utente loggato è un user può visualizzare solo le proprie autovetture
				String query = "SELECT f.id, f.targa, f.data, f.ora FROM Furto f JOIN Posizione p ON f.id = p.id_furto "+
								"WHERE f.id = ( SELECT MAX(id) FROM Furto where targa = ? ) "+
								"AND p.id = ( SELECT MAX(id) FROM Posizione WHERE targa = ? ) "+
								"AND f.data = ? "+
								"AND p.ora >= ?";
				Object[] params = new Object[4];
				params[0] = v.getTarga();
				params[1] = v.getTarga();
				params[2] = data;
				params[3] = ora;	

				ResultSet rs = driver.execute(query, params);

				while (rs.next())
					res.add(new Furto(rs));
			}
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	private static String getLocalData()
	{
		Calendar localDate = Calendar.getInstance();
		String data = "", mese = "", giorno = "";

		if ( (localDate.get(Calendar.MONTH)+1) < 10 )					// MESE
			mese = "0"+(localDate.get(Calendar.MONTH)+1);
		else 
			mese = ""+(localDate.get(Calendar.MONTH)+1);

		if ( localDate.get(Calendar.DAY_OF_MONTH) < 10 )				// GIORNO
			giorno = "0"+ localDate.get(Calendar.DAY_OF_MONTH);
		else 
			giorno = ""+ localDate.get(Calendar.DAY_OF_MONTH);
	
		return data = localDate.get(Calendar.YEAR) +"-"+ mese +"-"+ giorno;
	}
	
	private static String getLocalOra()
	{
		Calendar localDate = Calendar.getInstance();
		localDate.add(Calendar.MINUTE, -TEMP);
		String ora = "";
		
		if ( localDate.get(Calendar.HOUR_OF_DAY) < 10 )
			ora = "0"+ localDate.get(Calendar.HOUR_OF_DAY);
		else 
			ora = ""+ localDate.get(Calendar.HOUR_OF_DAY);

		if ( localDate.get(Calendar.MINUTE) < 10 )
			ora = ora+":0"+ localDate.get(Calendar.MINUTE);
		else
			ora = ora+":"+ localDate.get(Calendar.MINUTE);
		
		return ora;		
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
	
	public static String getLocalDate() {
		return ""+getLocalData()+" "+getLocalOra();
	}
}
