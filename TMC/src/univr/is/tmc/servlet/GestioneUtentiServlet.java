package univr.is.tmc.servlet;

import univr.is.tmc.entity.Utente;

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

		if (azione.equalsIgnoreCase("Inserisci"))
			response.sendRedirect("modificaUtente.jsp");

		if (azione.equalsIgnoreCase("Modifica")) {
			request.getSession().setAttribute("utenteSel", utenteSel);
			response.sendRedirect("modificaUtente.jsp");
		}
		if (azione.equalsIgnoreCase("Elimina")) {
			// Sei sicuro? --> Eliminazione effettuata con successo
			// Elimina
			if (Utente.eliminaUtente(utenteSel)) {
				if (utenteSel.equals(request.getSession().getAttribute("currUserEmail").toString()))
					request.getSession().invalidate();
				request.getSession().setAttribute("messaggio", "Eliminazione effettuata con successo!");
			} else {
				// Inserimento non andato a buon fine
				request.getSession().setAttribute("messaggio", "Errore!");
			}
			response.sendRedirect("modificaEffettuata.jsp");
		}
	}
}
