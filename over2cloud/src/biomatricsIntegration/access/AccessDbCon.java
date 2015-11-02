package biomatricsIntegration.access;


import java.sql.Connection;

import java.sql.DriverManager;


public class AccessDbCon 
{

	/**
	 * Getting Connection From Database
	 * @return Connection
	 */
	
public static Connection getCon()
{
		Connection conn = null;
		
try {
			
// Load MS accces driver class
			
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		
String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + "D:\\IBIOS_2.mdb";
 conn = DriverManager.getConnection(url, "admin", "ibios@2010");
} catch (Exception e) 
{
			

System.err.println("Got an exception! ");
			
System.err.println(e.getMessage());

			
}
		
return conn;
	
}


public static void main(String[] args) {
	System.out.println(getCon());
}
}
