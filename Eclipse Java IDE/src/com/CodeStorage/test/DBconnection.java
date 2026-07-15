package com.CodeStorage.test;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {
	public static Connection getConnection() {
		Connection con=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Eclipse_IDE","root","sohamk");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}
