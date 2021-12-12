package model;

public class Question {

    private String text;
    private String[] options;
    private String[] correct;
    private int pos;
    
    public Question(){
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public void setOptions(String[] options){
        this.options = options;
    }
    
    public void setCorrect(String[] correct){
        this.correct = correct;
    }
    
    public String getText(){
        return this.text;
    }
    
    public String[] getOptions(){
        return this.options;
    }
    
    public String[] getCorrect(){
        return this.correct;
    }
}