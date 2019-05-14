package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;

class MySqlCon {

	Connection con;
	public MySqlCon(){
	}


	public void openLocalConnection(){
		String nomBaseDonnee="";
		String motDePasse="";
	  try
	  {

		  try {
				File file = new File("config.ini");
				BufferedReader br = new BufferedReader(new FileReader(file));
				br.readLine();
				nomBaseDonnee = trim(br.readLine());
				motDePasse = trim(br.readLine());
			} catch ( IOException e) {
				nomBaseDonnee = "localhost";
				motDePasse ="root";
			}

		  Class.forName("com.mysql.jdbc.Driver");
		  con = DriverManager.getConnection("jdbc:mysql://"+nomBaseDonnee+":3306/sys","root",motDePasse);
	  }
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
	}

	private String trim(String readLine) {
		String[] infoSplit = readLine.split(":");
		String info = infoSplit[1].trim();
		return info;
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
