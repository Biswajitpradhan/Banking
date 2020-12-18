package banking;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		Cookie ck=new Cookie("name","");
		ck.setMaxAge(0);
		response.addCookie(ck);
		HttpSession sc=request.getSession();
		sc.invalidate();
		out.print("<center><h1><Successfully Loged out</h1></center>");
		RequestDispatcher dis=request.getRequestDispatcher("index.html");
		dis.include(request, response);
	}


}
