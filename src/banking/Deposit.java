package banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DBAAccess;

public class Deposit extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		double value=0.0;
		double balance=Double.parseDouble(request.getParameter("data"));
		HttpSession sc=request.getSession(false);
		if(sc!=null)
		{
			String name=(String)sc.getAttribute("name");
			try {
				
				Connection cn=DBAAccess.getConn();
				PreparedStatement ps=cn.prepareStatement("select balance from banking where username=?");
				ps.setString(1, name);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					value=Double.parseDouble(rs.getString(1));
				}
				
				value=value+balance;
				ps=cn.prepareStatement("update banking set balance="+value+"where username=?");
				ps.setString(1, name);
				int i=ps.executeUpdate();
				if(i==1)
				{
					String s="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
					Random r=new Random();
					StringBuffer bf=new StringBuffer();
					for(int j=1;j<=8;j++)
					{
						bf.append(s.charAt(r.nextInt(s.length())));
					}
					String id=bf.toString();
					String type="credited";
					ps=cn.prepareStatement("insert into transaction values(?,?,?,?,?)");
					ps.setString(1, name);
					ps.setString(2, id);
					ps.setString(3, type);
					ps.setDouble(4, balance);
					ps.setString(5, "self");
					ps.executeUpdate();
					out.print("<center><h3>Amount "+balance+" Deposited successfully.</h3></center>");
					RequestDispatcher disp=request.getRequestDispatcher("Deposit.html");
					disp.include(request, response);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			out.print("<center><h1>Login first to Deposit</h1></center>");
			RequestDispatcher disp=request.getRequestDispatcher("index.html");
			disp.include(request, response);
		}
	}

}
