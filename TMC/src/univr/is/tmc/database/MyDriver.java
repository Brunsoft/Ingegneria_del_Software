package univr.is.tmc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MyDriver {
	private String sUrlDB;
	private String sUserNameDB;
	private String sPasswordDB;
	private Connection connection;
	
	private static MyDriver instance;

	// Singleton
	private MyDriver() {

		try {
			//Class.forName("org.postgresql.Driver");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		//sUrlDB = "jdbc:postgresql://dbserver.scienze.univr.it/dblab23";
		sUrlDB = "jdbc:mysql://localhost:3306/TMC";
		//sUserNameDB = "userlab23";
		sUserNameDB = "root";
		//sPasswordDB = "ventitreDF";
		sPasswordDB = "luca";
		try {
			connection = DriverManager.getConnection(sUrlDB, sUserNameDB, sPasswordDB);
		} catch (SQLException e) {
			System.out.println("Non riesco a connettermi...");
		}
	}

	public static MyDriver getInstance() {
		return instance == null ? instance = new MyDriver() : instance;
	}

	public ResultSet execute(String query, Object[] params) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(query);

		if (params != null) {
			if (params.length > 0) {
				for (int i = 0; i < (params.length); i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
		}
		ResultSet result = execute(ps);
		// ps.close(); // GUAI, il resultset risulterebbe chiuso
		return result;
	}

	public int update(String query, Object[] params) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(query);
		if (params != null) {
			if (params.length > 0) {
				for (int i = 0; i < (params.length); i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
		}
		int result = update(ps);
		ps.close();
		return result;
	}

	private int update(PreparedStatement ps) throws SQLException {
		int result = -1;
		result = ps.executeUpdate();
		return result;
	}

	private ResultSet execute(PreparedStatement ps) throws SQLException {
		ResultSet result = null;
		result = ps.executeQuery();
		return result;
	}

	public void close() throws SQLException {
		connection.close();
	}
}
