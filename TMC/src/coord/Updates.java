package coord;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Updates extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		
		String url = "jdbc:postgresql://dbserver.scienze.univr.it/dblab23";
		String driver = "org.postgresql.Driver";
		String user = "userlab23";
		String pwd = "ventitreDF";

        try {
             ArrayList ar=new ArrayList();
             boolean foundResults = false;
             ResultSet set = null;
             Statement statement = null;         
             String lat=null;
             String lng=null;
                    int count=0;
                        try 
                        {
                            Class.forName(driver);
                            Connection conn=DriverManager.getConnection(url, user, pwd);
                            statement = conn.createStatement();
                            set = statement.executeQuery("SELECT p.latitudine, p.longitudine, p.ora, p.id_furto, f.targa FROM Posizione p "+
															"JOIN Furto f ON f.id = p.id_furto "+
															"WHERE p.id_furto = (SELECT MAX(id) FROM furto) "+
															"ORDER BY p.ora DESC LIMIT 1 ");
                            while(set.next()) {
                                Map<String, String> options = new LinkedHashMap<String, String>();
                                options.put("lat", ""+set.getBigDecimal(1));
                                options.put("lng", ""+set.getBigDecimal(2));
								options.put("ora", set.getString(3));
								options.put("id", ""+set.getInt(4));
								options.put("targa", set.getString(5));

                                ar.add(options);
                            }
                            conn.close(); 
                            String json = new Gson().toJson(ar); 
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write(json);
                        }
                        catch(Exception e)
                        {
                            out.print(e);
                        }
        }catch(Exception s){
            System.out.println("ERROR!!!");
            s.printStackTrace();
        }
         finally {            
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
