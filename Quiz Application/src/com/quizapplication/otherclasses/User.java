package com.quizapplication.otherclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String city;
	private String email;
	private String mobileNumber;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public static boolean checkRegisteredOrNot(String mobileNum, String username, String password, int option) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getDatabaseConnection();
		PreparedStatement pStatement1 = null;
		PreparedStatement pStatement2 = null;

		boolean result = false;

		try {
			pStatement1 = connection.prepareStatement("select * from students");
			pStatement2 = connection.prepareStatement("select * from admins");
			ResultSet resultSet1 = pStatement1.executeQuery();
			ResultSet resultSet2 = pStatement2.executeQuery();

			if (option == 1) {
				while (resultSet1.next()) {
					if ((resultSet1.getString(8).equals(mobileNum))
							&& (resultSet1.getString(4).equalsIgnoreCase(username))
							&& (resultSet1.getString(5).equals(password))) {
						result = true;
					}
				}
			}
			if (option == 2) {
				while (resultSet2.next()) {
					if ((resultSet2.getString(8).equals(mobileNum))
							&& (resultSet2.getString(4).equalsIgnoreCase(username))
							&& (resultSet2.getString(5).equals(password))) {
						result = true;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				pStatement2.close();
				pStatement1.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
