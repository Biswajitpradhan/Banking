package banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DBAAccess;


public class Login extends HttpServlet {
	static Connection cn;
	static PreparedStatement pr;
	
	public void init()throws ServletException
	{
		try {
			cn=DBAAccess.getConn();
			pr=cn.prepareStatement("select * from Banking where username=? and password=?");
		} 
		catch (SQLException f){
			f.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String name=request.getParameter("user");
		String password=request.getParameter("pass");
		try {
			pr.setString(1, name);
			pr.setString(2, password);
			ResultSet rs=pr.executeQuery();
			if(rs.next())
			{
				Cookie ck=new Cookie("name", name);
				ck.setMaxAge(60*60*24*365);
				response.addCookie(ck);
				HttpSession  sc=request.getSession();
				sc.setAttribute("name", name);
				out.print("<center><b>Hello "+name+" welcome to JTBank.</b></center>");
				RequestDispatcher req=request.getRequestDispatcher("Home.html");
				req.include(request, response);
			}
			else
			{
				out.print("<center><b>Hello "+name+" Login Failed </b><br><b>try again </b></center>");
				RequestDispatcher req=request.getRequestDispatcher("index.html");
				req.include(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
