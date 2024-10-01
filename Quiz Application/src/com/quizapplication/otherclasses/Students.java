package com.quizapplication.otherclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import com.quizapplication.exceptionclasses.DuplicateMobileNumberException;
import com.quizapplication.interfaces.StudentRegistration;

/* 
 * This is child class of User and inherit all properties of User class.
 * 
 * It also implements an interface StudentRegistration.
 * 
 * */
public class Students extends User implements StudentRegistration {

	/*
	 * This is the method that is used take input from user for registering students
	 * on Quiz application.
	 *
	 * This method is implemented from StudentRegistration interface.
	 */
	@Override
	public void registerStudent() {

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

	}

	/*
	 * This is the method that displays First name, Last Name , and id of user by
	 * accessing database.
	 * 
	 * Firstly this method establish connection by using getDatabaseConnection()
	 * from DatabaseConnection class
	 * 
	 * After establishing connection with database server it it execute a simple
	 * query that give First name, last name, and id of students from record in
	 * database table.
	 * 
	 * Here also try catch clock used to handle exceptions.
	 */
	public static void displayAllStudentsNameWithId() {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getDatabaseConnection();
		PreparedStatement pStatement = null;

		try {
			pStatement = connection.prepareStatement("select id,FirstName, LastName from students");
			ResultSet resultSet = pStatement.executeQuery();

			System.out.println(" ID " + "\t     Name");
			while (resultSet.next()) {
				System.out.println(" " + resultSet.getInt(1) + " " + "\t " + resultSet.getString(2) + " "
						+ resultSet.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pStatement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * This method is created for admin operations. This method is used to fetch
	 * score of quiz from database by using students id.
	 * 
	 * Firstly it calls displayAllStudentsNameWithId() that displays first name ,
	 * last name and id to admin.
	 * 
	 * Then this method ask admin for student id to get score of student in quiz.
	 * 
	 * After that this method establish connection by using getDatabaseConnection()
	 * from DatabaseConnection class.
	 * 
	 * After establishing connection with database server it simply fetch the score
	 * with help of id by executing sql query.
	 * 
	 * Then it print fetched score for that id.
	 * 
	 * try and catch blocks used for handling exception.
	 * 
	 */
	public static void fetchStudentScoreById() {
		displayAllStudentsNameWithId();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter id of student to get score");
		int id = scanner.nextInt();

		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getDatabaseConnection();
		PreparedStatement pStatement = null;

		try {
			pStatement = connection.prepareStatement("select score from students where id = ?");
			pStatement.setInt(1, id);

			ResultSet resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("score : " + resultSet.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pStatement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * This method is created for admin operations.
	 *
	 * This method is designed for printing scores of all students in descending
	 * order or in ascending order.
	 * 
	 * Firstly this method establish connection by using getDatabaseConnection()
	 * from DatabaseConnection class.
	 * 
	 * Then it execute two queries . one gives total number of records for giving
	 * size of array. another gives scores of all records.
	 * 
	 * Then the scores of all records is stored in array.
	 * 
	 * Then array is sorted in ascending order by using sort function.
	 * 
	 * Then if option is 1 then array printed in ascending order if option is 2 then
	 * array printed in descending order.
	 * 
	 * Here try catch blocks also used for exception handling.
	 * 
	 */
	public static void displayAllScoreInAnyOrder(int option) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getDatabaseConnection();
		PreparedStatement pStatement1 = null;
		PreparedStatement pStatement2 = null;
		int size = 0;
		int counter = 0;
		try {
			pStatement1 = connection.prepareStatement("select count(*) as totalrecords from students");
			pStatement2 = connection.prepareStatement("select score from students");
			ResultSet resultSet1 = pStatement1.executeQuery();
			ResultSet resultSet2 = pStatement2.executeQuery();

			while (resultSet1.next()) {
				size = resultSet1.getInt("totalrecords");
			}
			int arr[] = new int[size];

			while (resultSet2.next()) {
				arr[counter] = resultSet2.getInt(1);
				counter = counter + 1;
			}

			Arrays.sort(arr);
			if (option == 1) {
				System.out.println("Score by ascending order : ");

				for (int i = 0; i < arr.length; i++) {
					System.out.print(arr[i] + " ");
				}
			} else if (option == 2) {
				System.out.println("Score by Descending order : ");

				for (int i = arr.length - 1; i >= 0; i--) {
					System.out.print(arr[i] + " ");
				}
			}else {
				System.out.println("You enter wrong option.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pStatement2.close();
				pStatement1.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/*
	 *	This is the method designed for adding registerd student details in database.
	 *
	 *	Firstly it called registerStudent() of student class for student details as user input.
	 *	
	 *	Then this method establish connection by using getDatabaseConnection()
	 * 	from DatabaseConnection class.
	 * 
	 * 	Then it executes two queries.
	 * 
	 * 	One query checks the mobile number given by user present in database or not.
	 * 	
	 * 	another query is used for adding record in database. 
	 * 
	 * 	if there mobile number already present in database then this method wont add student data to database.
	 * 
	 * 	if mobile number is not present in database then method add student data in to database.
	 * 
	 * 	This method use try catch block for handling exception.
	 * 
	 * 	In this method custom DuplicateMobileNumberException exception is handled 
	 *
	 * */
	public static void addStudentDetailsIntoDatabase () throws SQLException {
		Students student = new Students();
		student.registerStudent();
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getDatabaseConnection();
		PreparedStatement pStatement1 = null;
		PreparedStatement pStatement2 = null;
		boolean alreadyRegistered = false;

		try {
			pStatement1 = connection.prepareStatement("insert into students(FirstName ,LastName ,"
					+ "UserName,UserPassword ,city ,Email,MobileNumber) " + "values (?,?,?,?,?,?,?) ");
			pStatement2 = connection.prepareStatement("select * from students");
			pStatement1.setString(1, student.getFirstName());
			pStatement1.setString(2, student.getLastName());
			pStatement1.setString(3, student.getUserName());
			pStatement1.setString(4, student.getPassword());
			pStatement1.setString(5, student.getCity());
			pStatement1.setString(6, student.getEmail());
			pStatement1.setString(7, student.getMobileNumber());

			ResultSet resultSet = pStatement2.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString(8).equals(student.getMobileNumber())) {
					alreadyRegistered = true;
				}
			}
			if (alreadyRegistered) {
				throw new DuplicateMobileNumberException();
			} else {
				pStatement1.execute();
				System.out.println("Added");
			}
		} catch (DuplicateMobileNumberException e) {
			e.fillInStackTrace();
		} finally {
			pStatement2.close();
			pStatement1.close();
			connection.close();

		}
	}

	/*
	 * 	This is the method that displays Quiz result.
	 * 	Firstly this method establish connection by using getDatabaseConnection()
	 * 	from DatabaseConnection class.
	 * 
	 * 	Then it runs a query that gives score of quiz according to mobile number given at the time of registration.
	 * 
	 * 	This method uses try and catch blocks for handling exceptions.
	 * 	
	 * */
	public static void displayQuizResult(String mobileNum) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getDatabaseConnection();
		PreparedStatement pStatement = null;

		try {
			pStatement = connection.prepareStatement("select score from students where MobileNumber= ?");
			pStatement.setString(1, mobileNum);

			ResultSet resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("Your score is : " + resultSet.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pStatement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * This method is used for storing individuals score in database after quiz completion.
	 * Firstly this method establish connection by using getDatabaseConnection()
	 * from DatabaseConnection class.
	 * 
	 * Then it executes a query that stores score of students according Mobile number.
	 * 
	 * because of mobile number is unique number so it get easy to find record of student and set its score.
	 * 
	 * This method uses try and catch block for handling exceptions.
	 * 
	 * */
	public static void storeQuizResult(String mobileNum, int score) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getDatabaseConnection();
		PreparedStatement pStatement = null;

		try {
			pStatement = connection.prepareStatement("update students set score = ? where MobileNumber = ?");
			pStatement.setInt(1, score);
			pStatement.setString(2, mobileNum);

			pStatement.execute();
			System.out.println("Successfully stored.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pStatement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
