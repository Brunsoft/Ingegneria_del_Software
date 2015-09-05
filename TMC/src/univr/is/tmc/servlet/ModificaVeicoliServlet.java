package univr.is.tmc.servlet;

import univr.is.tmc.entity.Veicolo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModificaVeicoliServlet extends HttpServlet {

	private static char utente = 'U';
	private static char admin = 'A';
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		// valutazione se si deve andare in uno dei modi DML
		String azione = request.getSession().getAttribute("azione").toString();

		if (azione.equalsIgnoreCase("Inserisci")) {
			// I dati nella form sono corretti?
			if (controllaDati(request))
				// Esiste già?
				if (this.trovaVeicolo(request))
					// Password corrispondono?
					this.InsertVeicolo(request);
		}
		if (azione.equalsIgnoreCase("Modifica")) {
			// I dati nella form sono corretti?
			if (controllaDati(request))
				// Password corrispondono?
				this.UpdateVeicolo(request);
		}

		response.sendRedirect("modificaEffettuata.jsp");
	}

	private boolean UpdateVeicolo(HttpServletRequest request) {
		if (Veicolo.modificaVeicolo(
				request.getSession().getAttribute("veicoloSel").toString(),
				request.getParameter("marca").toString(),
				request.getParameter("modello").toString())) {
			// Modifica effettuata con successo
			request.getSession().setAttribute("messaggio", "Modifica effettuata con successo!");
			return true;
		} else {
			// Inserimento non andato a buon fine
			request.getSession().setAttribute("messaggio", "Inserimento non effettuato a causa di un errore!");
			return false;
		}

	}

	private boolean InsertVeicolo(HttpServletRequest request) {
		if (Veicolo.inserisciVeicolo(
				request.getParameter("targa").toString(),
				request.getParameter("marca").toString(),
				request.getParameter("modello").toString())) {
			// Inserimento effettuato con successo
			request.getSession().setAttribute("messaggio", "Inserimento effettuato con successo!");
			return true;
		} else {
			// Inserimento non andato a buon fine
			request.getSession().setAttribute("messaggio", "Inserimento non effettuato a causa di un errore!");
			return false;
		}
	}

	private boolean trovaVeicolo(HttpServletRequest request) {
		if (Veicolo.trovaVeicolo(request.getParameter("targa").toString())) {
			// Utente esiste già
			request.getSession().setAttribute("messaggio", "Veicolo targato "+request.getParameter("targa")+" già esistente!");
			return false;
		}
		return true;
	}

	private boolean controllaDati(HttpServletRequest request) {
		String messaggio = "";
		// Controllo dati
		// Se uno fallisce return false

		if (request.getParameter("marca").toString().length() > 20)
			messaggio += "La marca deve contenere meno di 20 caratteri </br>";

		if (request.getParameter("modello").toString().length() > 20)
			messaggio += "Il modello deve contenere meno di 20 caratteri </br>";

		if (!messaggio.isEmpty()) {
			request.getSession().setAttribute("messaggio", messaggio);
			return false;
		}
		return true;
	}
}
