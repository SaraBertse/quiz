package model;

/**
 *
 * @author Sara Bertse and Jacob Dwyer
 */
public class Result {
    private int userID;
    private int quizType;
    private int score;
    
    public int getUserID(){
        return this.userID;
    }
    
    public int getQuizType(){
        return this.quizType;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public void setUserID(int userID){
        this.userID = userID;
    }
    
    public void setQuizType(int quizType){
        this.quizType = quizType;
    }
    
    public void setScore(int score){
        this.score = score;
    }
}