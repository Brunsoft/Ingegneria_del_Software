package univr.is.tmc.servlet;

import univr.is.tmc.entity.Utente;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

	private static char admin = 'A';
	private static char user = 'U';

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		try {
			Utente user = Utente.getUserData(request.getParameter("user"));

			if (user != null && user.getPassword().equals(request.getParameter("pwd"))) {

				HttpSession session = request.getSession(true);
				session.setAttribute("currUserEmail", user.getEmail());
				session.setAttribute("currUserMode", user.getPrivilegi());

				// setting session to expiry in 30 mins
				session.setMaxInactiveInterval(30 * 60);

				response.sendRedirect("welcomeUser.jsp");

			}else
				response.sendRedirect("invalidLogin.jsp"); // error page
		}
		catch (Throwable theException) {
			System.out.println(theException);
		}
	}
}
