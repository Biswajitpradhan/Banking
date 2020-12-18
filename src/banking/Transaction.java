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

public class Transaction extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession(false);
		if(session!=null)
		{
			String name=(String)session.getAttribute("name");
			try {
				Connection cn=DBAAccess.getConn();
				PreparedStatement pr=cn.prepareStatement("select * from Transaction where username=?");
				pr.setString(1, name);
				ResultSet rs=pr.executeQuery();
				out.print("<html><head><style>#my-table{border-collapse:collapse;height:40px;width:300px;}#my-table td{padding-right:8px}</style></head><body>");
				out.print("<a href='Home.html'>Back</a>");
				out.print("<center><table style='margin-top:70px'><tr><th>Name</th><th>Transaction ID</th><th>Type</th><th>Amount</th><th>Send To</th></tr></table></center>");
				while(rs.next())
				{
					out.println("<center><table id='my-table' ><tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getDouble(4)+"</td><td>"+rs.getString(5)+"</td></tr><br>");
					out.println("</table></center></body></html>");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else
		{
			out.print("<center><h1>Login first to see your transaction.</h1></center>");
			RequestDispatcher disp=request.getRequestDispatcher("index.html");
			disp.include(request, response);
		}
	}
}
