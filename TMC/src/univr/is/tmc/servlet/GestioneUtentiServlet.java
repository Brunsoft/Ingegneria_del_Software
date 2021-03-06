package univr.is.tmc.servlet;

import univr.is.tmc.entity.Utente;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GestioneUtentiServlet extends HttpServlet {

    /**
	 * GestioneUtentiServlet, 
     * IF (azione = "Nuovo Utente")
	 *   vai a modificaUtente.jsp
     *
     * IF (azione = "Modifica")
     *   set Attribute "utenteSel"
	 *   vai a modificaUtente.jsp
	 * 
     * IF (azione = "Elimina")
     *   elimino utente utenteSel
	 *   vai a modificaEffettuata.jsp
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		String azione = request.getParameter("azione");
		String utenteSel = request.getParameter("utenteSel");
		// setAttribute di azione
		request.getSession().setAttribute("azione", azione);

		if (azione.equalsIgnoreCase("Nuovo Utente"))
			response.sendRedirect("modificaUtente.jsp");

		if (azione.equalsIgnoreCase("Modifica")) {
            // setAttribute di utenteSel
			request.getSession().setAttribute("utenteSel", utenteSel);
			response.sendRedirect("modificaUtente.jsp");
		}
		if (azione.equalsIgnoreCase("Elimina")) {
			// Se sto eliminando un Admin, Controllo se ci sono altri amministratori
			if (otherAdmin(utenteSel)){
                // Elimina
				if (Utente.eliminaUtente(utenteSel)) {
                    // Se ho eliminato me stesso invalido la sessione 
					if (utenteSel.equals(request.getSession().getAttribute("currUserEmail").toString()))
						request.getSession().invalidate();
					request.getSession().setAttribute("messaggio", "Eliminazione effettuata con successo!");
				} else {
					// Eliminazione non andata a buon fine
					request.getSession().setAttribute("messaggio", "Errore!");
				}
			}else
				request.getSession().setAttribute("messaggio", "Errore! Non puoi eliminare l'ultimo Amministratore!");
			response.sendRedirect("modificaEffettuata.jsp");
		}
	}

    /**
	 * Controlla che ci siano altri amministratori
	 *
	 * @param utenteSel Email dell'utente in questione
	 * @return boolean che indica se ci son altri Admin
	 */

	private boolean otherAdmin(String utenteSel){
		int count = 0;
		List<Utente> listaUtenti = Utente.getUsers();
		for ( Utente u : listaUtenti ){
            // Se l'utente e' amministratore e non e' utenteSel
			if ( u.getPrivilegi() == 'A' && (!u.getEmail().equals(utenteSel)) )
				count ++;
		}
		if ( count > 0 )
			return true;
		return false;
	}
}
