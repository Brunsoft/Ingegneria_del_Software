package univr.is.tmc.servlet;

import univr.is.tmc.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModificaVeicoloUtenteServlet extends HttpServlet {

	private static char utente = 'U';
	private static char admin = 'A';
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		// valutazione se si deve andare in uno dei modi DML
		String azione = request.getSession().getAttribute("azione").toString();

		if (azione.equalsIgnoreCase("Inserisci")) {
			// I dati nella form sono corretti?
			if (controllaDati(request))
				// Esiste già l'Utente?
				if (this.trovaUtente(request))
					// Esiste già il Veicolo?
					if (this.trovaVeicolo(request))
						// Esiste già l'associazione Utente->Veicolo?
						if (this.trovaVeicoloUtente(request))
							// Inserimento
							this.InsertVeicoloUtente(request);
		}
		if (azione.equalsIgnoreCase("Modifica")) {
			// I dati nella form sono corretti?
			if (controllaDati(request))
				// Esiste già l'Utente?
				if (this.trovaUtente(request))
					// Esiste già il Veicolo?
					if (this.trovaVeicolo(request))
						// Esiste già l'associazione Utente->Veicolo?
						if (this.trovaVeicoloUtente(request))
							// Inserimento
							this.UpdateVeicoloUtente(request);
		}

		response.sendRedirect("modificaEffettuata.jsp");
	}

	private boolean UpdateVeicoloUtente(HttpServletRequest request) {

		String dati = request.getSession().getAttribute("veicoloUtenteSel").toString();
		String[] items = dati.split(",");
		String targa = items[0];
		String email = items[1];

		if (VeicoloUtente.modificaVeicoloUtente(targa, email)) {
			// Modifica effettuata con successo
			request.getSession().setAttribute("messaggio", "Modifica effettuata con successo!");
			return true;
		} else {
			// Inserimento non andato a buon fine
			request.getSession().setAttribute("messaggio", "Inserimento non effettuato a causa di un errore!");
			return false;
		}

	}

	private boolean InsertVeicoloUtente(HttpServletRequest request) {
		if (VeicoloUtente.inserisciVeicoloUtente(
				request.getParameter("targa").toString(),
				request.getParameter("email").toString())) {
			// Inserimento effettuato con successo
			request.getSession().setAttribute("messaggio", "Inserimento effettuato con successo!<br/>");
			return true;
		} else {
			// Inserimento non andato a buon fine
			request.getSession().setAttribute("messaggio", "Inserimento non effettuato a causa di un errore!<br/>");
			return false;
		}
	}

	private boolean trovaUtente(HttpServletRequest request) {
		String email = request.getParameter("email").toString();
		if (Utente.trovaUtente(email)) {
			// OK! Utente esiste
			return true;
		}
		request.getSession().setAttribute("messaggio", "Utente con email "+request.getParameter("email").toString()+" non esiste!<br/>");
		return false;
	}

	private boolean trovaVeicolo(HttpServletRequest request) {
		String targa = request.getParameter("targa").toString();
		if (Veicolo.trovaVeicolo(targa)) {
			// OK! Veicolo esiste
			return true;
		}
		request.getSession().setAttribute("messaggio", "Veicolo con targa "+targa+" non esiste!<br/>");
		return false;
	}

	private boolean trovaVeicoloUtente(HttpServletRequest request) {
		String targa = request.getParameter("targa").toString();
		String email = request.getParameter("email").toString();
		if (VeicoloUtente.trovaVeicoloUtente(targa, email) ) {
			// ERRORE! Associazione Veicolo-Utente esiste
			request.getSession().setAttribute("messaggio", "Associazione Veicolo -> Utente gia esistente!"+targa+" "+email+"<br/>");
			return false;
		}
		return true;
	}

	private boolean controllaDati(HttpServletRequest request) {
		String messaggio = "";
		// Controllo dati
		if (!messaggio.isEmpty()) {
			request.getSession().setAttribute("messaggio", messaggio);
			return false;
		}
		return true;
	}
}
