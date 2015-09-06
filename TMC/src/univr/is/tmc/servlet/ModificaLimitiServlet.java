package univr.is.tmc.servlet;

import univr.is.tmc.entity.Veicolo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModificaLimitiServlet extends HttpServlet {

	private static char utente = 'U';
	private static char admin = 'A';
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		// valutazione se si deve andare in uno dei modi DML
		String azione = request.getSession().getAttribute("azione").toString();

		if (azione.equalsIgnoreCase("Modifica")) {
			// I dati nella form sono corretti?
			if (controllaDati(request))
				// Password corrispondono?
				this.UpdateLimite(request);
		}

		response.sendRedirect("modificaEffettuata.jsp");
	}

	private boolean UpdateLimite(HttpServletRequest request) {
		if (Veicolo.impostaLimite(
				request.getSession().getAttribute("carLimSel").toString(),
				Integer.parseInt(request.getParameter("limite").toString()) )) {
			// Modifica effettuata con successo
			request.getSession().setAttribute("messaggio", "Modifica effettuata con successo!");
			return true;
		} else {
			// Inserimento non andato a buon fine
			request.getSession().setAttribute("messaggio", "Inserimento non effettuato a causa di un errore!");
			return false;
		}

	}

	private boolean controllaDati(HttpServletRequest request) {
		String messaggio = "";
		// Controllo dati
		// Se uno fallisce return false
		int limite = Integer.parseInt(request.getParameter("limite").toString());
		if ( limite < 0 )
			messaggio += "Il limite deve essere maggiore di 0 </br>";

		if ( limite > 300 )
			messaggio += "Il limite deve essere minore di 300 </br>";

		if (!messaggio.isEmpty()) {
			request.getSession().setAttribute("messaggio", messaggio);
			return false;
		}
		return true;
	}
}
