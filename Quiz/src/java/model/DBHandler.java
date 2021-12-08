/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sarab
 */
public class DBHandler {
       private static Connection connection;
       private PreparedStatement findUser;
       private PreparedStatement getUserCount;
       private PreparedStatement getQuestions;
       private PreparedStatement getResults;
       private PreparedStatement getUserID;
       int userCount;
       User[] users;
       ArrayList<Question> questions;
       ArrayList<Result> results;
       
       
       public DBHandler(){
           connToDB();
       }
         
       public static void connToDB(){
          try{
           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz","root", "admin");
           System.out.println("connection successful");
          }
          catch(Exception e){
                System.out.println("cannot connect");
               e.printStackTrace();
          }
       }
       
    public User[] findUsers() {
        try {
          //  connToDB();
            getUserCount = connection.prepareStatement("SELECT COUNT(*) FROM users");
            ResultSet countResult = getUserCount.executeQuery();
            countResult.next();
            userCount = countResult.getInt("COUNT(*)");
            findUser = connection.prepareStatement("SELECT * FROM users");
            //        + " WHERE username = ?");
            //findUser.setString(1, username);
            int i = 0;
           // users = new User[userCount];
            users = new User[2];
            ResultSet result = findUser.executeQuery();
            while (result.next()) {
                User u = new User();
                u.setUsername(result.getString("username"));
                u.setPassword(result.getString("password"));
                users[i] = u;
                i++;
            }

            //  password="notnull";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public ArrayList<Result> getResults(int quizType, String username) {
        // change list to ArrayList???
        results = new ArrayList<>();
        try {
            getUserID = connection.prepareStatement("SELECT id FROM users \n" +
                "WHERE username = ?");
            getUserID.setString(1, username);
            ResultSet userIDResult = getUserID.executeQuery();
            userIDResult.next();
            
            getResults = connection.prepareStatement("SELECT * FROM results\n" +
                "WHERE results.quiz_id = ? AND user_id = ?\n" +
                "ORDER BY score DESC");
            getResults.setInt(1, quizType);
            getResults.setInt(2, userIDResult.getInt("id"));
            ResultSet resultResult = getResults.executeQuery();

            while (resultResult.next()) {
                Result r = new Result();
                r.setUserID(resultResult.getInt("user_id"));
                r.setQuizType(resultResult.getInt("quiz_id"));
                r.setScore(resultResult.getInt("score"));
                results.add(r);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
    
    public List<Question> getQuestions(int quizType) {
        //    connToDB();
        questions = new ArrayList<>();
        try {
            getQuestions = connection.prepareStatement("SELECT * FROM questions "
                    + "INNER JOIN selector ON questions.id=selector.question_id "
                    + "WHERE selector.quiz_id = ?");
            getQuestions.setInt(1, quizType);
            ResultSet questionResult = getQuestions.executeQuery();

            int i = 0;
            while (questionResult.next()) {
                Question q = new Question();
                q.setText(questionResult.getString("text"));
                String options = questionResult.getString("options");
                String[] ops = options.split("/");
                q.setOptions(ops);
                String correct = questionResult.getString("answer");
                String[] corr = correct.split("/");
                q.setCorrect(corr);
                questions.add(q);
                i++;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
    

}