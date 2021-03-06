package univr.is.tmc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {

    /**
	 * LogoutServlet, 
     * invalida sessione
     * vai a login.jsp
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		response.setContentType("text/html");
		
		request.getSession().invalidate();		
		response.sendRedirect("login.jsp");
	}
}
