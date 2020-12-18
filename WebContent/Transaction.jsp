<!DOCTYPE html>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="Database.DBAAccess"%>
<%@page import="java.sql.Connection"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" href="Transaction.css" type="text/css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
</head>
<body>
    <div id="container">
        <nav id="my-nav">
            <h2 id="bank"><a href="#">JT Bank</a></h2>
           <ul>
                <li><a href="Home.html">Home</a></li>
                <li><a href="Withdraw.html">Withdraw</a></li>
                <li><a href="Enquiry">Enquiry</a></li>
                <li><a href="Deposit.html">Deposit</a></li>
                <li><a href="Transfer.html">Transfer</a></li>
                <li><a href="Transaction.jsp">Transaction</a></li>
            </ul>
            <h3 id="log"><a href="Logout">Logout</a></h3>
            <h3 id="log"><a href="Profile">Profile</a></h3>
        </nav>
        <div id="content">
            <h3>
                Transaction
            </h3>
            <div id="data">
                <table id="my-table">
                    <tr>
                        <th>Username</th>
                        <th>Transaction_ID</th>
                        <th>Transaction_Type</th>
                        <th>Amount</th>
                        <th>Send To</th>
                    </tr>
                    <%
                    	String name=(String)session.getAttribute("name");
                    	Connection cn=DBAAccess.getConn();
                    	PreparedStatement pr=cn.prepareStatement("select * from Transaction where username=?");
                    	pr.setString(1, name);
                    	ResultSet rs=pr.executeQuery();
                    	while(rs.next())
                    	{
                    %>
                    <tr>
                        <td><%=rs.getString(1) %></td>
                        <td><%=rs.getString(2) %></td>
                        <td><%=rs.getString(3) %></td>
                        <td><%=rs.getString(4) %></td>
                        <td><%=rs.getString(5) %></td>
                    </tr>
                   <%
                    	}
                   %>
                </table>
            </div>
        </div>
    </div>
</body>
</html>