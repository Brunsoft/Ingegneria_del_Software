package coord;  
  
import java.io.IOException;  
import java.io.PrintWriter;  
  
import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
  
import coord.database.*;  
  
public class LoginServlet extends HttpServlet{  
  
    private static final long serialVersionUID = 1L;  
  	LoginDbAccessor gestoreDB;

    public void doPost(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {    
  
		gestoreDB = new LoginDbAccessor();
        response.setContentType("text/html");    
        PrintWriter out = response.getWriter();    
		try {
		    String login = request.getParameter("tmcLogin");		// Parametro settato per Login/Logout
		
		// ==================================== LOGIN / LOGOUT ====================================
		
			if( login.equals("Login") ){
				String n = request.getParameter("username");    
				String p = request.getParameter("userpass");   
				  
				HttpSession session = request.getSession(false); 
				session.setAttribute("name", n);
				
				gestoreDB.openConnectionToDB();
				char c = gestoreDB.Login(n, p);			
				gestoreDB.closeConnectionToDB();

				if( c == 'A' ){    
				    request.getRequestDispatcher("welcome-admin.jsp").forward(request,response);
				}else 
				if( c == 'U' ){  
					request.getRequestDispatcher("welcome-regular.jsp").forward(request,response);
				}else{
					request.setAttribute("dataStringa", "Password o Username errate");    
				    request.getRequestDispatcher("index.jsp").include(request,response);    
				}    
	  		}else 
			if( login.equals("Logout") ){
				//request.setAttribute("dataStringa", "Logout eseguito"); 
				request.getRequestDispatcher("index.jsp").include(request,response); 
			}
		}catch(Exception e){}
	// ===============================
		try{
			String action = request.getParameter("tmcAction");		// Parametro settato per Le varie azioni

			if( action.equals("Crea Nuovo Account") ){
				out.println("Crea");
				request.getRequestDispatcher("new-account.jsp").include(request,response);
			}else
			if( action.equals("Modifica Account") ){
				out.println("Modifica");
				request.getRequestDispatcher("welcome-admin.jsp").include(request,response);
			}else
			if( action.equals("Eliminazione Account") ){
				out.println("Elimina");
				request.getRequestDispatcher("welcome-admin.jsp").include(request,response);
			}else
			if( action.equals("Tracking Veicolo") ){
				//out.println("Elimina");
				request.getRequestDispatcher("track.jsp").include(request,response);
			}else
			if( action.equals("Indietro") ){
				//out.println("Elimina");
				request.getRequestDispatcher("welcome-admin.jsp").include(request,response);
			}
		}catch(Exception e){}

        out.close();    
    }    
} 
