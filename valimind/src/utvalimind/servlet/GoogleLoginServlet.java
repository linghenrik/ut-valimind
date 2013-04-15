package utvalimind.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
//Not in use at the moment, just an idea to do

public class GoogleLoginServlet extends HttpServlet  {
		
		private static final long serialVersionUID = -6353165191160800417L;

		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");

			String google_id = request.getParameter("google_id");
			String login = request.getParameter("login");

			if (login != null && login.equals("yes")) {
				response.sendRedirect("/war/index.html"+google_id);
			}
			else { //TODO
				response.sendRedirect("https://accounts.google.com/o/oauth2/auth?" +
						"scope=" +
						"response_type=code&" +
						"state=TESTING&" +
						"redirect_uri=http://www.valimindbyutvalimind.appspot.com/AuthContainer" +
						"client_id=251999788712.project.googleusercontent.com" 
						);
			}
		}
	
}
