package univr.is.tmc.servlet;

import univr.is.tmc.entity.Veicolo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GestioneLimitiServlet extends HttpServlet {

    /**
	 * GestioneLimitiServlet, 
     * IF (azione = "Modifica")
     *   set Attribute "carLimSel"
	 *   vai a modificaLimite.jsp
     *
     * IF (azione = "Azzera")
     *   imposto nuovo limite
	 *   vai a modificaEffettuata.jsp
	 * 
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		String azione = request.getParameter("azione");
		String veicoloSel = request.getParameter("carLimSel");
		// setAttribute di azione
		request.getSession().setAttribute("azione", azione);

		if (azione.equalsIgnoreCase("Modifica")) {
		    // setAttribute di carLimSel
			request.getSession().setAttribute("carLimSel", veicoloSel);
			response.sendRedirect("modificaLimite.jsp");
		}
		if (azione.equalsIgnoreCase("Azzera")) {
			// Azzera
			if (Veicolo.impostaLimite(veicoloSel, 0)) {
				// Azzeramento andato a buon fine
				request.getSession().setAttribute("messaggio", "Azzeramento limite effettuato con successo!");
			} else {
				// Azzeramento non andato a buon fine
				request.getSession().setAttribute("messaggio", "Errore!");
			}
			response.sendRedirect("modificaEffettuata.jsp");
		}
	}
}
