package univr.is.tmc.servlet;

import univr.is.tmc.entity.Veicolo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GestioneLimitiServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		// valutazione se si deve andare in uno dei modi DML
		String azione = request.getParameter("azione");
		String veicoloSel = request.getParameter("carLimSel");
		// Metto in sessione la modalità per la pagina dei risultati
		request.getSession().setAttribute("azione", azione);

		if (azione.equalsIgnoreCase("Modifica")) {
			request.getSession().setAttribute("carLimSel", veicoloSel);
			response.sendRedirect("modificaLimite.jsp");
		}
		if (azione.equalsIgnoreCase("Azzera")) {
			// Sei sicuro? --> Azzeramento effettuato con successo
			// Azzera
			if (Veicolo.impostaLimite(veicoloSel, 0)) {
				// Eliminazione andata a buon fine
				request.getSession().setAttribute("messaggio", "Azzeramento limite effettuato con successo!");
			} else {
				// Eliminazione non andata a buon fine
				request.getSession().setAttribute("messaggio", "Errore!");
			}
			response.sendRedirect("modificaEffettuata.jsp");
		}
	}
}
