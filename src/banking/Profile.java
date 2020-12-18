package banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DBAAccess;



public class Profile extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		HttpSession sc=request.getSession(false);
		if(sc!=null)
		{
			String name=(String)sc.getAttribute("name");
			try {
				Connection cn=DBAAccess.getConn();
				PreparedStatement ps=cn.prepareStatement("select * from banking where username=?");
				ps.setString(1, name);
				ResultSet rs=ps.executeQuery();
				
				while(rs.next())
				{
					out.print("<html><body bgcolor='cyan' ><a href='Home.html'>Back</a><center>");
					out.print("<h1 >Profile</h1>");
					out.print("<table cellspacing='5px'>");
					out.print("<tr style='font-size:25px'><td>User Name</td><td>"+rs.getString(1)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>first Name</td><td>"+rs.getString(2)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>Last Name </td><td>"+rs.getString(3)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>Password</td><td>"+rs.getString(4)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>Account No</td><td>"+rs.getString(11)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>Email</td><td>"+rs.getString(5)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>Number</td><td>"+rs.getString(6)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>Aadhar NO.</td><td>"+rs.getString(8)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>PAN NO</td><td>"+rs.getString(9)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>Age</td><td>"+rs.getString(10)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>Address </td><td>"+rs.getString(12)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>Country </td><td>"+rs.getString(14)+"</td></tr>");
					out.print("<tr style='font-size:25px'><td>State</td><td>"+rs.getString(15)+"</td></tr>");
					
					
					java.sql.Date dd=rs.getDate(13);
					java.util.Date d=(java.util.Date)dd;
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String s=sdf.format(d);
					out.print("<tr style='font-size:25px'><td>DOB</td><td>"+s+"</td></tr>");
					out.print("<table>");
					out.print("</center></body></html>");
					
				}
				} catch (Exception e) {
				e.printStackTrace();
				}
		}
		else
		{
			out.print("<center><h1>Login first to see the profile</h1></center>");
			RequestDispatcher disp=request.getRequestDispatcher("index.html");
			disp.include(request, response);
		}
	}


}
