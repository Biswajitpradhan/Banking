package banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.DBAAccess;


public class Register extends HttpServlet {
	static Connection cn;
	static PreparedStatement pr;
	
	public void init()throws ServletException
	{
		try {
			cn=DBAAccess.getConn();
		} 
		catch(Exception f){
			f.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String name=request.getParameter("user");
		String password=request.getParameter("pass");
		String email=request.getParameter("email");
		String pnumber=request.getParameter("number");
		String aadhar=request.getParameter("aadhar");
		String pan=request.getParameter("pan");
		String first=request.getParameter("first");
		String last=request.getParameter("last");
		String age=request.getParameter("age");
		String country=request.getParameter("country");
		String state=request.getParameter("state");
		String dob=request.getParameter("dob");
		String address=request.getParameter("address");
		String ac="0123456789";
		Random rr=new Random();
		StringBuffer b=new StringBuffer();
		for(int i=1;i<=11;i++)
		{
			b.append(ac.charAt(rr.nextInt(ac.length())));
		}
		
		String acno=b.toString();
		double amount=0.00;
		boolean ck=true;
		try {
			
			pr=cn.prepareStatement("select username from Banking");
			ResultSet rs=pr.executeQuery();
			while(rs.next())
			{
				if(rs.getString(1).equals(name))
				{
					ck=false;
					break;
				}
			}
			
			
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d=sdf.parse(dob);
			java.sql.Date sdob=new java.sql.Date(d.getTime());
			
			java.util.Date dd=new java.util.Date();
			SimpleDateFormat adf=new SimpleDateFormat("yyyy-MM-dd");
			String s=adf.format(dd);
			
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d1=sdf.parse(s);
			java.sql.Date open=new java.sql.Date(d1.getTime());
			
			if(ck)
			{
				pr=cn.prepareStatement("insert into Banking values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//				pr=cn.prepareStatement("insert into login values(?,?)");
				pr.setString(1, name);
				pr.setString(2, first);
				pr.setString(3, last);
				pr.setString(4, password);
				pr.setString(5, email);
				pr.setString(6, pnumber);
				pr.setDouble(7, amount);
				pr.setString(8, aadhar);
				pr.setString(9, pan);
				pr.setString(10, age);
				pr.setString(11, acno);
				pr.setString(12, address);
				pr.setDate(13, sdob);
				pr.setString(14, country);
				pr.setString(15, state);
				pr.setDate(16, open);
				int i=pr.executeUpdate();
				cn.commit();
				if(i==1)
				{
					out.print("<center><h1 style='margin-top:200px'>Username :"+name+"</h1></center>");
					out.print("<center><h1 style='margin-top:20px'>Account No. :"+acno+"</h1></center>");
					out.print("<center><h1 ><a style='text-decoration:none;color:gray'  href='index.html'>Login to Continue</a></h1></center>");
//					RequestDispatcher req=request.getRequestDispatcher("index.html");
//					req.forward(request, response);
				}
			}
			else
			{
				out.print("<center><h3>Hello ."+name+" Username Exists</h3></center>");
				out.print("<center><h3>Try With Different User ID</h3></center>");
				RequestDispatcher req=request.getRequestDispatcher("signup.html");
				req.include(request, response);	
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
