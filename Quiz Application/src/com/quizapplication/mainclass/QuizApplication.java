package com.quizapplication.mainclass;

import java.sql.SQLException;

import java.util.Scanner;

import com.quizapplication.exceptionclasses.InvalidChoiceException;
import com.quizapplication.otherclasses.Admin;
import com.quizapplication.otherclasses.Question;
import com.quizapplication.otherclasses.Students;
import com.quizapplication.otherclasses.User;

public class QuizApplication {

	public static void main(String[] args) {

		System.out.println(
				"Welcome to Console based Quiz Application\n\n" + "1. User Registration\n" + "2. User Login\n");

		int option = 0;
		System.out.print("Enter your choice from above : ");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();

		switch (choice) {

		case 1:
			System.out.println("Who you are : 1) Student 2) Admin\n");
			System.out.print("Enter 1 If You are Student Enter 2 Incase Of Admin : ");
			option = scanner.nextInt();
			if (option == 1) {
				try {
					Students.addStudentDetailsIntoDatabase();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (option == 2) {
				try {
					Admin.addAdminDetailsToDatabase();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("INVALID OPTION");
			}

			break;

		case 2:
			System.out.println("Who you are : 1) Student 2) Admin\n");
			System.out.print("Enter 1 If You are Student Enter 2 Incase Of Admin : ");
			option = scanner.nextInt();
			scanner.nextLine();
			if (option == 1) {
				System.out.println("Enter your mobile number : ");
				String mobileNum = scanner.nextLine();
				System.out.println("Enter your user name : ");
				String username = scanner.nextLine();
				System.out.println("Enter your password : ");
				String password = scanner.nextLine();

				if (User.checkRegisteredOrNot(mobileNum, username, password, option)) {
					System.out
							.println("\n**Students Operations**\n\n" + "1. Start Quiz\n" + "2. Display Quiz result\n");
					System.out.print("Enter your choice : ");
					option = scanner.nextInt();

					switch (option) {
					case 1:
						int score = Question.startQuiz();

						System.out.print("1. Store Quiz result into database (Enter '1' to store) : ");
						option = scanner.nextInt();
						if (option == 1) {
							Students.storeQuizResult(mobileNum, score);
						} else {
							System.out.println("Thank you for attempting quiz");
						}

						break;

					case 2:
						Students.displayQuizResult(mobileNum);
						break;

					default:
						try {
							throw new InvalidChoiceException("Enter choice from menu");
						} catch (InvalidChoiceException e) {
							e.printStackTrace();
						}
					}

				} else {
					System.out.println("Either you entered wrong details else you are not registered with us.");
				}

			} else if (option == 2) {
				System.out.println("Enter your mobile number : ");
				String mobileNum = scanner.nextLine();
				System.out.println("Enter your user name : ");
				String username = scanner.nextLine();
				System.out.println("Enter your password : ");
				String password = scanner.nextLine();

				if (User.checkRegisteredOrNot(mobileNum, username, password, option)) {
					System.out.println(
							"\n**Admin Operations**\n\n" + "1. Display all students score as per ascending order or descending order \n"
									+ "2. Fetch student score by using id\n"
									+ "3. Add question with 4 options into database\n" + "4. Get student Name\n");

					System.out.print("Enter your choice : ");
					option = scanner.nextInt();

					switch (option) {
					case 1:
						System.out.println("1. Ascending Order \n" + "2. Descending order\n");
						System.out.print("Enter option : ");
						option= scanner.nextInt();
						Students.displayAllScoreInAnyOrder(option);
						break;

					case 2:
						Students.fetchStudentScoreById();
						break;

					case 3:
						Question.addQuestionsToDatabase();
						break;

					case 4:
						Students.displayAllStudentsNameWithId();
						break;

					default:
						try {
							throw new InvalidChoiceException("Enter choice from menu");
						} catch (InvalidChoiceException e) {
							e.printStackTrace();
						}
					}
				} else {
					System.out.println("Either you entered wrong details else you are not registered with us.");
				}
			} else {
				System.out.println("INVALID OPTION");
			}

			break;

		default:
			try {
				throw new InvalidChoiceException("Enter choice from menu");
			} catch (InvalidChoiceException e) {
				e.printStackTrace();
			}
			
		}

	}
}
