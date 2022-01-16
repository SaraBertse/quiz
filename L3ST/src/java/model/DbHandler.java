package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author sarab
 */
public class DbHandler {
       private static Connection connection;
       private PreparedStatement findUser;
       private PreparedStatement getUserCount;
       private PreparedStatement getQuestions;
       private PreparedStatement getResults;
       private PreparedStatement getUserID;
       private PreparedStatement setPoints;
       int userCount;
       User[] users;
       ArrayList<Question> questions;
       ArrayList<Result> results;
       
       
       public DbHandler(){
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
            getUserCount = connection.prepareStatement("SELECT COUNT(*) FROM users");
            ResultSet countResult = getUserCount.executeQuery();
            countResult.next();
            userCount = countResult.getInt("COUNT(*)");
            findUser = connection.prepareStatement("SELECT * FROM users");
            
            int i = 0;
            users = new User[2];
            ResultSet result = findUser.executeQuery();
            while (result.next()) { // skips null first line
                User u = new User();
                u.setUsername(result.getString("username"));
                u.setPassword(result.getString("password"));
                users[i] = u;
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public ArrayList<Result> getResults(int quizType, String username) {
        results = new ArrayList<>();
        try {
            /*getUserID = connection.prepareStatement("SELECT id FROM users \n" +
                "WHERE username = ?");
            getUserID.setString(1, username);
            ResultSet userIDResult = getUserID.executeQuery();
            userIDResult.next();*/
            int userID = getUserID(username);
            
            getResults = connection.prepareStatement("SELECT * FROM results\n" +
                "WHERE results.quiz_id = ? AND user_id = ?\n" +
                "ORDER BY score DESC");
            getResults.setInt(1, quizType);
            getResults.setInt(2, userID);
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
    
    public int getUserID(String username){
        int uID = -1;
        
        try{
            getUserID = connection.prepareStatement("SELECT id FROM users \n" +
                "WHERE username = ?");
            getUserID.setString(1, username);
            ResultSet userIDResult = getUserID.executeQuery();
            userIDResult.next();
            uID = userIDResult.getInt("id");
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return uID;
    }
    
    public ArrayList<Question> getQuestions(int quizType) {
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
    
    public void setPoints(int userID, int quizID, int score){
        try{
            setPoints = connection.prepareStatement("INSERT INTO results"
            + " (user_id, quiz_id, score) VALUES (?, ?, ?)");
            setPoints.setInt(1, userID);
            setPoints.setInt(2, quizID);
            setPoints.setInt(3, score);
            
            int updatedRows = setPoints.executeUpdate();
            if (updatedRows != 1) {
                //handleException(failureMsg, null);
                System.out.println("update rows failed");
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    

}