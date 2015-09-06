package univr.is.tmc.servlet;

import univr.is.tmc.entity.Utente;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GestioneUtentiServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		// valutazione se si deve andare in uno dei modi DML
		String azione = request.getParameter("azione");
		String utenteSel = request.getParameter("utenteSel");
		// Metto in sessione la modalità per la pagina dei risultati
		request.getSession().setAttribute("azione", azione);

		if (azione.equalsIgnoreCase("Nuovo Utente"))
			response.sendRedirect("modificaUtente.jsp");

		if (azione.equalsIgnoreCase("Modifica")) {
			request.getSession().setAttribute("utenteSel", utenteSel);
			response.sendRedirect("modificaUtente.jsp");
		}
		if (azione.equalsIgnoreCase("Elimina")) {
			// Sei sicuro? --> Eliminazione effettuata con successo
			// Elimina
			if (otherAdmin(utenteSel)){
				if (Utente.eliminaUtente(utenteSel)) {
					if (utenteSel.equals(request.getSession().getAttribute("currUserEmail").toString()))
						request.getSession().invalidate();
					request.getSession().setAttribute("messaggio", "Eliminazione effettuata con successo!");
				} else {
					// Inserimento non andato a buon fine
					request.getSession().setAttribute("messaggio", "Errore!");
				}
			}else
				request.getSession().setAttribute("messaggio", "Errore! Non puoi elliminare l'ultimo Amministratore!");
			response.sendRedirect("modificaEffettuata.jsp");
		}
	}

	private boolean otherAdmin(String utenteSel){
		int count = 0;
		List<Utente> listaUtenti = Utente.getUsers();
		for ( Utente u : listaUtenti ){
			if ( u.getPrivilegi() == 'A' && (!u.getEmail().equals(utenteSel)) )
				count ++;
		}
		if ( count > 0 )
			return true;
		return false;
	}
}
