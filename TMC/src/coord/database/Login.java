package coord.database;  
  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
  
public class Login {  

    public static char validate(String nome, String pass) {          
        boolean status = false;  
        Connection conn = null;  
        PreparedStatement pst = null;  
        ResultSet rs = null;
  
        String url = "jdbc:postgresql://dbserver.scienze.univr.it/dblab23";
		String driver = "org.postgresql.Driver";
		String user = "userlab23";
		String pwd = "ventitreDF";
		char result = '/';

        try {  
            Class.forName(driver).newInstance();  
            conn = DriverManager.getConnection(url, user, pwd);  
  
            pst = conn.prepareStatement("SELECT tipo FROM Utente WHERE nome = ? and pwd = ? ");  

            pst.setString(1, nome);  
            pst.setString(2, pass);  
  
            rs = pst.executeQuery();  
            if (rs.next())				// found
				  result = rs.getString(1).charAt(0);
  
        } catch (Exception e) {  
            System.out.println(e);  
        } finally {  
            if (conn != null) {  
                try {  
                    conn.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (pst != null) {  
                try {  
                    pst.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (rs != null) {  
                try {  
                    rs.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return result;  
    }  
}  
