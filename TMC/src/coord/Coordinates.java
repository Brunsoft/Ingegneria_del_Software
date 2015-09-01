package coord;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Coordinates extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		
		String url = "jdbc:postgresql://dbserver.scienze.univr.it/dblab23";
		String driver = "org.postgresql.Driver";
		String user = "userlab23";
		String pwd = "ventitreDF";
        try {
             Connection connection;
             Class.forName(driver);
             
             connection=DriverManager.getConnection(url, user, pwd);
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(" SELECT p.latitudine, p.longitudine, p.ora, p.id_furto, f.targa FROM Posizione p "+
														"JOIN Furto f ON f.id = p.id_furto "+
														"WHERE p.id_furto = (SELECT MAX(id) FROM furto) ");
             ArrayList ar = new ArrayList();

             while(set.next()){
				Map<String, String> options = new LinkedHashMap<String, String>();
		     	options.put("lat", ""+set.getBigDecimal(1));
		        options.put("lng", ""+set.getBigDecimal(2));
				options.put("ora", set.getString(3));
				options.put("id", ""+set.getInt(4));
				options.put("targa", set.getString(5));

		        ar.add(options);
             }
             String json = new Gson().toJson(ar); 
             response.setContentType("application/json");
             response.setCharacterEncoding("UTF-8");
             response.getWriter().write(json);
        } finally {            
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Coordinates.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Coordinates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Coordinates.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Coordinates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
