package univr.is.tmc.servlet;

import univr.is.tmc.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VeicoliUtenteServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		// valutazione se si deve andare in uno dei modi DML
		String azione = request.getParameter("azione");
		String veicoloUtenteSel = request.getParameter("veicoloUtenteSel");
		// Metto in sessione la modalità per la pagina dei risultati
		request.getSession().setAttribute("azione", azione);

		if (azione.equalsIgnoreCase("Inserisci"))
			response.sendRedirect("modificaVeicoloUtente.jsp");

		if (azione.equalsIgnoreCase("Modifica")) {
			request.getSession().setAttribute("veicoloUtenteSel", veicoloUtenteSel);
			response.sendRedirect("modificaVeicoloUtente.jsp");
		}
		if (azione.equalsIgnoreCase("Elimina")) {
			// Sei sicuro? --> Eliminazione effettuata con successo
			// Elimina
			String[] items = veicoloUtenteSel.split(",");
			String targa = items[0];
			String email = items[1];
			if (VeicoloUtente.eliminaVeicoloUtente(targa, email)) {
				// Eliminazione andata a buon fine
				request.getSession().setAttribute("messaggio", "Eliminazione effettuata con successo!");
			} else {
				// Eliminazione non andata a buon fine
				request.getSession().setAttribute("messaggio", "Errore!");
			}
			response.sendRedirect("modificaEffettuata.jsp");
		}
	}
}
