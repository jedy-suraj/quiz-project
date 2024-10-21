package com.quizapplication.otherclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.quizapplication.exceptionclasses.DuplicateMobileNumberException;
import com.quizapplication.interfaces.AdminRegistration;
/*
 *  This class is basically child class admin that extends from User class to inherit features of user class.
 *  
 *  This class also implement an interface AdminRegistration to implement an abstract method called registerAdmin.
 */
public class Admin extends User implements AdminRegistration {
	
	//It is a variable used to verify that in actual admin is registering
	private String adminKeycode;

	//setter
	public void setAdminKeycode(String adminKeycode) {
		this.adminKeycode = adminKeycode;
	}
	
	//getter
	public String getAdminKeycode() {
		return adminKeycode;
	}

	@Override
	
	/* 
	 * This method is implemented from AdminInterface.
	 * 
	 * Its just a method that take Admins details.
	 */
	public void registerAdmin() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your first name : ");
		this.setFirstName(scanner.nextLine());
		System.out.println("Enter your last name : ");
		this.setLastName(scanner.nextLine());
		System.out.println("Enter your user name : ");
		this.setUserName(scanner.nextLine());
		System.out.println("Enter your password : ");
		this.setPassword(scanner.nextLine());
		System.out.println("Enter your city : ");
		this.setCity(scanner.nextLine());
		System.out.println("Enter your Email : ");
		this.setEmail(scanner.nextLine());
		System.out.println("Enter your Mobile Number : ");
		this.setMobileNumber(scanner.nextLine());
		System.out.println("Enter Admin Key Code : ");
		this.setAdminKeycode(scanner.nextLine());

	}

	/* 
	 *  This is method that help to add admin details to database.
	 *   
	 *  Basically this method first take admin details by  calling  registerAdmin() method.
	 *  Then it makes connection with database by calling getDatabaseConnection() from DatabaseConnection class.
	 *  After making connection it runs a query with dynamic input.
	 * 
	 *  Before adding admin to database it check mobile number given by user present in database or not.
	 * 
	 *  If mobile number already present in database then it simply 
	 *  throw an exception called DuplicateMobileNumberException.
	 *  
	 *  Else it move forward and check adminkeycode to ensure that the user that registering is official person.
	 *  
	 *  If user enter correct adminkeycode then user is added in database as admin.
	 *  
	 *  No one can register without correct adminkeycode as admin.
	 *   
	 */
	
	public static void addAdminDetailsToDatabase() throws SQLException {
		Admin admin = new Admin();
		admin.registerAdmin();
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getDatabaseConnection();
		PreparedStatement pStatement1 = null;
		PreparedStatement pStatement2 = null;
		boolean alreadyRegistered = false;

		try {
			pStatement1 = connection.prepareStatement("insert into admins(FirstName ,LastName ,"
					+ "UserName,UserPassword ,city ,Email,MobileNumber) " + "values (?,?,?,?,?,?,?) ");
			pStatement2 = connection.prepareStatement("select * from admins");
			pStatement1.setString(1, admin.getFirstName());
			pStatement1.setString(2, admin.getLastName());
			pStatement1.setString(3, admin.getUserName());
			pStatement1.setString(4, admin.getPassword());
			pStatement1.setString(5, admin.getCity());
			pStatement1.setString(6, admin.getEmail());
			pStatement1.setString(7, admin.getMobileNumber());

			ResultSet resultSet = pStatement2.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString(8).equals(admin.getMobileNumber())) {
					alreadyRegistered = true;
				}
			}
			if (alreadyRegistered) {
				throw new DuplicateMobileNumberException();
			} else {
				if (admin.getAdminKeycode().equals("QuizAdmin2024")) {
					pStatement1.execute();
					System.out.println("Added");
				} else {
					System.out.println("You entered wrong Admin Keycode");
				}
			}
		} catch (DuplicateMobileNumberException e) {
			e.printStackTrace();
		} finally {
			pStatement2.close();
			pStatement1.close();
			connection.close();

		}
	}

}
