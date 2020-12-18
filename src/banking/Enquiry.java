package banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DBAAccess;


public class Enquiry extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		HttpSession sc=request.getSession(false);
		if(sc!=null)
		{
			String name=(String)sc.getAttribute("name");
			try {
				Connection cn=DBAAccess.getConn();
				PreparedStatement ps=cn.prepareStatement("select username,balance from banking where username=?");
				ps.setString(1, name);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					out.print("<center><a href='Home.html'>back</a></center>");	
					out.print("<center><h3>Username : "+rs.getString(1)+"</h3>");
					out.print("<h3>Amount : "+rs.getDouble(2)+"</h3></center>");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			out.print("<center><h1>Login first to see Acount Details.</h1></center>");
			RequestDispatcher disp=request.getRequestDispatcher("index.html");
			disp.include(request, response);
		}
	}
}
