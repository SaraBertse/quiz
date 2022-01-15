/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package examples;

import java.util.ArrayList;
import model.Result;
import model.DbHandler;
import model.Question;
import model.Quiz;

import org.springframework.stereotype.Controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping; // for mapping request path to invoking method
import org.springframework.web.bind.annotation.RequestMethod; // for mapping request method (GET, POST) to controller method 
import org.springframework.web.bind.annotation.RequestParam; // for retrieving HTTP parameters sent (GET, POST)
import org.springframework.web.bind.annotation.SessionAttributes; // same as setattribute on a HttpSession-object
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author HP
 */
@Controller
public class QuizServlet {
    int quizType = 0;
    ArrayList<Question> questions;
    
    @RequestMapping(value = "/QuizServlet", method = RequestMethod.POST)
    public String submit(@RequestParam("action") String action, 
            ModelMap model//, @RequestParam("quiz") Quiz quiz
    ){
        DbHandler dbh = new DbHandler();
        //String action = "temp"; // temporary
        
        if ("SelectQuiz1".equals(action)) {
            quizType = 1;
            questions = dbh.getQuestions(quizType);
            model.addAttribute("questions", questions);
            //session.setAttribute("questions", questions);
            //rd.forward(request, response);
            return "quiz1.html";
        } 
        else if ("SelectQuiz2".equals(action)) {
            quizType = 2;
            questions = dbh.getQuestions(quizType);
            return "quiz2.html";
        }
        else if ("submitQuiz".equals(action)){
            /*int[] q1answers = new int[3];
            int[] q2answers = new int[3];
            int[] q3answers = new int[3];*/
            String attr = "";
            int[][] qAnswers = new int[3][3];
            
            /*
            for(int i = 1; i < 4; i++){
                for(int j = 1; j < 4; j++){
                    if(!(boxes("q" + i + "b" + j)) == null){
                        qAnswers[i-1][j-1] = boxes("q" + i + "b" + j);
                    }
                    attr = "q" + i + "b" + j;
                    if(!(quiz.attr) == null){
                        qAnswers[i-1][j-1] = 1;
                    }
                    else{
                        qAnswers[i-1][j-1] = 1;
                    }
                }
            }*/
            String boxname= "";
            for(int i = 1; i < 4; i++){
                for(int j = 1; j < 4; j++){
                    boxname = "q"+i+"b"+j;
                    if(!(boxes(boxname) == null)){
                        qAnswers[i-1][j-1] = 1;
                    }
                    else{
                        qAnswers[i-1][j-1] = 0;
                    }
                }
            }
            
            
            int points = 0;
            for(int i = 0; i < 3; i++){
                boolean solvedQuestion = true;
                for(int j = 0; j < 3; j++){
                    if(!(Integer.parseInt(questions.get(i).getCorrect()[j]) == qAnswers[i][j])){
                        solvedQuestion = false;
                    }
                }
                if(solvedQuestion) { points++; }
                
            }
            model.addAttribute("points", points);
            return "quizResult.html";
            /*
            String usern = (String)application.getAttribute("username");
            dbh.setPoints(dbh.getUserID(usern), quizType, points);
            
            RequestDispatcher rd = request.getRequestDispatcher("/quizResult.jsp");
            ArrayList<Result> pointsHistory; // if default num shows up something is wrong
            pointsHistory = dbh.getResults(quizType, usern);
            session.setAttribute("quiz" + quizType + "History", pointsHistory);
            
            session.setAttribute("points", points);
            rd.forward(request, response);
*/
            
        }
        // activates when pressing "Back" in quizResult
        else if ("toMain".equals(action)) {
            return "mainMenu.html";
        }
        
        
        return "mainMenu.html";
        //processRequest(request, response);
    }
    
    public String boxes(@RequestParam("name") String value){ //Question q
        
        if(value == null)
            return null;
        return value;
    }

}