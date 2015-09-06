package univr.is.tmc.servlet;

import univr.is.tmc.entity.Veicolo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GestioneVeicoliServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		// valutazione se si deve andare in uno dei modi DML
		String azione = request.getParameter("azione");
		String veicoloSel = request.getParameter("veicoloSel");
		// Metto in sessione la modalità per la pagina dei risultati
		request.getSession().setAttribute("azione", azione);

		if (azione.equalsIgnoreCase("Nuovo Veicolo"))
			response.sendRedirect("modificaVeicolo.jsp");

		if (azione.equalsIgnoreCase("Modifica")) {
			request.getSession().setAttribute("veicoloSel", veicoloSel);
			response.sendRedirect("modificaVeicolo.jsp");
		}
		if (azione.equalsIgnoreCase("Elimina")) {
			// Sei sicuro? --> Eliminazione effettuata con successo
			// Elimina
			if (Veicolo.eliminaVeicolo(veicoloSel)) {
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
