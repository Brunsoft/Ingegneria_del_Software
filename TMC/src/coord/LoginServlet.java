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
import coord.entity.*;  
  
public class LoginServlet extends HttpServlet{  
  
    private static final long serialVersionUID = 1L;  
  	LoginDbAccessor gestoreDB;

    public void doPost(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {    
  
		gestoreDB = new LoginDbAccessor();
        response.setContentType("text/html");    
        PrintWriter out = response.getWriter();    

	// ==================================== (LOGIN/LOGOUT) USER ====================================
		
		try {
		    String login = request.getParameter("tmcLogin");		// Parametro settato per Login/Logout
		
			if( login.equals("Login") ){
				String n = request.getParameter("username");    
				String p = request.getParameter("userpass");   
				  
				HttpSession session = request.getSession(false); 
				session.setAttribute("name", n);
				
				gestoreDB.openConnectionToDB();			// Apro la connessione al DB
				char c = gestoreDB.Login(n, p);			// Cerco l'utente
				gestoreDB.closeConnectionToDB();		// Chiudo la connessione al DB

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

	// ====================== (INSERT/UPDATE/DELETE) USER | TRACK CAR =======================

		try{
			String action = request.getParameter("tmcAction");				// Parametro per Le varie azioni

			if( action.equals("Crea Nuovo Account") ){
				request.getRequestDispatcher("new-account.jsp").include(request,response);
			}else
			if( action.equals("Modifica Account") ){
				request.getRequestDispatcher("sel-upd-user.jsp").include(request,response);
			}else
			if( action.equals("Eliminazione Account") ){
				request.getRequestDispatcher("sel-del-user.jsp").include(request,response);
			}else
			if( action.equals("Tracking Veicolo") ){
				request.getRequestDispatcher("track.jsp").include(request,response);
			}else
			if( action.equals("Indietro") ){
				request.getRequestDispatcher("welcome-admin.jsp").include(request,response);
			}
		}catch(Exception e){}

	// =============================== INSERT USER ====================================

		try{
			String action = request.getParameter("tmcNewUser");
			if( action.equals("Iscrivi") ){

				String nome = request.getParameter("nomeN");
				String cogn = request.getParameter("cognN");  
				String pwd = request.getParameter("pwdN");
				String mail = request.getParameter("mailN");
				String tel = request.getParameter("telN");
				
				gestoreDB.openConnectionToDB();										// Apro la connessione al DB
				boolean b = gestoreDB.NewUser(nome, cogn, pwd, mail, tel);			// Cerco l'utente
				gestoreDB.closeConnectionToDB();									// Chiudo la connessione al DB
				
				if( !b )
					request.setAttribute("dataStringa", "Successo!");
				else
					request.setAttribute("dataStringa", "Errore!");

				request.getRequestDispatcher("new-account.jsp").include(request,response);        
			}
		}catch(Exception e){}

	// =============================== UPDATE ACCOUNT ====================================

		try{
			String action = request.getParameter("tmcUpdate");
			if( action.equals("Cerca") ){

				String mail = request.getParameter("mailU");
				
				gestoreDB.openConnectionToDB();							// Apro la connessione al DB
				Utente u = gestoreDB.SearchUser(mail);					// Cerco l'utente
				gestoreDB.closeConnectionToDB();						// Chiudo la connessione al DB
				
						
				if( u.getId() != 0 && u.getTipo() == 'U'){
					request.setAttribute("idU", u.getId());
					request.setAttribute("nomeU", u.getNome());
					request.setAttribute("cognU", u.getCognome());
					request.setAttribute("pwdU", u.getPwd());
					request.setAttribute("mailU", u.getEmail());
					request.setAttribute("telU", u.getTel());

					request.getRequestDispatcher("update-user.jsp").include(request,response);
				}else{
					request.setAttribute("dataStringa", "Nessun Utente Corrispondente!");
					request.getRequestDispatcher("sel-upd-user.jsp").include(request,response);  
				}
			}
			if( action.equals("Modifica") ){
				Utente u = new Utente();
				u.setId( Integer.parseInt(request.getParameter("idU")) );
				u.setNome( ""+request.getParameter("nomeU") );
				u.setCognome( ""+request.getParameter("cognU") );
				u.setPwd( ""+request.getParameter("pwdU") );
				u.setEmail( ""+request.getParameter("mailU") );
				u.setTel( ""+request.getParameter("telU") );

				gestoreDB.openConnectionToDB();							// Apro la connessione al DB
				boolean b = gestoreDB.UpdateUser( u );					// Modifica l'utente
				gestoreDB.closeConnectionToDB();						// Chiudo la connessione al DB

				if( !b )
					request.setAttribute("dataStringa", "Successo!");
				else
					request.setAttribute("dataStringa", "Errore!");

				request.getRequestDispatcher("sel-upd-user.jsp").include(request,response);  
			}
		}catch(Exception e){}

	// =============================== DELETE ACCOUNT ====================================

		try{
			String action = request.getParameter("tmcDelete");
			if( action.equals("Cerca") ){

				String mail = request.getParameter("mailD");
				
				gestoreDB.openConnectionToDB();							// Apro la connessione al DB
				Utente u = gestoreDB.SearchUser(mail);					// Cerco l'utente
				gestoreDB.closeConnectionToDB();						// Chiudo la connessione al DB
				
						
				if( u.getId() != 0 && u.getTipo() == 'U'){

					gestoreDB.openConnectionToDB();							// Apro la connessione al DB
					boolean b = gestoreDB.DeleteUser( u );			// Cancello l'utente
					gestoreDB.closeConnectionToDB();						// Chiudo la connessione al DB

					if ( !b )
						request.setAttribute("dataStringa", "Successo!");
					else
						request.setAttribute("dataStringa", "Errore!");

					request.getRequestDispatcher("sel-del-user.jsp").include(request,response);

				}else{
					request.setAttribute("dataStringa", "Nessun Utente Corrispondente!");
					request.getRequestDispatcher("sel-del-user.jsp").include(request,response);  
				}
			}
		}catch(Exception e){}

        out.close();    
    }    
} 
