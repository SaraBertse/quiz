/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package examples;

import static examples.UserServlet.userName;
import java.util.ArrayList;
import model.Result;
import model.DbHandler;
import model.Question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping; // for mapping request path to invoking method
import org.springframework.web.bind.annotation.RequestMethod; // for mapping request method (GET, POST) to controller method 
import org.springframework.web.bind.annotation.RequestParam; // for retrieving HTTP parameters sent (GET, POST)

/**
 *
 * @author Sara Bertse and Jacob Dwyer
 */
@Controller
public class QuizServlet {

    int quizType = 0;
    ArrayList<Question> questions;

    @RequestMapping(value = "/QuizServlet", method = RequestMethod.POST)
    public String submit(@RequestParam("action") String action,
            ModelMap model
    ) {
        DbHandler dbh = new DbHandler();

        if ("SelectQuiz1".equals(action)) {
            quizType = 1;
            questions = dbh.getQuestions(quizType);
            model.addAttribute("questions", questions);

            return "quiz1.html";
        } else if ("SelectQuiz2".equals(action)) {
            quizType = 2;
            questions = dbh.getQuestions(quizType);
            model.addAttribute("questions", questions);
            return "quiz2.html";
        } else if ("toMain".equals(action)) {

            ArrayList<Result> quiz1History; // if default num shows up something is wrong
            ArrayList<Result> quiz2History;
            quiz1History = dbh.getResults(1, userName);
            quiz2History = dbh.getResults(2, userName);
            model.addAttribute("quiz1History", quiz1History);
            model.addAttribute("quiz2History", quiz2History);

            return "mainMenu.html";
        }
        return "mainMenu.html";
    }
}
