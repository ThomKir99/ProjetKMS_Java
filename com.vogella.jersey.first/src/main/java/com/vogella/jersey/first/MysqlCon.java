package com.vogella.jersey.first;

import java.sql.*;
 
class MysqlCon{  

	
	public static ResultSet main(){  

		try{   
			Connection con=DriverManager.getConnection(  
					"C:\\Users\\admin\\Documents\\MySQLServer\\data\\sys","root","");  
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from Entity;");  
			while(rs.next()) {
				System.out.println(rs.getString(1)+"  "+rs.getInt(2));  
				con.close(); 
			}
				return rs; 
		}catch(Exception e){ System.out.println(e);}
		return null;  
	}  
}  
