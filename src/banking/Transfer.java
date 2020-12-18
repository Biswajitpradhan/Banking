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

public class Transfer extends HttpServlet {
	protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		PreparedStatement ps=null;
		boolean ck=false;
		String accn=request.getParameter("accn");
		double balance=Double.parseDouble(request.getParameter("data"));
		HttpSession session=request.getSession(false);
		double value=0.0;
		if(session!=null)
		{
			String name=(String)session.getAttribute("name");
			try {
				Connection cn=DBAAccess.getConn();
				ps=cn.prepareStatement("select acno from banking");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					if(rs.getString(1).equals(accn))
					{
						ck=true;
					}
				}
				if(ck)
				{
					ps=cn.prepareStatement("select balance from banking where username=?");
					ps.setString(1, name);
					ResultSet rs2=ps.executeQuery();
					if(rs2.next())
					{
						value=rs2.getDouble(1);
					}
					if(balance>value)
					{
						out.print("<center><h3>Insufficient balance.</h3></center>");
						RequestDispatcher disp=request.getRequestDispatcher("Transfer.html");
						disp.include(request, response);
					}
					else{
						value=value-balance;
						ps=cn.prepareStatement("update banking set balance="+value+" where username=?");
						ps.setString(1, name);
						ps.executeUpdate();
						ps=cn.prepareStatement("select balance from banking where acno=?");
						ps.setString(1, accn);
						rs=ps.executeQuery();
						if(rs.next())
						{
							value=rs.getDouble(1);
						}
						value=value+balance;
						ps=cn.prepareStatement("update banking set balance="+value+" where acno=?");
						ps.setString(1, accn);
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
							String type="transfered";
							ps=cn.prepareStatement("insert into transaction values(?,?,?,?,?)");
							ps.setString(1, name);
							ps.setString(2, id);
							ps.setString(3, type);
							ps.setDouble(4, balance);
							ps.setString(5, accn);
							ps.executeUpdate();
							
							String name1="";
							ps=cn.prepareStatement("select username from banking where acno=?");
							ps.setString(1, accn);
							rs=ps.executeQuery();
							if(rs.next())
							{
								name1=rs.getString(1);
							}
							ps=cn.prepareStatement("insert into transaction values(?,?,?,?,?)");
							ps.setString(1,name1);
							ps.setString(2, id);
							ps.setString(3,"credited");
							ps.setDouble(4,balance);
							ps.setString(5,"");
							ps.executeUpdate();
							
							
							out.print("<center><h3>Successfully transfered. "+balance+"</h3></center>");
							RequestDispatcher disp=request.getRequestDispatcher("Transfer.html");
							disp.include(request, response);
						}
					}
					
				}
				else
				{
					out.print("<center><h3>This account no. does not exist.</h3></center>");
					RequestDispatcher disp=request.getRequestDispatcher("Transfer.html");
					disp.include(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			out.print("<center><h1>Login first to Deposit</h1></center>");
			RequestDispatcher disp=request.getRequestDispatcher("index.html");
			disp.include(request, response);
		}
    }

}
