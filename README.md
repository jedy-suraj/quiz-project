# Console-Based Quiz Application

<p>This is a console-based Quiz Application developed using Core Java, JDBC, and a MySQL database.<br>
  The application allows students to register, login, take quizzes, and view their results.</p>
<br>

<h3>Features</h3> 

1.User Registration: User can create account as student or as admin. <br>
Student or Admin cannot register with mobile number that already present in data base.<br>
(Admin need a keycode to register as admin)
2.User Login: User can login as student or as admin. 
3.Student Login: Student can login with there credentials.(mobile number , username , password)
4.Admin Login: Admin can login with there credentials.(mobile number, username, password)

When User Login as Student : <br>
5.Start Quiz: Student can attempt quiz and store result.
6.display Result: Student can see the result of last attempted quiz whenever he want.

When User Login as Admin : <br>
7.Display all students score in Order: Admin can see all Students score in any order(ascending or descending).
8.Fetch student score by using id: Admin can fetch score of any student by using student id.
9.Add question with 4 options into database: Admin can add question in Quiz with 4 options.
10.Get student Name: Admin can fetch student full name and id using this option.
<br>
<br>
<br>
<br>


<h3>Technologies Used</h3>

1.Core Java: Application logic and functionalities.
2.JDBC (Java Database Connectivity): Connecting and interacting with the MySQL database.
3.MySQL Database: Storing student data, quiz questions, and quiz results.
<br>
<br>
<br>
<br>
<h3>Prerequisites</h3>

1.JDK 8 or higher
2.MySQL installed and running
3.JDBC Driver (MySQL Connector)

