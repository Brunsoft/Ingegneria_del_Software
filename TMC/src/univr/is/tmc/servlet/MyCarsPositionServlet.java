package univr.is.tmc.servlet;

import univr.is.tmc.entity.Posizione;
import univr.is.tmc.entity.Utente;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyCarsPositionServlet extends HttpServlet {

    /**
	 * MyCarsPositionServlet, 
     * trova posizione dei veicoli
     * inserisco le posizioni in un Map e inglobo in un jSon
	 */
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

		ArrayList ar = new ArrayList();

		List<Posizione> posVeicoli = this.trovaPosizioneVeicoli(request);

		for (Posizione p : posVeicoli) {
			Map<String, String> options = new LinkedHashMap<String, String>();
			options.put("lat", p.getLatitudine());
			options.put("lng", p.getLongitudine());
			options.put("targa", p.getTarga());
			options.put("email", request.getParameter("email").toString() );
			ar.add(options);
		}

		String json = new Gson().toJson(ar); 
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		out.close();

	}
	
    /**
	 * Cerca la posizione corrente dei veicoli gestiti dall'utente corrente
	 *
	 * @param request contiene l'email dell'utente corrente
	 * @return List Posizione dei veicoli gestiti
	 */

	private List<Posizione> trovaPosizioneVeicoli(HttpServletRequest request)
	{
		List<Posizione> posVeicoli;
		// Se son amministratore, visualizzo tutti i veicoli
		if( Utente.getUserData(request.getParameter("email").toString()).getPrivilegi() == 'A' )
			posVeicoli = Posizione.getPosizioneVeicoli();
		else
			posVeicoli = Posizione.getPosizioneVeicoliUtente( request.getParameter("email").toString() );

		if (posVeicoli.isEmpty())
			// Nessun veicolo trovato
			request.getSession().setAttribute("messaggio", "Nessun veicolo trovato!<br/>");
		else
			// Trovati dei veicoli
			request.getSession().setAttribute("messaggio", "Trovati dei veicoli!<br/>");
		return posVeicoli;
	}

	
}
