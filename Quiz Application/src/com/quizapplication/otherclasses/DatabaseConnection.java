package com.quizapplication.otherclasses;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	/*
	 * This is the class is used to make connection with database.
	 * 
	 * This class has single method getDatabaseConnection() method.
	 * 
	 * This method firstly loads the Driver class from 'com.mysql.cj.jdbc' package.
	 * Then makes connections with mysql server by calling getConnection(url,username,password) 
	 * 
	 * method form DriverManager class.
	 */
	public Connection getDatabaseConnection(){
		
		Connection connection= null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapplication","root","JETTas@1103");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}
