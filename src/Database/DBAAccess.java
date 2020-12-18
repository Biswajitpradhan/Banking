package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBAAccess {
	static Connection cn;
	public static Connection getConn()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			if(cn==null)
			{
				cn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","Biswajit");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cn;
	}
	
}
