package univr.is.tmc.servlet;

import univr.is.tmc.entity.PosizioneFurto;

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

public class UpdateServlet extends HttpServlet {

    /**
	 * UpdateServlet, utilizzata dal JavaScript LiveTracking
     * trova l'ultima posizione, del furto scelto
     * inserisco la posizione in un Map e inglobo in un jSon
	 */
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

		ArrayList ar = new ArrayList();

		PosizioneFurto posVeicolo = this.trovaPosizioneVeicolo(request);

		Map<String, String> options = new LinkedHashMap<String, String>();
		options.put("lat", posVeicolo.getLatitudine());
		options.put("lng", posVeicolo.getLongitudine());
		options.put("ora", posVeicolo.getOra());
		ar.add(options);

		String json = new Gson().toJson(ar); 
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		out.close();

	}
	
    /**
	 * Cerca l'ultima Posizione del Furto in questione
	 *
	 * @param request contiene l'id del furto in questione
	 * @return PosizioneFurto con l'ultima posizione del ladro
	 */

	private PosizioneFurto trovaPosizioneVeicolo(HttpServletRequest request)
	{
		int idFurto = Integer.parseInt(request.getParameter("idFurto").toString());
		PosizioneFurto posFurto = PosizioneFurto.getUltimaPos( idFurto );
		if ( posFurto == null )
			request.getSession().setAttribute("messaggio", "Errore nel recupero dell'ultima posizione!<br/>");
		else
			request.getSession().setAttribute("messaggio", "Ultima posizione recuperata con successo!<br/>");
		return posFurto;
	}
}
