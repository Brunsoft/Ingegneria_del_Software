package univr.is.tmc.servlet;

import univr.is.tmc.entity.Utente;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllarmiServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		// valutazione se si deve andare in uno dei modi DML
		String azione = request.getParameter("azione");
		String veicoloSel = request.getParameter("veicoloSel");
		// Metto in sessione la modalità per la pagina dei risultati
		request.getSession().setAttribute("azione", azione);
		request.getSession().setAttribute("veicoloSel", veicoloSel);

		if (azione.equalsIgnoreCase("Vedi Allarmi"))
			response.sendRedirect("allarmiVeicolo.jsp");
	}
}
