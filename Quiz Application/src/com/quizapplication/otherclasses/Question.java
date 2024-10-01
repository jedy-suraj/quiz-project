package com.quizapplication.otherclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Scanner;

public class Question{
	//gloabal variables 
	private String question;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private int correctOption;
	
	Scanner scanner= new Scanner(System.in);
	//Default constructor
	public Question() {
		
	}
	
	//paramterized constructor that initialiaze values to global variables 
	public Question(String ques, String option1 ,String option2 ,String option3 ,String option4 ,int correctOption){
		this.question=ques;
		this.option1=option1;
		this.option2= option2;
		this.option3=option3;
		this.option4=option4;
		this.correctOption=correctOption;
	}
	//Getters and setters
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getOption1() {
		return option1;
	}
	
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	
	public String getOption2() {
		return option2;
	}
	
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	
	public String getOption3() {
		return option3;
	}
	
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	
	public String getOption4() {
		return option4;
	}
	
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	
	public int getCorrectOption() {
		return correctOption;
	}
	
	public void setCorrectOption(int correctOption) {
		this.correctOption = correctOption;
	}
	
	
	/*
	 *  This is a method that takes input from user to add questions in the form of objects.
	 */
	
	public void addQuestionAndOptions() {
		System.out.println("Enter the question below to add in quiz : ");
		this.question= scanner.nextLine();
		System.out.println("Enter first option : ");
		this.option1= scanner.nextLine();
		System.out.println("Enter second option : ");
		this.option2= scanner.nextLine();
		System.out.println("Enter third option : ");
		this.option3= scanner.nextLine();
		System.out.println("Enter fourth option : ");
		this.option4= scanner.nextLine();
		System.out.println("Enter correct option : ");
		this.correctOption= scanner.nextInt();
	}
	
	/*
	 *  This is the method that is used to add questions and there options to database.
	 *  
	 *  Basically this method calls addQuestionAndOptions() method to get user input for adding questions and options.
	 *  
	 *  Then there is getDatabaseConnection() method from DatabaseConnection 
	 *  used to establish connection to database server.
	 *  
	 *  after establishing connection to server PreparedStatement object execute 
	 *  a query that add questions and there options to database.
	 *  
	 *  here used try and catch block to handle exception during handling of query 
	 * 
	 * */
	public static void addQuestionsToDatabase() {
		Question question= new Question();
		question.addQuestionAndOptions();
		DatabaseConnection databaseConnection =new DatabaseConnection();
		Connection connection = databaseConnection.getDatabaseConnection();
		PreparedStatement pStatement= null;
		try {
			pStatement = connection.prepareStatement("insert into quiz(Question ,Option1, Option2 ,Option3 ,Option4 ,CorrectOption) values(?,?,?,?,?,?)");
			
			pStatement.setString(1, question.getQuestion());
			pStatement.setString(2, question.getOption1());
			pStatement.setString(3, question.getOption2());
			pStatement.setString(4, question.getOption3());
			pStatement.setString(5, question.getOption4());
			pStatement.setInt(6, question.getCorrectOption());
			
			pStatement.execute();
			System.out.println("Question Added Successfully");
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pStatement.close();
				connection.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 	This is method that has return type HashSet<Question> and returns hashset.
	 * 
	 * 	Firstly This method establish connection with database using getDatabaseConnection() from DatabaseConnection.
	 * 
	 * 	Then this Method runs a query to store records from database as an object of Question class in hashset.
	 * 
	 * 	After adding all the records in hashset  it will return the hashset.
	 * 
	 * 	Here the collection hashset is used because i want to display quiz question in random order.
	 * 	As we know hashset dont maintain insertion order , it randomly store object in collection 
	 * 	so that is the reason behind using this collection here.
	 */
	public static HashSet<Question> storeQuestionTohashset() {
		HashSet<Question> hSet = new HashSet<Question>();

		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getDatabaseConnection();
		PreparedStatement pStatement = null;

		try {
			pStatement = connection.prepareStatement("Select * from quiz");
			ResultSet resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				String ques = resultSet.getString(2);
				String option1 = resultSet.getString(3);
				String option2 = resultSet.getString(4);
				String option3 = resultSet.getString(5);
				String option4 = resultSet.getString(6);
				int correctOption = resultSet.getInt(7);

				hSet.add(new Question(ques, option1, option2, option3, option4, correctOption));
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
		return hSet;
	}
	
	/*
	 * 	This is integer method that display question and 
	 * 	option to user and ask user to give answer and return score of quiz.
	 * 
	 * 	Firstly it call storeQuestionTohashset() Method and store return collection in hashset of Type Question.
	 * 
	 * 	Then it display questions and options to user ask user about there answers.
	 * 
	 * 	As user enter answer, this method comapare user's answer with correctoption.
	 * 
	 * 	if answer is right then it increase score by 1 because each is of one mark.
	 * 
	 */
	public static int startQuiz() {
		int score = 0;
		Scanner scanner = new Scanner(System.in);
		HashSet<Question> hSet = storeQuestionTohashset();
		int counter = 1;
		for (Question question : hSet) {
			System.out.println(counter + ". " + question.getQuestion() + " ?\n" + "   1. " + question.getOption1()
					+ "\n   2. " + question.getOption2() + "\n   3. " + question.getOption3() + "\n   4. "
					+ question.getOption4() + "\n");
			counter = counter + 1;

			System.out.print("Enter Answer : ");
			int answer = scanner.nextInt();
			if (answer == question.getCorrectOption()) {
				score = score + 1;
			}
			System.out.println();
		}
		return score;
	}

	
}
