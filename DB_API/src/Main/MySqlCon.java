package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;

class MySqlCon {

	Connection con;
	public MySqlCon(){
	}


	public void openLocalConnection(){
	  try
	  {
		  Class.forName("com.mysql.jdbc.Driver");
		  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys","root","");
	  }
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
	}

	public void closeConnection(){
		try{
			con.close();
		}
		catch (Exception e){
			System.out.println(e);
		}
	}

	public ResultSet getQueryResult(String query) {
		Statement stmt = null;
		ResultSet rs = null;

		try{
		  stmt = con.createStatement();
		  rs = stmt.executeQuery(query);
		}
		catch(Exception e){
			System.out.println(e);
		}

		 return rs;

	}

	public void executeNonQuery(String query) {
		Statement stmt = null;

		try{
		  stmt = con.createStatement();
		  stmt.executeUpdate(query);
		}
		catch(Exception e){
			System.out.println(e);
		}


	}


}
